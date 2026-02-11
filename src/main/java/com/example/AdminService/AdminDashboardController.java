package com.example.AdminService;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    //  All employees
    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(adminDashboardService.getAllEmployees());
    }

    //  Attendance by employee + date range
    @GetMapping("/attendance/{employeeId}")
    public ResponseEntity<?> getAttendance(
            @PathVariable Long employeeId,
            @RequestParam String fromDate,
            @RequestParam String toDate) {

        return ResponseEntity.ok(
                adminDashboardService.getAttendance(
                        employeeId,
                        LocalDate.parse(fromDate),
                        LocalDate.parse(toDate))
        );
    }

    //  Payslip history (employee)
    @GetMapping("/payslips/{employeeId}")
    public ResponseEntity<?> getPayslips(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                adminDashboardService.getPayslips(employeeId)
        );
    }

    //  Monthly payroll (ALL employees)
    @GetMapping("/payroll")
    public ResponseEntity<?> getMonthlyPayroll(
            @RequestParam int month,
            @RequestParam int year) {

        return ResponseEntity.ok(
                adminDashboardService.getMonthlyPayroll(month, year)
        );
    }
}
