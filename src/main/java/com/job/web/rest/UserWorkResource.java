package com.job.web.rest;

import com.job.domain.UserWork;
import com.job.service.UserWorkService;
import com.job.web.rest.errors.BadRequestAlertException;
import com.job.service.dto.UserWorkCriteria;
import com.job.service.UserWorkQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.job.domain.UserWork}.
 */
@RestController
@RequestMapping("/api")
public class UserWorkResource {

    private final Logger log = LoggerFactory.getLogger(UserWorkResource.class);

    private static final String ENTITY_NAME = "userWork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserWorkService userWorkService;

    private final UserWorkQueryService userWorkQueryService;

    public UserWorkResource(UserWorkService userWorkService, UserWorkQueryService userWorkQueryService) {
        this.userWorkService = userWorkService;
        this.userWorkQueryService = userWorkQueryService;
    }

    /**
     * {@code POST  /user-works} : Create a new userWork.
     *
     * @param userWork the userWork to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userWork, or with status {@code 400 (Bad Request)} if the userWork has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-works")
    public ResponseEntity<UserWork> createUserWork(@RequestBody UserWork userWork) throws URISyntaxException {
        log.debug("REST request to save UserWork : {}", userWork);
        if (userWork.getId() != null) {
            throw new BadRequestAlertException("A new userWork cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserWork result = userWorkService.save(userWork);
        return ResponseEntity.created(new URI("/api/user-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-works} : Updates an existing userWork.
     *
     * @param userWork the userWork to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userWork,
     * or with status {@code 400 (Bad Request)} if the userWork is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userWork couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-works")
    public ResponseEntity<UserWork> updateUserWork(@RequestBody UserWork userWork) throws URISyntaxException {
        log.debug("REST request to update UserWork : {}", userWork);
        if (userWork.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserWork result = userWorkService.save(userWork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userWork.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-works} : get all the userWorks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userWorks in body.
     */
    @GetMapping("/user-works")
    public ResponseEntity<List<UserWork>> getAllUserWorks(UserWorkCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserWorks by criteria: {}", criteria);
        Page<UserWork> page = userWorkQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-works/count} : count all the userWorks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-works/count")
    public ResponseEntity<Long> countUserWorks(UserWorkCriteria criteria) {
        log.debug("REST request to count UserWorks by criteria: {}", criteria);
        return ResponseEntity.ok().body(userWorkQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-works/:id} : get the "id" userWork.
     *
     * @param id the id of the userWork to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userWork, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-works/{id}")
    public ResponseEntity<UserWork> getUserWork(@PathVariable Long id) {
        log.debug("REST request to get UserWork : {}", id);
        Optional<UserWork> userWork = userWorkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userWork);
    }

    /**
     * {@code DELETE  /user-works/:id} : delete the "id" userWork.
     *
     * @param id the id of the userWork to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-works/{id}")
    public ResponseEntity<Void> deleteUserWork(@PathVariable Long id) {
        log.debug("REST request to delete UserWork : {}", id);
        userWorkService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
