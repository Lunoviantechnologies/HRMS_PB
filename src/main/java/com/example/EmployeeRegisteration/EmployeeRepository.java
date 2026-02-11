package com.example.EmployeeRegisteration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Load employee + leaveRequests in one query (avoids LazyInitializationException)
    @EntityGraph(attributePaths = "leaveRequests")
    Optional<Employee> findById(Long id);

    // Find employee by email (for login / unique check)
    Optional<Employee> findByEmailId(String emailId);
    
    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    
    Page<Employee> findAll(org.springframework.data.domain.Pageable pageable);
    
    List<Employee> findByEmploymentStatusAndLastWorkingDay(
            String employmentStatus,
            LocalDate lastWorkingDay
    );


}
