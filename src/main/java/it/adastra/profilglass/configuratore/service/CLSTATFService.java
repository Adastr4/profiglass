package it.adastra.profilglass.configuratore.service;

import it.adastra.profilglass.configuratore.service.dto.CLSTATFDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link it.adastra.profilglass.configuratore.domain.CLSTATF}.
 */
public interface CLSTATFService {
    /**
     * Save a cLSTATF.
     *
     * @param cLSTATFDTO the entity to save.
     * @return the persisted entity.
     */
    CLSTATFDTO save(CLSTATFDTO cLSTATFDTO);

    /**
     * Partially updates a cLSTATF.
     *
     * @param cLSTATFDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CLSTATFDTO> partialUpdate(CLSTATFDTO cLSTATFDTO);

    /**
     * Get all the cLSTATFS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CLSTATFDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cLSTATF.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CLSTATFDTO> findOne(Long id);

    /**
     * Delete the "id" cLSTATF.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
