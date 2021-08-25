package it.adastra.profilglass.configuratore.service;

import it.adastra.profilglass.configuratore.domain.*; // for static metamodels
import it.adastra.profilglass.configuratore.domain.Parameters;
import it.adastra.profilglass.configuratore.repository.ParametersRepository;
import it.adastra.profilglass.configuratore.service.criteria.ParametersCriteria;
import it.adastra.profilglass.configuratore.service.dto.ParametersDTO;
import it.adastra.profilglass.configuratore.service.mapper.ParametersMapper;
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
 * Service for executing complex queries for {@link Parameters} entities in the database.
 * The main input is a {@link ParametersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParametersDTO} or a {@link Page} of {@link ParametersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParametersQueryService extends QueryService<Parameters> {

    private final Logger log = LoggerFactory.getLogger(ParametersQueryService.class);

    private final ParametersRepository parametersRepository;

    private final ParametersMapper parametersMapper;

    public ParametersQueryService(ParametersRepository parametersRepository, ParametersMapper parametersMapper) {
        this.parametersRepository = parametersRepository;
        this.parametersMapper = parametersMapper;
    }

    /**
     * Return a {@link List} of {@link ParametersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParametersDTO> findByCriteria(ParametersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Parameters> specification = createSpecification(criteria);
        return parametersMapper.toDto(parametersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParametersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParametersDTO> findByCriteria(ParametersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Parameters> specification = createSpecification(criteria);
        return parametersRepository.findAll(specification, page).map(parametersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParametersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Parameters> specification = createSpecification(criteria);
        return parametersRepository.count(specification);
    }

    /**
     * Function to convert {@link ParametersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Parameters> createSpecification(ParametersCriteria criteria) {
        Specification<Parameters> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Parameters_.id));
            }
            if (criteria.getKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKey(), Parameters_.key));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), Parameters_.value));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Parameters_.description));
            }
        }
        return specification;
    }
}
