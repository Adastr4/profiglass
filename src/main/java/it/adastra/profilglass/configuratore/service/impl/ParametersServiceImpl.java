package it.adastra.profilglass.configuratore.service.impl;

import it.adastra.profilglass.configuratore.domain.Parameters;
import it.adastra.profilglass.configuratore.repository.ParametersRepository;
import it.adastra.profilglass.configuratore.service.ParametersService;
import it.adastra.profilglass.configuratore.service.dto.ParametersDTO;
import it.adastra.profilglass.configuratore.service.mapper.ParametersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Parameters}.
 */
@Service
@Transactional
public class ParametersServiceImpl implements ParametersService {

    private final Logger log = LoggerFactory.getLogger(ParametersServiceImpl.class);

    private final ParametersRepository parametersRepository;

    private final ParametersMapper parametersMapper;

    public ParametersServiceImpl(ParametersRepository parametersRepository, ParametersMapper parametersMapper) {
        this.parametersRepository = parametersRepository;
        this.parametersMapper = parametersMapper;
    }

    @Override
    public ParametersDTO save(ParametersDTO parametersDTO) {
        log.debug("Request to save Parameters : {}", parametersDTO);
        Parameters parameters = parametersMapper.toEntity(parametersDTO);
        parameters = parametersRepository.save(parameters);
        return parametersMapper.toDto(parameters);
    }

    @Override
    public Optional<ParametersDTO> partialUpdate(ParametersDTO parametersDTO) {
        log.debug("Request to partially update Parameters : {}", parametersDTO);

        return parametersRepository
            .findById(parametersDTO.getId())
            .map(
                existingParameters -> {
                    parametersMapper.partialUpdate(existingParameters, parametersDTO);

                    return existingParameters;
                }
            )
            .map(parametersRepository::save)
            .map(parametersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParametersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parameters");
        return parametersRepository.findAll(pageable).map(parametersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParametersDTO> findOne(Long id) {
        log.debug("Request to get Parameters : {}", id);
        return parametersRepository.findById(id).map(parametersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parameters : {}", id);
        parametersRepository.deleteById(id);
    }
}
