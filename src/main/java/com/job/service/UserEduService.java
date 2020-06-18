package com.job.service;

import com.job.domain.UserEdu;
import com.job.repository.UserEduRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserEdu}.
 */
@Service
@Transactional
public class UserEduService {

    private final Logger log = LoggerFactory.getLogger(UserEduService.class);

    private final UserEduRepository userEduRepository;

    public UserEduService(UserEduRepository userEduRepository) {
        this.userEduRepository = userEduRepository;
    }

    /**
     * Save a userEdu.
     *
     * @param userEdu the entity to save.
     * @return the persisted entity.
     */
    public UserEdu save(UserEdu userEdu) {
        log.debug("Request to save UserEdu : {}", userEdu);
        return userEduRepository.save(userEdu);
    }

    /**
     * Get all the userEdus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserEdu> findAll(Pageable pageable) {
        log.debug("Request to get all UserEdus");
        return userEduRepository.findAll(pageable);
    }


    /**
     * Get one userEdu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserEdu> findOne(Long id) {
        log.debug("Request to get UserEdu : {}", id);
        return userEduRepository.findById(id);
    }

    /**
     * Delete the userEdu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserEdu : {}", id);
        userEduRepository.deleteById(id);
    }
}
