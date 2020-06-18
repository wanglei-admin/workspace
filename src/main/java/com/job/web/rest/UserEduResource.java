package com.job.web.rest;

import com.job.domain.UserEdu;
import com.job.service.UserEduService;
import com.job.web.rest.errors.BadRequestAlertException;
import com.job.service.dto.UserEduCriteria;
import com.job.service.UserEduQueryService;

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
 * REST controller for managing {@link com.job.domain.UserEdu}.
 */
@RestController
@RequestMapping("/api")
public class UserEduResource {

    private final Logger log = LoggerFactory.getLogger(UserEduResource.class);

    private static final String ENTITY_NAME = "userEdu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserEduService userEduService;

    private final UserEduQueryService userEduQueryService;

    public UserEduResource(UserEduService userEduService, UserEduQueryService userEduQueryService) {
        this.userEduService = userEduService;
        this.userEduQueryService = userEduQueryService;
    }

    /**
     * {@code POST  /user-edus} : Create a new userEdu.
     *
     * @param userEdu the userEdu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userEdu, or with status {@code 400 (Bad Request)} if the userEdu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-edus")
    public ResponseEntity<UserEdu> createUserEdu(@RequestBody UserEdu userEdu) throws URISyntaxException {
        log.debug("REST request to save UserEdu : {}", userEdu);
        if (userEdu.getId() != null) {
            throw new BadRequestAlertException("A new userEdu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserEdu result = userEduService.save(userEdu);
        return ResponseEntity.created(new URI("/api/user-edus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-edus} : Updates an existing userEdu.
     *
     * @param userEdu the userEdu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userEdu,
     * or with status {@code 400 (Bad Request)} if the userEdu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userEdu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-edus")
    public ResponseEntity<UserEdu> updateUserEdu(@RequestBody UserEdu userEdu) throws URISyntaxException {
        log.debug("REST request to update UserEdu : {}", userEdu);
        if (userEdu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserEdu result = userEduService.save(userEdu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userEdu.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-edus} : get all the userEdus.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userEdus in body.
     */
    @GetMapping("/user-edus")
    public ResponseEntity<List<UserEdu>> getAllUserEdus(UserEduCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserEdus by criteria: {}", criteria);
        Page<UserEdu> page = userEduQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-edus/count} : count all the userEdus.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-edus/count")
    public ResponseEntity<Long> countUserEdus(UserEduCriteria criteria) {
        log.debug("REST request to count UserEdus by criteria: {}", criteria);
        return ResponseEntity.ok().body(userEduQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-edus/:id} : get the "id" userEdu.
     *
     * @param id the id of the userEdu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userEdu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-edus/{id}")
    public ResponseEntity<UserEdu> getUserEdu(@PathVariable Long id) {
        log.debug("REST request to get UserEdu : {}", id);
        Optional<UserEdu> userEdu = userEduService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userEdu);
    }

    /**
     * {@code DELETE  /user-edus/:id} : delete the "id" userEdu.
     *
     * @param id the id of the userEdu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-edus/{id}")
    public ResponseEntity<Void> deleteUserEdu(@PathVariable Long id) {
        log.debug("REST request to delete UserEdu : {}", id);
        userEduService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
