package com.tracker.car.web.rest;

import com.tracker.car.domain.EmployeeCars;
import com.tracker.car.service.EmployeeCarsService;
import com.tracker.car.web.rest.errors.BadRequestAlertException;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tracker.car.domain.EmployeeCars}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeCarsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeCarsResource.class);

    private static final String ENTITY_NAME = "employeeCars";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeCarsService employeeCarsService;

    public EmployeeCarsResource(EmployeeCarsService employeeCarsService) {
        this.employeeCarsService = employeeCarsService;
    }

    /**
     * {@code POST  /employee-cars} : Create a new employeeCars.
     *
     * @param employeeCars the employeeCars to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeCars, or with status {@code 400 (Bad Request)} if the employeeCars has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-cars")
    public ResponseEntity<EmployeeCars> createEmployeeCars(@Valid @RequestBody EmployeeCars employeeCars) throws URISyntaxException {
        log.debug("REST request to save EmployeeCars : {}", employeeCars);
        if (employeeCars.getId() != null) {
            throw new BadRequestAlertException("A new employeeCars cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeCars result = employeeCarsService.save(employeeCars);
        return ResponseEntity.created(new URI("/api/employee-cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-cars} : Updates an existing employeeCars.
     *
     * @param employeeCars the employeeCars to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeCars,
     * or with status {@code 400 (Bad Request)} if the employeeCars is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeCars couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-cars")
    public ResponseEntity<EmployeeCars> updateEmployeeCars(@Valid @RequestBody EmployeeCars employeeCars) throws URISyntaxException {
        log.debug("REST request to update EmployeeCars : {}", employeeCars);
        if (employeeCars.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeCars result = employeeCarsService.save(employeeCars);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeCars.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-cars} : get all the employeeCars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeCars in body.
     */
    @GetMapping("/employee-cars")
    public ResponseEntity<List<EmployeeCars>> getAllEmployeeCars(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of EmployeeCars");
        Page<EmployeeCars> page = employeeCarsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-cars/:id} : get the "id" employeeCars.
     *
     * @param id the id of the employeeCars to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeCars, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-cars/{id}")
    public ResponseEntity<EmployeeCars> getEmployeeCars(@PathVariable Long id) {
        log.debug("REST request to get EmployeeCars : {}", id);
        Optional<EmployeeCars> employeeCars = employeeCarsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeCars);
    }

    /**
     * {@code DELETE  /employee-cars/:id} : delete the "id" employeeCars.
     *
     * @param id the id of the employeeCars to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-cars/{id}")
    public ResponseEntity<Void> deleteEmployeeCars(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeCars : {}", id);
        employeeCarsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
