package com.micro.app.web.rest;

import com.micro.app.domain.EmployeeInfo;
import com.micro.app.service.EmployeeInfoService;
import com.micro.app.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.micro.app.domain.EmployeeInfo}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeInfoResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeInfoResource.class);

    private static final String ENTITY_NAME = "employeeInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeInfoService employeeInfoService;

    public EmployeeInfoResource(EmployeeInfoService employeeInfoService) {
        this.employeeInfoService = employeeInfoService;
    }

    /**
     * {@code POST  /employee-infos} : Create a new employeeInfo.
     *
     * @param employeeInfo the employeeInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeInfo, or with status {@code 400 (Bad Request)} if the employeeInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-infos")
    public ResponseEntity<EmployeeInfo> createEmployeeInfo(@RequestBody EmployeeInfo employeeInfo) throws URISyntaxException {
        log.debug("REST request to save EmployeeInfo : {}", employeeInfo);
        if (employeeInfo.getId() != null) {
            throw new BadRequestAlertException("A new employeeInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeInfo result = employeeInfoService.save(employeeInfo);
        return ResponseEntity.created(new URI("/api/employee-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-infos} : Updates an existing employeeInfo.
     *
     * @param employeeInfo the employeeInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeInfo,
     * or with status {@code 400 (Bad Request)} if the employeeInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-infos")
    public ResponseEntity<EmployeeInfo> updateEmployeeInfo(@RequestBody EmployeeInfo employeeInfo) throws URISyntaxException {
        log.debug("REST request to update EmployeeInfo : {}", employeeInfo);
        if (employeeInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeInfo result = employeeInfoService.save(employeeInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-infos} : get all the employeeInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeInfos in body.
     */
    @GetMapping("/employee-infos")
    public ResponseEntity<List<EmployeeInfo>> getAllEmployeeInfos(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeInfos");
        Page<EmployeeInfo> page = employeeInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-infos/:id} : get the "id" employeeInfo.
     *
     * @param id the id of the employeeInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-infos/{id}")
    public ResponseEntity<EmployeeInfo> getEmployeeInfo(@PathVariable Long id) {
        log.debug("REST request to get EmployeeInfo : {}", id);
        Optional<EmployeeInfo> employeeInfo = employeeInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeInfo);
    }

    /**
     * {@code DELETE  /employee-infos/:id} : delete the "id" employeeInfo.
     *
     * @param id the id of the employeeInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-infos/{id}")
    public ResponseEntity<Void> deleteEmployeeInfo(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeInfo : {}", id);
        employeeInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
