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

import com.job.domain.UserWork;
import com.job.domain.*; // for static metamodels
import com.job.repository.UserWorkRepository;
import com.job.service.dto.UserWorkCriteria;

/**
 * Service for executing complex queries for {@link UserWork} entities in the database.
 * The main input is a {@link UserWorkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserWork} or a {@link Page} of {@link UserWork} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserWorkQueryService extends QueryService<UserWork> {

    private final Logger log = LoggerFactory.getLogger(UserWorkQueryService.class);

    private final UserWorkRepository userWorkRepository;

    public UserWorkQueryService(UserWorkRepository userWorkRepository) {
        this.userWorkRepository = userWorkRepository;
    }

    /**
     * Return a {@link List} of {@link UserWork} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserWork> findByCriteria(UserWorkCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserWork> specification = createSpecification(criteria);
        return userWorkRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserWork} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserWork> findByCriteria(UserWorkCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserWork> specification = createSpecification(criteria);
        return userWorkRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserWorkCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserWork> specification = createSpecification(criteria);
        return userWorkRepository.count(specification);
    }

    /**
     * Function to convert {@link UserWorkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserWork> createSpecification(UserWorkCriteria criteria) {
        Specification<UserWork> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserWork_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), UserWork_.login));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), UserWork_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), UserWork_.endDate));
            }
            if (criteria.getCompany() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompany(), UserWork_.company));
            }
            if (criteria.getPost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPost(), UserWork_.post));
            }
            if (criteria.getSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalary(), UserWork_.salary));
            }
        }
        return specification;
    }
}
