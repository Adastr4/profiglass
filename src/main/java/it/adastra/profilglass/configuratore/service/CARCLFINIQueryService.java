package it.adastra.profilglass.configuratore.service;

// for static metamodels
import it.adastra.profilglass.configuratore.domain.CARCLFINI;
import it.adastra.profilglass.configuratore.domain.CARCLFINI_;
import it.adastra.profilglass.configuratore.repository.CARCLFINIRepository;
import it.adastra.profilglass.configuratore.service.criteria.CARCLFINICriteria;
import it.adastra.profilglass.configuratore.service.dto.CARCLFINIDTO;
import it.adastra.profilglass.configuratore.service.mapper.CARCLFINIMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CARCLFINI} entities in the database.
 * The main input is a {@link CARCLFINICriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CARCLFINIDTO} or a {@link Page} of {@link CARCLFINIDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CARCLFINIQueryService extends QueryService<CARCLFINI> {

    private final Logger log = LoggerFactory.getLogger(CARCLFINIQueryService.class);

    private final CARCLFINIRepository cARCLFINIRepository;

    private final CARCLFINIMapper cARCLFINIMapper;

    public CARCLFINIQueryService(CARCLFINIRepository cARCLFINIRepository, CARCLFINIMapper cARCLFINIMapper) {
        this.cARCLFINIRepository = cARCLFINIRepository;
        this.cARCLFINIMapper = cARCLFINIMapper;
    }

    /**
     * Return a {@link List} of {@link CARCLFINIDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CARCLFINIDTO> findByCriteria(CARCLFINICriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CARCLFINI> specification = createSpecification(criteria);
        return cARCLFINIMapper.toDto(cARCLFINIRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CARCLFINIDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CARCLFINIDTO> findByCriteria(CARCLFINICriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CARCLFINI> specification = createSpecification(criteria);
        return cARCLFINIRepository.findAll(specification, page).map(cARCLFINIMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CARCLFINICriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CARCLFINI> specification = createSpecification(criteria);
        return cARCLFINIRepository.count(specification);
    }

    /**
     * Function to convert {@link CARCLFINICriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CARCLFINI> createSpecification(CARCLFINICriteria criteria) {
        Specification<CARCLFINI> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CARCLFINI_.id));
            }
            if (criteria.getClasse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClasse(), CARCLFINI_.classe));
            }
        }
        return specification;
    }
}
