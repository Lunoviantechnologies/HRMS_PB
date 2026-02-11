package com.example.PaySlips;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;
import com.example.Exception.Attendance;
import com.example.Exception.AttendanceRepository;

import jakarta.transaction.Transactional;
@Service
public class PayslipService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayslipRepository payslipRepository;

    @Transactional
    public Payslip generateOrGetPayslip(Long employeeId, int month, int year) {

        return payslipRepository
                .findByEmployeeIdAndYearAndMonth(employeeId, year, month)
                .orElseGet(() -> generatePayslip(employeeId, month, year));
    }

    private Payslip generatePayslip(Long employeeId, int month, int year) {

        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Attendance> records =
                attendanceRepository.findByEmployee_IdAndAttendanceDateBetween(
                        employeeId, start, end);

        int workingDays = records.size();
        int presentDays = 0;
        int halfDays = 0;

        for (Attendance a : records) {
            if (!"PRESENT".equalsIgnoreCase(a.getStatus())) continue;

            long minutes = a.getWorkingMinutes() != null
                    ? a.getWorkingMinutes()
                    : a.calculateNetWorkingMinutes();

            if (minutes >= PayrollRules.FULL_DAY_MINUTES) {
                presentDays++;
            } else if (minutes >= PayrollRules.HALF_DAY_MINUTES) {
                halfDays++;
            }
        }

        double paidDays = presentDays + (halfDays * 0.5);
        double lopDays = workingDays - paidDays;

        long basicSalary = emp.getBasicEmployeeSalary();
        long perDaySalary = workingDays == 0 ? 0 : basicSalary / workingDays;
        long finalSalary = Math.round(perDaySalary * paidDays);

        Payslip p = new Payslip();
        p.setEmployeeId(emp.getId());
        p.setEmployeeName(emp.getFirstName() + " " + emp.getLastName());
        p.setEmployeeEmail(emp.getEmailId());

        p.setYear(year);
        p.setMonth(month);

        p.setWorkingDays(workingDays);
        p.setPresentDays(presentDays);
        p.setHalfDays(halfDays);
        p.setPaidDays(paidDays);
        p.setLopDays(lopDays);

        p.setBasicSalary(basicSalary);
        p.setPerDaySalary(perDaySalary);
        p.setFinalSalary(finalSalary);

        return payslipRepository.save(p);
    }

    public List<Payslip> getPayslipHistory(Long employeeId) {
        return payslipRepository.findByEmployeeIdOrderByYearDescMonthDesc(employeeId);
    }
}
