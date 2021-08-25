package it.adastra.profilglass.configuratore.service.impl;

import it.adastra.profilglass.configuratore.domain.CLLEGA;
import it.adastra.profilglass.configuratore.repository.CLLEGARepository;
import it.adastra.profilglass.configuratore.service.CLLEGAService;
import it.adastra.profilglass.configuratore.service.dto.CLLEGADTO;
import it.adastra.profilglass.configuratore.service.mapper.CLLEGAMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CLLEGA}.
 */
@Service
@Transactional
public class CLLEGAServiceImpl implements CLLEGAService {

    private final Logger log = LoggerFactory.getLogger(CLLEGAServiceImpl.class);

    private final CLLEGARepository cLLEGARepository;

    private final CLLEGAMapper cLLEGAMapper;

    public CLLEGAServiceImpl(CLLEGARepository cLLEGARepository, CLLEGAMapper cLLEGAMapper) {
        this.cLLEGARepository = cLLEGARepository;
        this.cLLEGAMapper = cLLEGAMapper;
    }

    @Override
    public CLLEGADTO save(CLLEGADTO cLLEGADTO) {
        log.debug("Request to save CLLEGA : {}", cLLEGADTO);
        CLLEGA cLLEGA = cLLEGAMapper.toEntity(cLLEGADTO);
        cLLEGA = cLLEGARepository.save(cLLEGA);
        return cLLEGAMapper.toDto(cLLEGA);
    }

    @Override
    public Optional<CLLEGADTO> partialUpdate(CLLEGADTO cLLEGADTO) {
        log.debug("Request to partially update CLLEGA : {}", cLLEGADTO);

        return cLLEGARepository
            .findById(cLLEGADTO.getId())
            .map(
                existingCLLEGA -> {
                    cLLEGAMapper.partialUpdate(existingCLLEGA, cLLEGADTO);

                    return existingCLLEGA;
                }
            )
            .map(cLLEGARepository::save)
            .map(cLLEGAMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CLLEGADTO> findAll(Pageable pageable) {
        log.debug("Request to get all CLLEGAS");
        return cLLEGARepository.findAll(pageable).map(cLLEGAMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CLLEGADTO> findOne(Long id) {
        log.debug("Request to get CLLEGA : {}", id);
        return cLLEGARepository.findById(id).map(cLLEGAMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CLLEGA : {}", id);
        cLLEGARepository.deleteById(id);
    }
}
