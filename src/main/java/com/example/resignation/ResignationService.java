package com.example.resignation;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResignationService {

    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private EmployeeResignationRepository resignationRepo;

    // 1️⃣ Employee submits resignation
    public void submit(Employee emp, LocalDate lwd, String reason) {

        if (!"ACTIVE".equals(emp.getEmploymentStatus())) {
            throw new IllegalStateException("Invalid state");
        }

        EmployeeResignation r = new EmployeeResignation();
        r.setEmployeeId(emp.getId());
        r.setEmployeeEmail(emp.getEmailId());
        r.setResignationDate(LocalDate.now());
        r.setLastWorkingDay(lwd);
        r.setReason(reason);
        r.setStatus("PENDING");

        emp.setEmploymentStatus("RESIGNATION_SUBMITTED");

        resignationRepo.save(r);
        employeeRepo.save(emp);
    }

    // 2️⃣ Manager approval
    public void managerApprove(Long id, String managerEmail) {

        EmployeeResignation r = resignationRepo.findById(id).orElseThrow();
        Employee emp = employeeRepo.findById(r.getEmployeeId()).orElseThrow();

        if (!"PENDING".equals(r.getStatus())) {
            throw new IllegalStateException("Invalid state");
        }

        r.setStatus("MANAGER_APPROVED");
        r.setManagerEmail(managerEmail);
        emp.setEmploymentStatus("MANAGER_APPROVED");

        resignationRepo.save(r);
        employeeRepo.save(emp);
    }

    // 3️⃣ HR approval
    public void hrApprove(Long id, String hrEmail) {

        EmployeeResignation r = resignationRepo.findById(id).orElseThrow();
        Employee emp = employeeRepo.findById(r.getEmployeeId()).orElseThrow();

        if (!"MANAGER_APPROVED".equals(r.getStatus())) {
            throw new IllegalStateException("Manager approval required");
        }

        r.setStatus("HR_APPROVED");
        r.setHrEmail(hrEmail);

        emp.setEmploymentStatus("NOTICE_PERIOD");
        emp.setResignationDate(r.getResignationDate());
        emp.setLastWorkingDay(r.getLastWorkingDay());

        resignationRepo.save(r);
        employeeRepo.save(emp);
    }
}

