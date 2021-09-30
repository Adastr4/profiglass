package it.adastra.profilglass.configuratore.service;

// for static metamodels
import it.adastra.profilglass.configuratore.domain.CLLEGA;
import it.adastra.profilglass.configuratore.domain.CLLEGA_;
import it.adastra.profilglass.configuratore.repository.CLLEGARepository;
import it.adastra.profilglass.configuratore.service.criteria.CLLEGACriteria;
import it.adastra.profilglass.configuratore.service.dto.CLLEGADTO;
import it.adastra.profilglass.configuratore.service.mapper.CLLEGAMapper;
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
 * Service for executing complex queries for {@link CLLEGA} entities in the database.
 * The main input is a {@link CLLEGACriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CLLEGADTO} or a {@link Page} of {@link CLLEGADTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CLLEGAQueryService extends QueryService<CLLEGA> {

    private final Logger log = LoggerFactory.getLogger(CLLEGAQueryService.class);

    private final CLLEGARepository cLLEGARepository;

    private final CLLEGAMapper cLLEGAMapper;

    public CLLEGAQueryService(CLLEGARepository cLLEGARepository, CLLEGAMapper cLLEGAMapper) {
        this.cLLEGARepository = cLLEGARepository;
        this.cLLEGAMapper = cLLEGAMapper;
    }

    /**
     * Return a {@link List} of {@link CLLEGADTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CLLEGADTO> findByCriteria(CLLEGACriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CLLEGA> specification = createSpecification(criteria);
        return cLLEGAMapper.toDto(cLLEGARepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CLLEGADTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CLLEGADTO> findByCriteria(CLLEGACriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CLLEGA> specification = createSpecification(criteria);
        return cLLEGARepository.findAll(specification, page).map(cLLEGAMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CLLEGACriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CLLEGA> specification = createSpecification(criteria);
        return cLLEGARepository.count(specification);
    }

    /**
     * Function to convert {@link CLLEGACriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CLLEGA> createSpecification(CLLEGACriteria criteria) {
        Specification<CLLEGA> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CLLEGA_.id));
            }
            if (criteria.getOpzione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpzione(), CLLEGA_.opzione));
            }
            if (criteria.getDescrizione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescrizione(), CLLEGA_.descrizione));
            }
        }
        return specification;
    }
}
