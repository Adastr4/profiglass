package it.adastra.profilglass.configuratore.web.rest;

import it.adastra.profilglass.configuratore.repository.CLLEGARepository;
import it.adastra.profilglass.configuratore.service.CLLEGAQueryService;
import it.adastra.profilglass.configuratore.service.CLLEGAService;
import it.adastra.profilglass.configuratore.service.criteria.CLLEGACriteria;
import it.adastra.profilglass.configuratore.service.dto.CLLEGADTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.adastra.profilglass.configuratore.domain.CLLEGA}.
 */
@RestController
@RequestMapping("/api")
public class CLLEGAResource {

    private final Logger log = LoggerFactory.getLogger(CLLEGAResource.class);

    private static final String ENTITY_NAME = "cLLEGA";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CLLEGAService cLLEGAService;

    private final CLLEGARepository cLLEGARepository;

    private final CLLEGAQueryService cLLEGAQueryService;

    public CLLEGAResource(CLLEGAService cLLEGAService, CLLEGARepository cLLEGARepository, CLLEGAQueryService cLLEGAQueryService) {
        this.cLLEGAService = cLLEGAService;
        this.cLLEGARepository = cLLEGARepository;
        this.cLLEGAQueryService = cLLEGAQueryService;
    }

    /**
     * {@code POST  /cllegas} : Create a new cLLEGA.
     *
     * @param cLLEGADTO the cLLEGADTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cLLEGADTO, or with status {@code 400 (Bad Request)} if the cLLEGA has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cllegas")
    public ResponseEntity<CLLEGADTO> createCLLEGA(@Valid @RequestBody CLLEGADTO cLLEGADTO) throws URISyntaxException {
        log.debug("REST request to save CLLEGA : {}", cLLEGADTO);
        if (cLLEGADTO.getId() != null) {
            throw new BadRequestAlertException("A new cLLEGA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CLLEGADTO result = cLLEGAService.save(cLLEGADTO);
        return ResponseEntity
            .created(new URI("/api/cllegas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cllegas/:id} : Updates an existing cLLEGA.
     *
     * @param id the id of the cLLEGADTO to save.
     * @param cLLEGADTO the cLLEGADTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cLLEGADTO,
     * or with status {@code 400 (Bad Request)} if the cLLEGADTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cLLEGADTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cllegas/{id}")
    public ResponseEntity<CLLEGADTO> updateCLLEGA(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CLLEGADTO cLLEGADTO
    ) throws URISyntaxException {
        log.debug("REST request to update CLLEGA : {}, {}", id, cLLEGADTO);
        if (cLLEGADTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cLLEGADTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cLLEGARepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CLLEGADTO result = cLLEGAService.save(cLLEGADTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cLLEGADTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cllegas/:id} : Partial updates given fields of an existing cLLEGA, field will ignore if it is null
     *
     * @param id the id of the cLLEGADTO to save.
     * @param cLLEGADTO the cLLEGADTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cLLEGADTO,
     * or with status {@code 400 (Bad Request)} if the cLLEGADTO is not valid,
     * or with status {@code 404 (Not Found)} if the cLLEGADTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cLLEGADTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cllegas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CLLEGADTO> partialUpdateCLLEGA(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CLLEGADTO cLLEGADTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CLLEGA partially : {}, {}", id, cLLEGADTO);
        if (cLLEGADTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cLLEGADTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cLLEGARepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CLLEGADTO> result = cLLEGAService.partialUpdate(cLLEGADTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cLLEGADTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cllegas} : get all the cLLEGAS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cLLEGAS in body.
     */
    @GetMapping("/cllegas")
    public ResponseEntity<List<CLLEGADTO>> getAllCLLEGAS(CLLEGACriteria criteria, Pageable pageable) {
        log.debug("REST request to get CLLEGAS by criteria: {}", criteria);
        Page<CLLEGADTO> page = cLLEGAQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cllegas/count} : count all the cLLEGAS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cllegas/count")
    public ResponseEntity<Long> countCLLEGAS(CLLEGACriteria criteria) {
        log.debug("REST request to count CLLEGAS by criteria: {}", criteria);
        return ResponseEntity.ok().body(cLLEGAQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cllegas/:id} : get the "id" cLLEGA.
     *
     * @param id the id of the cLLEGADTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cLLEGADTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cllegas/{id}")
    public ResponseEntity<CLLEGADTO> getCLLEGA(@PathVariable Long id) {
        log.debug("REST request to get CLLEGA : {}", id);
        Optional<CLLEGADTO> cLLEGADTO = cLLEGAService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cLLEGADTO);
    }

    /**
     * {@code DELETE  /cllegas/:id} : delete the "id" cLLEGA.
     *
     * @param id the id of the cLLEGADTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cllegas/{id}")
    public ResponseEntity<Void> deleteCLLEGA(@PathVariable Long id) {
        log.debug("REST request to delete CLLEGA : {}", id);
        cLLEGAService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
