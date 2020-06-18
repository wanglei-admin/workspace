package com.job.repository;

import com.job.domain.ApplyInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplyInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplyInfoRepository extends JpaRepository<ApplyInfo, Long>, JpaSpecificationExecutor<ApplyInfo> {
}
