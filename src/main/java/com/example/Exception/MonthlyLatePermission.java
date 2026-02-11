package com.example.Exception;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "monthly_late_permissions",
    uniqueConstraints = @UniqueConstraint(columnNames = {
        "employee_email", "year", "month"
    })
)
public class MonthlyLatePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeEmail;

    private int year;
    private int month; // 1–12

    private int lateCount = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getLateCount() {
		return lateCount;
	}

	public void setLateCount(int lateCount) {
		this.lateCount = lateCount;
	}

	@Override
	public String toString() {
		return "MonthlyLatePermission [id=" + id + ", employeeEmail=" + employeeEmail + ", year=" + year + ", month="
				+ month + ", lateCount=" + lateCount + "]";
	}

    
    
}

