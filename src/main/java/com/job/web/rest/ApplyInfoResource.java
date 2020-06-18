package com.job.web.rest;

import com.job.domain.ApplyInfo;
import com.job.service.ApplyInfoService;
import com.job.web.rest.errors.BadRequestAlertException;
import com.job.service.dto.ApplyInfoCriteria;
import com.job.service.ApplyInfoQueryService;

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
 * REST controller for managing {@link com.job.domain.ApplyInfo}.
 */
@RestController
@RequestMapping("/api")
public class ApplyInfoResource {

    private final Logger log = LoggerFactory.getLogger(ApplyInfoResource.class);

    private static final String ENTITY_NAME = "applyInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplyInfoService applyInfoService;

    private final ApplyInfoQueryService applyInfoQueryService;

    public ApplyInfoResource(ApplyInfoService applyInfoService, ApplyInfoQueryService applyInfoQueryService) {
        this.applyInfoService = applyInfoService;
        this.applyInfoQueryService = applyInfoQueryService;
    }

    /**
     * {@code POST  /apply-infos} : Create a new applyInfo.
     *
     * @param applyInfo the applyInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applyInfo, or with status {@code 400 (Bad Request)} if the applyInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/apply-infos")
    public ResponseEntity<ApplyInfo> createApplyInfo(@RequestBody ApplyInfo applyInfo) throws URISyntaxException {
        log.debug("REST request to save ApplyInfo : {}", applyInfo);
        if (applyInfo.getId() != null) {
            throw new BadRequestAlertException("A new applyInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplyInfo result = applyInfoService.save(applyInfo);
        return ResponseEntity.created(new URI("/api/apply-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /apply-infos} : Updates an existing applyInfo.
     *
     * @param applyInfo the applyInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applyInfo,
     * or with status {@code 400 (Bad Request)} if the applyInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applyInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/apply-infos")
    public ResponseEntity<ApplyInfo> updateApplyInfo(@RequestBody ApplyInfo applyInfo) throws URISyntaxException {
        log.debug("REST request to update ApplyInfo : {}", applyInfo);
        if (applyInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplyInfo result = applyInfoService.save(applyInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applyInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /apply-infos} : get all the applyInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applyInfos in body.
     */
    @GetMapping("/apply-infos")
    public ResponseEntity<List<ApplyInfo>> getAllApplyInfos(ApplyInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplyInfos by criteria: {}", criteria);
        Page<ApplyInfo> page = applyInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apply-infos/count} : count all the applyInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/apply-infos/count")
    public ResponseEntity<Long> countApplyInfos(ApplyInfoCriteria criteria) {
        log.debug("REST request to count ApplyInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(applyInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /apply-infos/:id} : get the "id" applyInfo.
     *
     * @param id the id of the applyInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applyInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/apply-infos/{id}")
    public ResponseEntity<ApplyInfo> getApplyInfo(@PathVariable Long id) {
        log.debug("REST request to get ApplyInfo : {}", id);
        Optional<ApplyInfo> applyInfo = applyInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applyInfo);
    }

    /**
     * {@code DELETE  /apply-infos/:id} : delete the "id" applyInfo.
     *
     * @param id the id of the applyInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/apply-infos/{id}")
    public ResponseEntity<Void> deleteApplyInfo(@PathVariable Long id) {
        log.debug("REST request to delete ApplyInfo : {}", id);
        applyInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
