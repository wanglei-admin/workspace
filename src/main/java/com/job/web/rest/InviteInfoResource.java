package com.job.web.rest;

import com.job.domain.InviteInfo;
import com.job.service.InviteInfoService;
import com.job.web.rest.errors.BadRequestAlertException;
import com.job.service.dto.InviteInfoCriteria;
import com.job.service.InviteInfoQueryService;

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
 * REST controller for managing {@link com.job.domain.InviteInfo}.
 */
@RestController
@RequestMapping("/api")
public class InviteInfoResource {

    private final Logger log = LoggerFactory.getLogger(InviteInfoResource.class);

    private static final String ENTITY_NAME = "inviteInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InviteInfoService inviteInfoService;

    private final InviteInfoQueryService inviteInfoQueryService;

    public InviteInfoResource(InviteInfoService inviteInfoService, InviteInfoQueryService inviteInfoQueryService) {
        this.inviteInfoService = inviteInfoService;
        this.inviteInfoQueryService = inviteInfoQueryService;
    }

    /**
     * {@code POST  /invite-infos} : Create a new inviteInfo.
     *
     * @param inviteInfo the inviteInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inviteInfo, or with status {@code 400 (Bad Request)} if the inviteInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invite-infos")
    public ResponseEntity<InviteInfo> createInviteInfo(@RequestBody InviteInfo inviteInfo) throws URISyntaxException {
        log.debug("REST request to save InviteInfo : {}", inviteInfo);
        if (inviteInfo.getId() != null) {
            throw new BadRequestAlertException("A new inviteInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InviteInfo result = inviteInfoService.save(inviteInfo);
        return ResponseEntity.created(new URI("/api/invite-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invite-infos} : Updates an existing inviteInfo.
     *
     * @param inviteInfo the inviteInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inviteInfo,
     * or with status {@code 400 (Bad Request)} if the inviteInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inviteInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invite-infos")
    public ResponseEntity<InviteInfo> updateInviteInfo(@RequestBody InviteInfo inviteInfo) throws URISyntaxException {
        log.debug("REST request to update InviteInfo : {}", inviteInfo);
        if (inviteInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InviteInfo result = inviteInfoService.save(inviteInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inviteInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invite-infos} : get all the inviteInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inviteInfos in body.
     */
    @GetMapping("/invite-infos")
    public ResponseEntity<List<InviteInfo>> getAllInviteInfos(InviteInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InviteInfos by criteria: {}", criteria);
        Page<InviteInfo> page = inviteInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invite-infos/count} : count all the inviteInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/invite-infos/count")
    public ResponseEntity<Long> countInviteInfos(InviteInfoCriteria criteria) {
        log.debug("REST request to count InviteInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(inviteInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invite-infos/:id} : get the "id" inviteInfo.
     *
     * @param id the id of the inviteInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inviteInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invite-infos/{id}")
    public ResponseEntity<InviteInfo> getInviteInfo(@PathVariable Long id) {
        log.debug("REST request to get InviteInfo : {}", id);
        Optional<InviteInfo> inviteInfo = inviteInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inviteInfo);
    }

    /**
     * {@code DELETE  /invite-infos/:id} : delete the "id" inviteInfo.
     *
     * @param id the id of the inviteInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invite-infos/{id}")
    public ResponseEntity<Void> deleteInviteInfo(@PathVariable Long id) {
        log.debug("REST request to delete InviteInfo : {}", id);
        inviteInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
