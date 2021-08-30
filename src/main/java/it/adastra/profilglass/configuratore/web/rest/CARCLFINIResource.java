package it.adastra.profilglass.configuratore.web.rest;

import cart.test.CaratteristicaBean;
import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.Result;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import it.adastra.profilglass.configuratore.domain.CARCLFINI;
import it.adastra.profilglass.configuratore.repository.CARCLFINIRepository;
import it.adastra.profilglass.configuratore.service.CARCLFINIQueryService;
import it.adastra.profilglass.configuratore.service.CARCLFINIService;
import it.adastra.profilglass.configuratore.service.criteria.CARCLFINICriteria;
import it.adastra.profilglass.configuratore.service.dto.CARCLFINIDTO;
import it.adastra.profilglass.configuratore.service.dto.CLSTATFDTO;
import it.adastra.profilglass.configuratore.web.rest.errors.BadRequestAlertException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.adastra.profilglass.configuratore.domain.CARCLFINI}.
 */
@RestController
@RequestMapping("/api")
public class CARCLFINIResource {

    private final Logger log = LoggerFactory.getLogger(CARCLFINIResource.class);

    private static final String ENTITY_NAME = "cARCLFINI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CARCLFINIService cARCLFINIService;

    private final CARCLFINIRepository cARCLFINIRepository;

    private final CARCLFINIQueryService cARCLFINIQueryService;

    public CARCLFINIResource(
        CARCLFINIService cARCLFINIService,
        CARCLFINIRepository cARCLFINIRepository,
        CARCLFINIQueryService cARCLFINIQueryService
    ) {
        this.cARCLFINIService = cARCLFINIService;
        this.cARCLFINIRepository = cARCLFINIRepository;
        this.cARCLFINIQueryService = cARCLFINIQueryService;
    }

    /**
     * {@code POST  /carclfinis} : Create a new cARCLFINI.
     *
     * @param cARCLFINIDTO the cARCLFINIDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cARCLFINIDTO, or with status {@code 400 (Bad Request)} if the cARCLFINI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carclfinis")
    public ResponseEntity<CARCLFINIDTO> createCARCLFINI(@Valid @RequestBody CARCLFINIDTO cARCLFINIDTO) throws URISyntaxException {
        log.debug("REST request to save CARCLFINI : {}", cARCLFINIDTO);
        if (cARCLFINIDTO.getId() != null) {
            throw new BadRequestAlertException("A new cARCLFINI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CARCLFINIDTO result = cARCLFINIService.save(cARCLFINIDTO);
        return ResponseEntity
            .created(new URI("/api/carclfinis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carclfinis/:id} : Updates an existing cARCLFINI.
     *
     * @param id the id of the cARCLFINIDTO to save.
     * @param cARCLFINIDTO the cARCLFINIDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cARCLFINIDTO,
     * or with status {@code 400 (Bad Request)} if the cARCLFINIDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cARCLFINIDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carclfinis/{id}")
    public ResponseEntity<CARCLFINIDTO> updateCARCLFINI(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CARCLFINIDTO cARCLFINIDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CARCLFINI : {}, {}", id, cARCLFINIDTO);
        if (cARCLFINIDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cARCLFINIDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cARCLFINIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CARCLFINIDTO result = cARCLFINIService.save(cARCLFINIDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cARCLFINIDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carclfinis/:id} : Partial updates given fields of an existing cARCLFINI, field will ignore if it is null
     *
     * @param id the id of the cARCLFINIDTO to save.
     * @param cARCLFINIDTO the cARCLFINIDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cARCLFINIDTO,
     * or with status {@code 400 (Bad Request)} if the cARCLFINIDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cARCLFINIDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cARCLFINIDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/carclfinis/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CARCLFINIDTO> partialUpdateCARCLFINI(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CARCLFINIDTO cARCLFINIDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CARCLFINI partially : {}, {}", id, cARCLFINIDTO);
        if (cARCLFINIDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cARCLFINIDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cARCLFINIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CARCLFINIDTO> result = cARCLFINIService.partialUpdate(cARCLFINIDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cARCLFINIDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carclfinis} : get all the cARCLFINIS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cARCLFINIS in body.
     */
    @GetMapping("/carclfinis")
    public ResponseEntity<List<CARCLFINIDTO>> getAllCARCLFINIS(CARCLFINICriteria criteria, Pageable pageable) {
        log.debug("REST request to get CARCLFINIS by criteria: {}", criteria);
        Page<CARCLFINIDTO> page = cARCLFINIQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clstatfsfinitura/:idlega/:idfinitura} : get the "id" che sono compatibili con la
     * lega selezionata el a finitura.
     *
     * prendere tutti i
     *
     * @param id the idlega of the lega scelta to retrieve.
     * @param id the idfinitura of the lega scelta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the cLSTATFDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clstatfsfinitura/{idlega}/{idfinitura}")
    public List<CARCLFINIDTO> getAllCLSTATFwFinitura(@PathVariable String idlega, @PathVariable String idfinitura) {
        log.debug("REST request to get getAllCLSTATFwLega by criteria: {}", idlega);
        List<CARCLFINIDTO> page = cARCLFINIQueryService.findByCriteria(null);
        applyRulesFinitura(idlega, idfinitura, page);
        return page;
    }

    void applyRulesFinitura(String idlega, String idfinitura, List<CARCLFINIDTO> page) {
        for (Iterator iterator = page.iterator(); iterator.hasNext();) {
            CARCLFINIDTO clstatfdto = (CARCLFINIDTO) iterator.next();
            if (!applyRulesToFinitura(idlega, idfinitura, clstatfdto.getClasse())) {
                iterator.remove();
            }
        }
    }

    boolean applyRulesToFinitura(String lega, String statofisico, String finitura) {
        RuleBookRunner ruleBook = new RuleBookRunner("cart.test.constraints.cs2");
        NameValueReferableMap<CaratteristicaBean> facts = new FactMap<>();

        CaratteristicaBean applicant1 = new CaratteristicaBean(new BigDecimal(650), lega, statofisico, "B07187", "B07187", finitura, "EDT");

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
     * {@code GET  /carclfinis/count} : count all the cARCLFINIS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/carclfinis/count")
    public ResponseEntity<Long> countCARCLFINIS(CARCLFINICriteria criteria) {
        log.debug("REST request to count CARCLFINIS by criteria: {}", criteria);
        return ResponseEntity.ok().body(cARCLFINIQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /carclfinis/:id} : get the "id" cARCLFINI.
     *
     * @param id the id of the cARCLFINIDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cARCLFINIDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carclfinis/{id}")
    public ResponseEntity<CARCLFINIDTO> getCARCLFINI(@PathVariable Long id) {
        log.debug("REST request to get CARCLFINI : {}", id);
        Optional<CARCLFINIDTO> cARCLFINIDTO = cARCLFINIService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cARCLFINIDTO);
    }

    /**
     * {@code DELETE  /carclfinis/:id} : delete the "id" cARCLFINI.
     *
     * @param id the id of the cARCLFINIDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carclfinis/{id}")
    public ResponseEntity<Void> deleteCARCLFINI(@PathVariable Long id) {
        log.debug("REST request to delete CARCLFINI : {}", id);
        cARCLFINIService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
