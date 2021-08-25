package it.adastra.profilglass.configuratore.service.impl;

import it.adastra.profilglass.configuratore.domain.CLSTATF;
import it.adastra.profilglass.configuratore.repository.CLSTATFRepository;
import it.adastra.profilglass.configuratore.service.CLSTATFService;
import it.adastra.profilglass.configuratore.service.dto.CLSTATFDTO;
import it.adastra.profilglass.configuratore.service.mapper.CLSTATFMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CLSTATF}.
 */
@Service
@Transactional
public class CLSTATFServiceImpl implements CLSTATFService {

    private final Logger log = LoggerFactory.getLogger(CLSTATFServiceImpl.class);

    private final CLSTATFRepository cLSTATFRepository;

    private final CLSTATFMapper cLSTATFMapper;

    public CLSTATFServiceImpl(CLSTATFRepository cLSTATFRepository, CLSTATFMapper cLSTATFMapper) {
        this.cLSTATFRepository = cLSTATFRepository;
        this.cLSTATFMapper = cLSTATFMapper;
    }

    @Override
    public CLSTATFDTO save(CLSTATFDTO cLSTATFDTO) {
        log.debug("Request to save CLSTATF : {}", cLSTATFDTO);
        CLSTATF cLSTATF = cLSTATFMapper.toEntity(cLSTATFDTO);
        cLSTATF = cLSTATFRepository.save(cLSTATF);
        return cLSTATFMapper.toDto(cLSTATF);
    }

    @Override
    public Optional<CLSTATFDTO> partialUpdate(CLSTATFDTO cLSTATFDTO) {
        log.debug("Request to partially update CLSTATF : {}", cLSTATFDTO);

        return cLSTATFRepository
            .findById(cLSTATFDTO.getId())
            .map(
                existingCLSTATF -> {
                    cLSTATFMapper.partialUpdate(existingCLSTATF, cLSTATFDTO);

                    return existingCLSTATF;
                }
            )
            .map(cLSTATFRepository::save)
            .map(cLSTATFMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CLSTATFDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CLSTATFS");
        return cLSTATFRepository.findAll(pageable).map(cLSTATFMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CLSTATFDTO> findOne(Long id) {
        log.debug("Request to get CLSTATF : {}", id);
        return cLSTATFRepository.findById(id).map(cLSTATFMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CLSTATF : {}", id);
        cLSTATFRepository.deleteById(id);
    }
}
