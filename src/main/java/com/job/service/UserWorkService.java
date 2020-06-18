package com.job.service;

import com.job.domain.UserWork;
import com.job.repository.UserWorkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserWork}.
 */
@Service
@Transactional
public class UserWorkService {

    private final Logger log = LoggerFactory.getLogger(UserWorkService.class);

    private final UserWorkRepository userWorkRepository;

    public UserWorkService(UserWorkRepository userWorkRepository) {
        this.userWorkRepository = userWorkRepository;
    }

    /**
     * Save a userWork.
     *
     * @param userWork the entity to save.
     * @return the persisted entity.
     */
    public UserWork save(UserWork userWork) {
        log.debug("Request to save UserWork : {}", userWork);
        return userWorkRepository.save(userWork);
    }

    /**
     * Get all the userWorks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserWork> findAll(Pageable pageable) {
        log.debug("Request to get all UserWorks");
        return userWorkRepository.findAll(pageable);
    }


    /**
     * Get one userWork by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserWork> findOne(Long id) {
        log.debug("Request to get UserWork : {}", id);
        return userWorkRepository.findById(id);
    }

    /**
     * Delete the userWork by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWork : {}", id);
        userWorkRepository.deleteById(id);
    }
}
