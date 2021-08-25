package it.adastra.profilglass.configuratore.service;

import it.adastra.profilglass.configuratore.service.dto.CLLEGADTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link it.adastra.profilglass.configuratore.domain.CLLEGA}.
 */
public interface CLLEGAService {
    /**
     * Save a cLLEGA.
     *
     * @param cLLEGADTO the entity to save.
     * @return the persisted entity.
     */
    CLLEGADTO save(CLLEGADTO cLLEGADTO);

    /**
     * Partially updates a cLLEGA.
     *
     * @param cLLEGADTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CLLEGADTO> partialUpdate(CLLEGADTO cLLEGADTO);

    /**
     * Get all the cLLEGAS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CLLEGADTO> findAll(Pageable pageable);

    /**
     * Get the "id" cLLEGA.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CLLEGADTO> findOne(Long id);

    /**
     * Delete the "id" cLLEGA.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
