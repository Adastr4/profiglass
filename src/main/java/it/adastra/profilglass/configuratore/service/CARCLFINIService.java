package it.adastra.profilglass.configuratore.service;

import it.adastra.profilglass.configuratore.service.dto.CARCLFINIDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link it.adastra.profilglass.configuratore.domain.CARCLFINI}.
 */
public interface CARCLFINIService {
    /**
     * Save a cARCLFINI.
     *
     * @param cARCLFINIDTO the entity to save.
     * @return the persisted entity.
     */
    CARCLFINIDTO save(CARCLFINIDTO cARCLFINIDTO);

    /**
     * Partially updates a cARCLFINI.
     *
     * @param cARCLFINIDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CARCLFINIDTO> partialUpdate(CARCLFINIDTO cARCLFINIDTO);

    /**
     * Get all the cARCLFINIS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CARCLFINIDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cARCLFINI.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CARCLFINIDTO> findOne(Long id);

    /**
     * Delete the "id" cARCLFINI.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
