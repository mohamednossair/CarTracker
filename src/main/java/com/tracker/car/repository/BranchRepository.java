package com.tracker.car.repository;

import com.tracker.car.domain.Branch;
import com.tracker.car.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Branch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
	Optional<Branch> findByCode(String code);

}
