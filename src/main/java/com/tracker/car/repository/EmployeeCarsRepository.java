package com.tracker.car.repository;

import com.tracker.car.domain.EmployeeCars;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeCars entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeCarsRepository extends JpaRepository<EmployeeCars, Long> {

}
