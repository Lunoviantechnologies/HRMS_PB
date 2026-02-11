package com.example.PaySlips;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayslipRepository extends JpaRepository<Payslip, Long> {

    Optional<Payslip> findByEmployeeIdAndYearAndMonth(
            Long employeeId, int year, int month);

    List<Payslip> findByEmployeeIdOrderByYearDescMonthDesc(Long employeeId);
    List<Payslip> findByEmployeeId(Long employeeId);

    List<Payslip> findByYearAndMonth(int year, int month);

}
