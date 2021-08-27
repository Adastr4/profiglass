package it.adastra.profilglass.configuratore.service.impl;

import it.adastra.profilglass.configuratore.domain.CARCLFINI;
import it.adastra.profilglass.configuratore.repository.CARCLFINIRepository;
import it.adastra.profilglass.configuratore.service.CARCLFINIService;
import it.adastra.profilglass.configuratore.service.dto.CARCLFINIDTO;
import it.adastra.profilglass.configuratore.service.mapper.CARCLFINIMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CARCLFINI}.
 */
@Service
@Transactional
public class CARCLFINIServiceImpl implements CARCLFINIService {

    private final Logger log = LoggerFactory.getLogger(CARCLFINIServiceImpl.class);

    private final CARCLFINIRepository cARCLFINIRepository;

    private final CARCLFINIMapper cARCLFINIMapper;

    public CARCLFINIServiceImpl(CARCLFINIRepository cARCLFINIRepository, CARCLFINIMapper cARCLFINIMapper) {
        this.cARCLFINIRepository = cARCLFINIRepository;
        this.cARCLFINIMapper = cARCLFINIMapper;
    }

    @Override
    public CARCLFINIDTO save(CARCLFINIDTO cARCLFINIDTO) {
        log.debug("Request to save CARCLFINI : {}", cARCLFINIDTO);
        CARCLFINI cARCLFINI = cARCLFINIMapper.toEntity(cARCLFINIDTO);
        cARCLFINI = cARCLFINIRepository.save(cARCLFINI);
        return cARCLFINIMapper.toDto(cARCLFINI);
    }

    @Override
    public Optional<CARCLFINIDTO> partialUpdate(CARCLFINIDTO cARCLFINIDTO) {
        log.debug("Request to partially update CARCLFINI : {}", cARCLFINIDTO);

        return cARCLFINIRepository
            .findById(cARCLFINIDTO.getId())
            .map(
                existingCARCLFINI -> {
                    cARCLFINIMapper.partialUpdate(existingCARCLFINI, cARCLFINIDTO);

                    return existingCARCLFINI;
                }
            )
            .map(cARCLFINIRepository::save)
            .map(cARCLFINIMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CARCLFINIDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CARCLFINIS");
        return cARCLFINIRepository.findAll(pageable).map(cARCLFINIMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CARCLFINIDTO> findOne(Long id) {
        log.debug("Request to get CARCLFINI : {}", id);
        return cARCLFINIRepository.findById(id).map(cARCLFINIMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CARCLFINI : {}", id);
        cARCLFINIRepository.deleteById(id);
    }
}
