package com.example.ArchievedEmployees;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivedEmployeeRepository extends JpaRepository<ArchivedEmployee, Long>{
	
	Optional<ArchivedEmployee> findByOriginalEmployeeId(Long originalEmployeeId);

	
}
