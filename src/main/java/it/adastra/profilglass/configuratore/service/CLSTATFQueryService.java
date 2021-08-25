package it.adastra.profilglass.configuratore.service;

import it.adastra.profilglass.configuratore.domain.*; // for static metamodels
import it.adastra.profilglass.configuratore.domain.CLSTATF;
import it.adastra.profilglass.configuratore.repository.CLSTATFRepository;
import it.adastra.profilglass.configuratore.service.criteria.CLSTATFCriteria;
import it.adastra.profilglass.configuratore.service.dto.CLSTATFDTO;
import it.adastra.profilglass.configuratore.service.mapper.CLSTATFMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CLSTATF} entities in the database.
 * The main input is a {@link CLSTATFCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CLSTATFDTO} or a {@link Page} of {@link CLSTATFDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CLSTATFQueryService extends QueryService<CLSTATF> {

    private final Logger log = LoggerFactory.getLogger(CLSTATFQueryService.class);

    private final CLSTATFRepository cLSTATFRepository;

    private final CLSTATFMapper cLSTATFMapper;

    public CLSTATFQueryService(CLSTATFRepository cLSTATFRepository, CLSTATFMapper cLSTATFMapper) {
        this.cLSTATFRepository = cLSTATFRepository;
        this.cLSTATFMapper = cLSTATFMapper;
    }

    /**
     * Return a {@link List} of {@link CLSTATFDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CLSTATFDTO> findByCriteria(CLSTATFCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CLSTATF> specification = createSpecification(criteria);
        return cLSTATFMapper.toDto(cLSTATFRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CLSTATFDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CLSTATFDTO> findByCriteria(CLSTATFCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CLSTATF> specification = createSpecification(criteria);
        return cLSTATFRepository.findAll(specification, page).map(cLSTATFMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CLSTATFCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CLSTATF> specification = createSpecification(criteria);
        return cLSTATFRepository.count(specification);
    }

    /**
     * Function to convert {@link CLSTATFCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CLSTATF> createSpecification(CLSTATFCriteria criteria) {
        Specification<CLSTATF> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CLSTATF_.id));
            }
            if (criteria.getOpzione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpzione(), CLSTATF_.opzione));
            }
            if (criteria.getDescrizione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescrizione(), CLSTATF_.descrizione));
            }
        }
        return specification;
    }
}
