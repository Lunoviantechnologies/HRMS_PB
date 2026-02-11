package com.example.PaySlips;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "payslips",
       uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id","year","month"}))
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private String employeeName;
    private String employeeEmail;

    private int year;
    private int month;

    private int workingDays;
    private int presentDays;
    private int halfDays;
    private double paidDays;
    private double lopDays;

    private long basicSalary;
    private long perDaySalary;
    private long finalSalary;

    private LocalDateTime generatedAt;

    @PrePersist
    void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}

	public int getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(int presentDays) {
		this.presentDays = presentDays;
	}

	public int getHalfDays() {
		return halfDays;
	}

	public void setHalfDays(int halfDays) {
		this.halfDays = halfDays;
	}

	public double getPaidDays() {
		return paidDays;
	}

	public void setPaidDays(double paidDays) {
		this.paidDays = paidDays;
	}

	public double getLopDays() {
		return lopDays;
	}

	public void setLopDays(double lopDays) {
		this.lopDays = lopDays;
	}

	public long getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(long basicSalary) {
		this.basicSalary = basicSalary;
	}

	public long getPerDaySalary() {
		return perDaySalary;
	}

	public void setPerDaySalary(long perDaySalary) {
		this.perDaySalary = perDaySalary;
	}

	public long getFinalSalary() {
		return finalSalary;
	}

	public void setFinalSalary(long finalSalary) {
		this.finalSalary = finalSalary;
	}

	public LocalDateTime getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt(LocalDateTime generatedAt) {
		this.generatedAt = generatedAt;
	}

	@Override
	public String toString() {
		return "Payslip [id=" + id + ", employeeId=" + employeeId + ", employeeName=" + employeeName
				+ ", employeeEmail=" + employeeEmail + ", year=" + year + ", month=" + month + ", workingDays="
				+ workingDays + ", presentDays=" + presentDays + ", halfDays=" + halfDays + ", paidDays=" + paidDays
				+ ", lopDays=" + lopDays + ", basicSalary=" + basicSalary + ", perDaySalary=" + perDaySalary
				+ ", finalSalary=" + finalSalary + ", generatedAt=" + generatedAt + "]";
	}

    
}

