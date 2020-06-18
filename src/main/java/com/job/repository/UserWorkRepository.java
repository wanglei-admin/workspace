package com.job.repository;

import com.job.domain.UserWork;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserWork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserWorkRepository extends JpaRepository<UserWork, Long>, JpaSpecificationExecutor<UserWork> {
}
