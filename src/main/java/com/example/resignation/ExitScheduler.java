package com.example.resignation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;
import com.example.ArchievedEmployees.ArchivedEmployee;
import com.example.ArchievedEmployees.ArchivedEmployeeRepository;

import jakarta.transaction.Transactional;

@Component
public class ExitScheduler {

    private final EmployeeRepository employeeRepo;
    private final ArchivedEmployeeRepository archivedRepo;

    // constructor injection (recommended)
    public ExitScheduler(EmployeeRepository employeeRepo,
                         ArchivedEmployeeRepository archivedRepo) {
        this.employeeRepo = employeeRepo;
        this.archivedRepo = archivedRepo;
    }

    // Runs daily at 12:05 AM
    @Scheduled(cron = "0 5 0 * * ?")
    @Transactional
    public void autoExit() {

        LocalDate today = LocalDate.now();

        List<Employee> exitingEmployees =
                employeeRepo.findByEmploymentStatusAndLastWorkingDay(
                        "NOTICE_PERIOD", today);

        for (Employee emp : exitingEmployees) {

            boolean alreadyArchived =
                    archivedRepo.findByOriginalEmployeeId(emp.getId()).isPresent();

            if (!alreadyArchived) {

                ArchivedEmployee archive = new ArchivedEmployee();
                archive.setOriginalEmployeeId(emp.getId());
                archive.setFirstName(emp.getFirstName());
                archive.setLastName(emp.getLastName());
                archive.setEmailId(emp.getEmailId());
                archive.setContactNumber1(emp.getContactNumber1());
                archive.setDepartment(emp.getDepartment());
                archive.setDesignation(emp.getDesignation());
                archive.setJoiningDate(emp.getJoiningDate());
                archive.setDateOfBirth(emp.getDateOfBirth());
                archive.setProfilePhoto(emp.getProfilePhoto());
                archive.setDeletedAt(today);

                archivedRepo.save(archive);
            }

            // anonymize
            emp.setPassword("ARCHIVED");
            emp.setContactNumber1(null);
            emp.setEmailId("archived_" + emp.getId() + "@company.local");

            // final exit
            emp.setEmploymentStatus("EXITED");

            employeeRepo.save(emp);
        }
    }
}
