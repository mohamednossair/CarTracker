package com.tracker.car.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.car.domain.Company;
import com.tracker.car.repository.CompanyRepository;
import com.tracker.car.service.CompanyService;
import com.tracker.car.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tracker.car.domain.Company}.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

	private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

	private static final String ENTITY_NAME = "company";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final CompanyService companyService;
	private final CompanyRepository companyRepository;

	public CompanyResource(CompanyService companyService, CompanyRepository companyRepository) {
		this.companyService = companyService;
		this.companyRepository = companyRepository;
	}

	/**
	 * {@code POST  /companies} : Create a new company.
	 *
	 * @param company the company to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new company, or with status {@code 400 (Bad Request)} if the
	 *         company has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/companies")
	public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) throws URISyntaxException {
		log.debug("REST request to save Company : {}", company);
		if (company.getId() != null) {
			throw new BadRequestAlertException("A new company cannot already have an ID", ENTITY_NAME, "idexists");
		}
		if (companyRepository.findByCode(company.getCode()).isPresent()) {
			throw new BadRequestAlertException("A new company code already used", ENTITY_NAME, "codeexists");
		}
		Company result = companyService.save(company);
		return ResponseEntity
				.created(new URI("/api/companies/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /companies} : Updates an existing company.
	 *
	 * @param company the company to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated company, or with status {@code 400 (Bad Request)} if the
	 *         company is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the company couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/companies")
	public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) throws URISyntaxException {
		log.debug("REST request to update Company : {}", company);
		if (company.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (companyRepository.findByCode(company.getCode()).filter(b -> b.getId() != company.getId()).isPresent()) {
			throw new BadRequestAlertException("A company code already used", ENTITY_NAME, "codeexists");
		}
		Company result = companyService.save(company);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, company.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /companies} : get all the companies.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of companies in body.
	 */
	@GetMapping("/companies")
	public List<Company> getAllCompanies() {
		log.debug("REST request to get all Companies");
		return companyService.findAll();
	}

	/**
	 * {@code GET  /companies/:id} : get the "id" company.
	 *
	 * @param id the id of the company to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the company, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/companies/{id}")
	public ResponseEntity<Company> getCompany(@PathVariable Long id) {
		log.debug("REST request to get Company : {}", id);
		Optional<Company> company = companyService.findOne(id);
		return ResponseUtil.wrapOrNotFound(company);
	}

	/**
	 * {@code DELETE  /companies/:id} : delete the "id" company.
	 *
	 * @param id the id of the company to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/companies/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
		log.debug("REST request to delete Company : {}", id);
		companyService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
