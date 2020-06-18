package com.job.service;

import com.job.domain.ApplyInfo;
import com.job.repository.ApplyInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplyInfo}.
 */
@Service
@Transactional
public class ApplyInfoService {

    private final Logger log = LoggerFactory.getLogger(ApplyInfoService.class);

    private final ApplyInfoRepository applyInfoRepository;

    public ApplyInfoService(ApplyInfoRepository applyInfoRepository) {
        this.applyInfoRepository = applyInfoRepository;
    }

    /**
     * Save a applyInfo.
     *
     * @param applyInfo the entity to save.
     * @return the persisted entity.
     */
    public ApplyInfo save(ApplyInfo applyInfo) {
        log.debug("Request to save ApplyInfo : {}", applyInfo);
        return applyInfoRepository.save(applyInfo);
    }

    /**
     * Get all the applyInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplyInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ApplyInfos");
        return applyInfoRepository.findAll(pageable);
    }


    /**
     * Get one applyInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplyInfo> findOne(Long id) {
        log.debug("Request to get ApplyInfo : {}", id);
        return applyInfoRepository.findById(id);
    }

    /**
     * Delete the applyInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplyInfo : {}", id);
        applyInfoRepository.deleteById(id);
    }
}
