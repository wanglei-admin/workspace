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

import com.job.domain.UserEdu;
import com.job.domain.*; // for static metamodels
import com.job.repository.UserEduRepository;
import com.job.service.dto.UserEduCriteria;

/**
 * Service for executing complex queries for {@link UserEdu} entities in the database.
 * The main input is a {@link UserEduCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserEdu} or a {@link Page} of {@link UserEdu} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserEduQueryService extends QueryService<UserEdu> {

    private final Logger log = LoggerFactory.getLogger(UserEduQueryService.class);

    private final UserEduRepository userEduRepository;

    public UserEduQueryService(UserEduRepository userEduRepository) {
        this.userEduRepository = userEduRepository;
    }

    /**
     * Return a {@link List} of {@link UserEdu} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserEdu> findByCriteria(UserEduCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserEdu> specification = createSpecification(criteria);
        return userEduRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserEdu} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserEdu> findByCriteria(UserEduCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserEdu> specification = createSpecification(criteria);
        return userEduRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserEduCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserEdu> specification = createSpecification(criteria);
        return userEduRepository.count(specification);
    }

    /**
     * Function to convert {@link UserEduCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserEdu> createSpecification(UserEduCriteria criteria) {
        Specification<UserEdu> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserEdu_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), UserEdu_.login));
            }
            if (criteria.getGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrade(), UserEdu_.grade));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), UserEdu_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), UserEdu_.endDate));
            }
            if (criteria.getSchool() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchool(), UserEdu_.school));
            }
        }
        return specification;
    }
}
