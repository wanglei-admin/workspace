package com.job.service;

import com.job.domain.InviteInfo;
import com.job.repository.InviteInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InviteInfo}.
 */
@Service
@Transactional
public class InviteInfoService {

    private final Logger log = LoggerFactory.getLogger(InviteInfoService.class);

    private final InviteInfoRepository inviteInfoRepository;

    public InviteInfoService(InviteInfoRepository inviteInfoRepository) {
        this.inviteInfoRepository = inviteInfoRepository;
    }

    /**
     * Save a inviteInfo.
     *
     * @param inviteInfo the entity to save.
     * @return the persisted entity.
     */
    public InviteInfo save(InviteInfo inviteInfo) {
        log.debug("Request to save InviteInfo : {}", inviteInfo);
        return inviteInfoRepository.save(inviteInfo);
    }

    /**
     * Get all the inviteInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InviteInfo> findAll(Pageable pageable) {
        log.debug("Request to get all InviteInfos");
        return inviteInfoRepository.findAll(pageable);
    }


    /**
     * Get one inviteInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InviteInfo> findOne(Long id) {
        log.debug("Request to get InviteInfo : {}", id);
        return inviteInfoRepository.findById(id);
    }

    /**
     * Delete the inviteInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InviteInfo : {}", id);
        inviteInfoRepository.deleteById(id);
    }
}
