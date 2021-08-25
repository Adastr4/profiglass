package it.adastra.profilglass.configuratore.service;

import it.adastra.profilglass.configuratore.service.dto.ParametersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link it.adastra.profilglass.configuratore.domain.Parameters}.
 */
public interface ParametersService {
    /**
     * Save a parameters.
     *
     * @param parametersDTO the entity to save.
     * @return the persisted entity.
     */
    ParametersDTO save(ParametersDTO parametersDTO);

    /**
     * Partially updates a parameters.
     *
     * @param parametersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParametersDTO> partialUpdate(ParametersDTO parametersDTO);

    /**
     * Get all the parameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParametersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" parameters.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParametersDTO> findOne(Long id);

    /**
     * Delete the "id" parameters.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
