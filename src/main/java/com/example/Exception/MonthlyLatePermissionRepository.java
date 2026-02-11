package com.example.Exception;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyLatePermissionRepository
        extends JpaRepository<MonthlyLatePermission, Long> {

    Optional<MonthlyLatePermission> findByEmployeeEmailAndYearAndMonth(
            String employeeEmail,
            int year,
            int month
    );
}
