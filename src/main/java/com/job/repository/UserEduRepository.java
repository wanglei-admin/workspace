package com.job.repository;

import com.job.domain.UserEdu;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserEdu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserEduRepository extends JpaRepository<UserEdu, Long>, JpaSpecificationExecutor<UserEdu> {
}
