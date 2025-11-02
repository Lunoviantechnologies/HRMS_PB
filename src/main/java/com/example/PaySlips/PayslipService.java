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

@Service
public class PayslipService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    public PayslipDTO generatePayslip(Long employeeId, int month, int year) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isEmpty()) {
            return null; 
        }
        Employee employee = optionalEmployee.get();        
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<Attendance> attendanceList =
                attendanceRepository.findByIdAndAttendanceDateBetween(employeeId, startOfMonth, endOfMonth);

        int presentDays = attendanceList.size();
        int totalWorkingDays = 22; 

        long basicSalary = employee.getBasicEmployeeSalary();
        long dailySalary = basicSalary / totalWorkingDays;
        long finalSalary = dailySalary * presentDays;

        PayslipDTO payslip = new PayslipDTO();
        payslip.setEmployeeId(employee.getId());
        payslip.setEmployeeName(employee.getFirstName() + " " + employee.getLastName());
        payslip.setEmployeeEmail(employee.getEmailId());
        payslip.setPresentDays(presentDays);
        payslip.setWorkingDays(totalWorkingDays);
        payslip.setBasicSalary(basicSalary);
        payslip.setMonthlySalary(dailySalary);
        payslip.setFinalSalary(finalSalary);
        return payslip;
    }

}
