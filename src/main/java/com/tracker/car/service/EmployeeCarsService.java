package com.tracker.car.service;

import com.tracker.car.domain.EmployeeCars;
import com.tracker.car.repository.EmployeeCarsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeCars}.
 */
@Service
@Transactional
public class EmployeeCarsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeCarsService.class);

    private final EmployeeCarsRepository employeeCarsRepository;

    public EmployeeCarsService(EmployeeCarsRepository employeeCarsRepository) {
        this.employeeCarsRepository = employeeCarsRepository;
    }

    /**
     * Save a employeeCars.
     *
     * @param employeeCars the entity to save.
     * @return the persisted entity.
     */
    public EmployeeCars save(EmployeeCars employeeCars) {
        log.debug("Request to save EmployeeCars : {}", employeeCars);
        return employeeCarsRepository.save(employeeCars);
    }

    /**
     * Get all the employeeCars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeCars> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeCars");
        return employeeCarsRepository.findAll(pageable);
    }


    /**
     * Get one employeeCars by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeCars> findOne(Long id) {
        log.debug("Request to get EmployeeCars : {}", id);
        return employeeCarsRepository.findById(id);
    }

    /**
     * Delete the employeeCars by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeCars : {}", id);
        employeeCarsRepository.deleteById(id);
    }
}
