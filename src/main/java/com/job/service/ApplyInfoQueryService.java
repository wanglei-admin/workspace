package com.job.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.job.domain.ApplyInfo;
import com.job.domain.*; // for static metamodels
import com.job.repository.ApplyInfoRepository;
import com.job.service.dto.ApplyInfoCriteria;

/**
 * Service for executing complex queries for {@link ApplyInfo} entities in the database.
 * The main input is a {@link ApplyInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplyInfo} or a {@link Page} of {@link ApplyInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplyInfoQueryService extends QueryService<ApplyInfo> {

    private final Logger log = LoggerFactory.getLogger(ApplyInfoQueryService.class);

    private final ApplyInfoRepository applyInfoRepository;

    public ApplyInfoQueryService(ApplyInfoRepository applyInfoRepository) {
        this.applyInfoRepository = applyInfoRepository;
    }

    /**
     * Return a {@link List} of {@link ApplyInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplyInfo> findByCriteria(ApplyInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplyInfo> specification = createSpecification(criteria);
        return applyInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApplyInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplyInfo> findByCriteria(ApplyInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplyInfo> specification = createSpecification(criteria);
        return applyInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplyInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplyInfo> specification = createSpecification(criteria);
        return applyInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplyInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplyInfo> createSpecification(ApplyInfoCriteria criteria) {
        Specification<ApplyInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplyInfo_.id));
            }
            if (criteria.getUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUser(), ApplyInfo_.user));
            }
            if (criteria.getPost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPost(), ApplyInfo_.post));
            }
            if (criteria.getExpCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpCity(), ApplyInfo_.expCity));
            }
            if (criteria.getExpSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpSalary(), ApplyInfo_.expSalary));
            }
            if (criteria.getSubmitTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmitTime(), ApplyInfo_.submitTime));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ApplyInfo_.status));
            }
            if (criteria.getEducation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEducation(), ApplyInfo_.education));
            }
            if (criteria.getSpecialty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecialty(), ApplyInfo_.specialty));
            }
            if (criteria.getGraduation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGraduation(), ApplyInfo_.graduation));
            }
        }
        return specification;
    }
}
