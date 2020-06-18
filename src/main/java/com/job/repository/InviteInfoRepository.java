package com.job.repository;

import com.job.domain.InviteInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InviteInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InviteInfoRepository extends JpaRepository<InviteInfo, Long>, JpaSpecificationExecutor<InviteInfo> {
}
