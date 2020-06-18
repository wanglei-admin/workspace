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

import com.job.domain.InviteInfo;
import com.job.domain.*; // for static metamodels
import com.job.repository.InviteInfoRepository;
import com.job.service.dto.InviteInfoCriteria;

/**
 * Service for executing complex queries for {@link InviteInfo} entities in the database.
 * The main input is a {@link InviteInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InviteInfo} or a {@link Page} of {@link InviteInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InviteInfoQueryService extends QueryService<InviteInfo> {

    private final Logger log = LoggerFactory.getLogger(InviteInfoQueryService.class);

    private final InviteInfoRepository inviteInfoRepository;

    public InviteInfoQueryService(InviteInfoRepository inviteInfoRepository) {
        this.inviteInfoRepository = inviteInfoRepository;
    }

    /**
     * Return a {@link List} of {@link InviteInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InviteInfo> findByCriteria(InviteInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InviteInfo> specification = createSpecification(criteria);
        return inviteInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InviteInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InviteInfo> findByCriteria(InviteInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InviteInfo> specification = createSpecification(criteria);
        return inviteInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InviteInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InviteInfo> specification = createSpecification(criteria);
        return inviteInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link InviteInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InviteInfo> createSpecification(InviteInfoCriteria criteria) {
        Specification<InviteInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InviteInfo_.id));
            }
            if (criteria.getUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUser(), InviteInfo_.user));
            }
            if (criteria.getPost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPost(), InviteInfo_.post));
            }
            if (criteria.getWorkCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkCity(), InviteInfo_.workCity));
            }
            if (criteria.getMinSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinSalary(), InviteInfo_.minSalary));
            }
            if (criteria.getMaxSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxSalary(), InviteInfo_.maxSalary));
            }
            if (criteria.getEducation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEducation(), InviteInfo_.education));
            }
            if (criteria.getSpecialty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecialty(), InviteInfo_.specialty));
            }
            if (criteria.getGraduation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGraduation(), InviteInfo_.graduation));
            }
            if (criteria.getCompany() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompany(), InviteInfo_.company));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), InviteInfo_.mail));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), InviteInfo_.status));
            }
            if (criteria.getSubmitTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmitTime(), InviteInfo_.submitTime));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), InviteInfo_.remark));
            }
        }
        return specification;
    }
}
