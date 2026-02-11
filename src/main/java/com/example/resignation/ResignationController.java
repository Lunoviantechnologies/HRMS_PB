package com.example.resignation;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;

@RestController
@RequestMapping("/api/resignations")
public class ResignationController {

    @Autowired private ResignationService service;
    @Autowired private EmployeeRepository employeeRepo;

    // Employee
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/submit")
    public ResponseEntity<?> submit(
            @RequestParam String email,
            @RequestParam String lastWorkingDay,
            @RequestParam String reason) {

        Employee emp = employeeRepo.findByEmailId(email).orElseThrow();
        service.submit(emp, LocalDate.parse(lastWorkingDay), reason);
        return ResponseEntity.ok("Resignation submitted");
    }

    

    // HR
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/hr-approval/{id}")
    public ResponseEntity<?> hrApprove(
            @PathVariable Long id,
            @RequestParam String hrEmail) {

        service.hrApprove(id, hrEmail);
        return ResponseEntity.ok("HR approved");
    }
}
