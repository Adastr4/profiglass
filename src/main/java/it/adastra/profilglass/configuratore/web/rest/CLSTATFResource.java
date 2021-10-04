package it.adastra.profilglass.configuratore.web.rest;

import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.Result;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import it.adastra.profilglass.configuratore.repository.CLSTATFRepository;
import it.adastra.profilglass.configuratore.service.CLSTATFQueryService;
import it.adastra.profilglass.configuratore.service.CLSTATFService;
import it.adastra.profilglass.configuratore.service.criteria.CLSTATFCriteria;
import it.adastra.profilglass.configuratore.service.dto.CLSTATFDTO;
import it.adastra.profilglass.configuratore.web.rest.errors.BadRequestAlertException;
import it.profilglass.classmodel.Caratteristica;
import it.profilglass.classmodel.ICaratteristica;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
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
 * REST controller for managing
 * {@link it.adastra.profilglass.configuratore.domain.CLSTATF}.
 */
@RestController
@RequestMapping("/api")
public class CLSTATFResource {

    private final Logger log = LoggerFactory.getLogger(CLSTATFResource.class);

    private static final String ENTITY_NAME = "cLSTATF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CLSTATFService cLSTATFService;

    private final CLSTATFRepository cLSTATFRepository;

    private final CLSTATFQueryService cLSTATFQueryService;

    public CLSTATFResource(CLSTATFService cLSTATFService, CLSTATFRepository cLSTATFRepository, CLSTATFQueryService cLSTATFQueryService) {
        this.cLSTATFService = cLSTATFService;
        this.cLSTATFRepository = cLSTATFRepository;
        this.cLSTATFQueryService = cLSTATFQueryService;
    }

    /**
     * {@code POST  /clstatfs} : Create a new cLSTATF.
     *
     * @param cLSTATFDTO the cLSTATFDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new cLSTATFDTO, or with status {@code 400 (Bad Request)} if
     *         the cLSTATF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clstatfs")
    public ResponseEntity<CLSTATFDTO> createCLSTATF(@Valid @RequestBody CLSTATFDTO cLSTATFDTO) throws URISyntaxException {
        log.debug("REST request to save CLSTATF : {}", cLSTATFDTO);
        if (cLSTATFDTO.getId() != null) {
            throw new BadRequestAlertException("A new cLSTATF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CLSTATFDTO result = cLSTATFService.save(cLSTATFDTO);
        return ResponseEntity
            .created(new URI("/api/clstatfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clstatfs/:id} : Updates an existing cLSTATF.
     *
     * @param id         the id of the cLSTATFDTO to save.
     * @param cLSTATFDTO the cLSTATFDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated cLSTATFDTO, or with status {@code 400 (Bad Request)} if
     *         the cLSTATFDTO is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the cLSTATFDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clstatfs/{id}")
    public ResponseEntity<CLSTATFDTO> updateCLSTATF(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CLSTATFDTO cLSTATFDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CLSTATF : {}, {}", id, cLSTATFDTO);
        if (cLSTATFDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cLSTATFDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cLSTATFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CLSTATFDTO result = cLSTATFService.save(cLSTATFDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cLSTATFDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clstatfs/:id} : Partial updates given fields of an existing
     * cLSTATF, field will ignore if it is null
     *
     * @param id         the id of the cLSTATFDTO to save.
     * @param cLSTATFDTO the cLSTATFDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated cLSTATFDTO, or with status {@code 400 (Bad Request)} if
     *         the cLSTATFDTO is not valid, or with status {@code 404 (Not Found)}
     *         if the cLSTATFDTO is not found, or with status
     *         {@code 500 (Internal Server Error)} if the cLSTATFDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clstatfs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CLSTATFDTO> partialUpdateCLSTATF(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CLSTATFDTO cLSTATFDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CLSTATF partially : {}, {}", id, cLSTATFDTO);
        if (cLSTATFDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cLSTATFDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cLSTATFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CLSTATFDTO> result = cLSTATFService.partialUpdate(cLSTATFDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cLSTATFDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /clstatfs} : get all the cLSTATFS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of cLSTATFS in body.
     */
    @GetMapping("/clstatfs")
    public ResponseEntity<List<CLSTATFDTO>> getAllCLSTATFS(CLSTATFCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CLSTATFS by criteria: {}", criteria);
        Page<CLSTATFDTO> page = cLSTATFQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clstatfs/count} : count all the cLSTATFS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/clstatfs/count")
    public ResponseEntity<Long> countCLSTATFS(CLSTATFCriteria criteria) {
        log.debug("REST request to count CLSTATFS by criteria: {}", criteria);
        return ResponseEntity.ok().body(cLSTATFQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /clstatfs/:id} : get the "id" cLSTATF.
     *
     * @param id the id of the cLSTATFDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the cLSTATFDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clstatfs/{id}")
    public ResponseEntity<CLSTATFDTO> getCLSTATF(@PathVariable Long id) {
        log.debug("REST request to get CLSTATF : {}", id);
        Optional<CLSTATFDTO> cLSTATFDTO = cLSTATFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cLSTATFDTO);
    }

    /**
     * {@code GET  /clstatfs/:idlega} : get the "id" che sono compatibili con la
     * lega selezionata.
     *
     * prendere tutti i
     *
     * @param id the idlega of the lega scelta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the cLSTATFDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clstatfslega/{idlega}")
    public List<CLSTATFDTO> getAllCLSTATFwLega(@PathVariable String idlega) {
        log.debug("REST request to get getAllCLSTATFwLega by criteria: {}", idlega);
        List<CLSTATFDTO> page = cLSTATFQueryService.findByCriteria(null);
        applyRules(idlega, page);
        return page;
    }

    void applyRules(String idlega, List<CLSTATFDTO> page) {
        for (Iterator iterator = page.iterator(); iterator.hasNext();) {
            CLSTATFDTO clstatfdto = (CLSTATFDTO) iterator.next();
            if (!applyRulesToLega(idlega, clstatfdto.getOpzione())) {
                iterator.remove();
            }
        }
    }

    boolean applyRulesToLega(String lega, String statofisico) {
        RuleBookRunner ruleBook = new RuleBookRunner(
            "it.profilglass.constraint.bav.CLLEGA.val",
            s -> s.equalsIgnoreCase("it.profilglass.constraint.bav.CLLEGA.val")
        );
        NameValueReferableMap<ICaratteristica> facts = new FactMap<>();

        ICaratteristica applicant1 = new Caratteristica(new BigDecimal(650), lega, statofisico, "B07187", "B07187", "", "");

        facts.put(new Fact<>(applicant1));

        ruleBook.setDefaultResult(Boolean.FALSE);
        ruleBook.run(facts);
        boolean ret = false;
        Optional<Result> result = ruleBook.getResult();

        result.ifPresent(
            action -> {
                System.out.println("Vincolo per Caratteristica stato fisico " + " validato " + action);
            }
        );

        return (boolean) result.get().getValue();
    }

    /**
     * {@code DELETE  /clstatfs/:id} : delete the "id" cLSTATF.
     *
     * @param id the id of the cLSTATFDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clstatfs/{id}")
    public ResponseEntity<Void> deleteCLSTATF(@PathVariable Long id) {
        log.debug("REST request to delete CLSTATF : {}", id);
        cLSTATFService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
