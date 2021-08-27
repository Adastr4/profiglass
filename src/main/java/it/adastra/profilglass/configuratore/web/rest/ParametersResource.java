package it.adastra.profilglass.configuratore.web.rest;

import it.adastra.profilglass.configuratore.repository.ParametersRepository;
import it.adastra.profilglass.configuratore.service.ParametersQueryService;
import it.adastra.profilglass.configuratore.service.ParametersService;
import it.adastra.profilglass.configuratore.service.criteria.ParametersCriteria;
import it.adastra.profilglass.configuratore.service.dto.ParametersDTO;
import it.adastra.profilglass.configuratore.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.adastra.profilglass.configuratore.domain.Parameters}.
 */
@RestController
@RequestMapping("/api")
public class ParametersResource {

    private final Logger log = LoggerFactory.getLogger(ParametersResource.class);

    private static final String ENTITY_NAME = "parameters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametersService parametersService;

    private final ParametersRepository parametersRepository;

    private final ParametersQueryService parametersQueryService;

    public ParametersResource(
        ParametersService parametersService,
        ParametersRepository parametersRepository,
        ParametersQueryService parametersQueryService
    ) {
        this.parametersService = parametersService;
        this.parametersRepository = parametersRepository;
        this.parametersQueryService = parametersQueryService;
    }

    /**
     * {@code POST  /parameters} : Create a new parameters.
     *
     * @param parametersDTO the parametersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametersDTO, or with status {@code 400 (Bad Request)} if the parameters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parameters")
    public ResponseEntity<ParametersDTO> createParameters(@Valid @RequestBody ParametersDTO parametersDTO) throws URISyntaxException {
        log.debug("REST request to save Parameters : {}", parametersDTO);
        if (parametersDTO.getId() != null) {
            throw new BadRequestAlertException("A new parameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametersDTO result = parametersService.save(parametersDTO);
        return ResponseEntity
            .created(new URI("/api/parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parameters/:id} : Updates an existing parameters.
     *
     * @param id the id of the parametersDTO to save.
     * @param parametersDTO the parametersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametersDTO,
     * or with status {@code 400 (Bad Request)} if the parametersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parameters/{id}")
    public ResponseEntity<ParametersDTO> updateParameters(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParametersDTO parametersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Parameters : {}, {}", id, parametersDTO);
        if (parametersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parametersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parametersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParametersDTO result = parametersService.save(parametersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parameters/:id} : Partial updates given fields of an existing parameters, field will ignore if it is null
     *
     * @param id the id of the parametersDTO to save.
     * @param parametersDTO the parametersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametersDTO,
     * or with status {@code 400 (Bad Request)} if the parametersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parametersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parametersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parameters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ParametersDTO> partialUpdateParameters(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParametersDTO parametersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parameters partially : {}, {}", id, parametersDTO);
        if (parametersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parametersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parametersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParametersDTO> result = parametersService.partialUpdate(parametersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parameters} : get all the parameters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameters in body.
     */
    @GetMapping("/parameters")
    public ResponseEntity<List<ParametersDTO>> getAllParameters(ParametersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Parameters by criteria: {}", criteria);
        Page<ParametersDTO> page = parametersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parameters/count} : count all the parameters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/parameters/count")
    public ResponseEntity<Long> countParameters(ParametersCriteria criteria) {
        log.debug("REST request to count Parameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(parametersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parameters/:id} : get the "id" parameters.
     *
     * @param id the id of the parametersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parameters/{id}")
    public ResponseEntity<ParametersDTO> getParameters(@PathVariable Long id) {
        log.debug("REST request to get Parameters : {}", id);
        Optional<ParametersDTO> parametersDTO = parametersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametersDTO);
    }

    /**
     * {@code DELETE  /parameters/:id} : delete the "id" parameters.
     *
     * @param id the id of the parametersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parameters/{id}")
    public ResponseEntity<Void> deleteParameters(@PathVariable Long id) {
        log.debug("REST request to delete Parameters : {}", id);
        parametersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
