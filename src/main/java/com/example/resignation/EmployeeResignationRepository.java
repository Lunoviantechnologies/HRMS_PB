package com.example.resignation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeResignationRepository extends JpaRepository<EmployeeResignation, Long> {

	Optional<EmployeeResignation> findByEmployeeIdAndStatusIn(Long employeeId, List<String> statuses);
}
