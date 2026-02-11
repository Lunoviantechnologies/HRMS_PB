package com.example.AdminService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;
import com.example.Exception.Attendance;
import com.example.Exception.AttendanceRepository;
import com.example.PaySlips.Payslip;
import com.example.PaySlips.PayslipRepository;

@Service
public class AdminDashboardService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PayslipRepository payslipRepository;

    // 👤 1. Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // 🕒 2. Get attendance for employee (date range)
    public List<Attendance> getAttendance(
            Long employeeId,
            LocalDate fromDate,
            LocalDate toDate) {

        return attendanceRepository
                .findByEmployee_IdAndAttendanceDateBetween(
                        employeeId, fromDate, toDate);
    }

    // 💰 3. Get payslip history for employee
    public List<Payslip> getPayslips(Long employeeId) {
        return payslipRepository.findByEmployeeId(employeeId);
    }

    // 💰 4. Monthly payroll (ALL employees)
    public List<Payslip> getMonthlyPayroll(int month, int year) {
        return payslipRepository.findByYearAndMonth(year, month);
    }
}

