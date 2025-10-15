package com.example.Exception;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.EmployeeRegisteration.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "attendance")
public class Attendance {
    
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String location;

	    private String name;

	    @Lob
	    private byte[] photo;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "employee_id", nullable = false)
	    @JsonBackReference
	    private Employee employee;
	    
	    @Column(name = "attendance_date")
	    private LocalDate attendanceDate;

	    @Column(name = "employee_email", nullable = false)
	    private String employeeEmail;
	    
	    @Column(name = "punch_in_time")
	    private LocalDateTime punchInTime;

	    @Column(name = "punch_out_time")
	    private LocalDateTime punchOutTime;

	    // 🔹 NEW BREAK COLUMNS
	    @Column(name = "break_start_time")
	    private LocalDateTime breakStartTime;

	    @Column(name = "break_end_time")
	    private LocalDateTime breakEndTime;

	    // --- Business Logic ---
	    public long getNetWorkingMinutes() {
	        if (punchInTime == null || punchOutTime == null) {
	            return 0;
	        }

	        long totalMinutes = Duration.between(punchInTime, punchOutTime).toMinutes();

	        long breakMinutes = 0;
	        if (breakStartTime != null && breakEndTime != null) {
	            breakMinutes = Duration.between(breakStartTime, breakEndTime).toMinutes();
	        }

	        return Math.max(0, totalMinutes - breakMinutes);
	    }

	    public double getNetWorkingHours() {
	        return getNetWorkingMinutes() / 60.0;
	    }

	    // --- Getters & Setters ---
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getLocation() { return location; }
	    public void setLocation(String location) { this.location = location; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public byte[] getPhoto() { return photo; }
	    public void setPhoto(byte[] photo) { this.photo = photo; }

	    public Employee getEmployee() { return employee; }
	    public void setEmployee(Employee employee) { this.employee = employee; }

	    public String getEmployeeEmail() { return employeeEmail; }
	    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }

	    public LocalDate getAttendanceDate() { return attendanceDate; }
	    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }

	    public LocalDateTime getPunchInTime() { return punchInTime; }
	    public void setPunchInTime(LocalDateTime punchInTime) { this.punchInTime = punchInTime; }

	    public LocalDateTime getPunchOutTime() { return punchOutTime; }
	    public void setPunchOutTime(LocalDateTime punchOutTime) { this.punchOutTime = punchOutTime; }

	    public LocalDateTime getBreakStartTime() { return breakStartTime; }
	    public void setBreakStartTime(LocalDateTime breakStartTime) { this.breakStartTime = breakStartTime; }

	    public LocalDateTime getBreakEndTime() { return breakEndTime; }
	    public void setBreakEndTime(LocalDateTime breakEndTime) { this.breakEndTime = breakEndTime; }
	
	
	
	
	
	
	
	
	
	
	
	
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String location;
//
//    private String name;
//
//    @Lob
//    private byte[] photo;
//
//    // ✅ Foreign key relationship
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id", nullable = false)
//    @JsonBackReference      // ✅ Child
//    private Employee employee;
//    
//    
//    @Column(name = "attendance_date")
//    private LocalDate attendanceDate;
//    
//
//    // ✅ Additional column that DB requires
//    @Column(name = "employee_email", nullable = false)
//    private String employeeEmail;
//    
//    @Column(name = "punch_out_time")
//    private LocalDateTime punchOutTime;
//    
//    @Column(name = "punch_in_time", nullable = true)
//    private LocalDateTime punchInTime;
//
//    
//
//	public LocalDateTime getPunchInTime() {
//		return punchInTime;
//	}
//
//	public void setPunchInTime(LocalDateTime punchInTime) {
//		this.punchInTime = punchInTime;
//	}
//
//	public LocalDateTime getPunchOutTime() {
//		return punchOutTime;
//	}
//
//	public void setPunchOutTime(LocalDateTime punchOutTime) {
//		this.punchOutTime = punchOutTime;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public byte[] getPhoto() {
//		return photo;
//	}
//
//	public void setPhoto(byte[] photo) {
//		this.photo = photo;
//	}
//
//	public Employee getEmployee() {
//		return employee;
//	}
//
//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}
//
//	public String getEmployeeEmail() {
//		return employeeEmail;
//	}
//
//	public void setEmployeeEmail(String employeeEmail) {
//		this.employeeEmail = employeeEmail;
//	}
//
//	public LocalDate getAttendanceDate() {
//		return attendanceDate;
//	}
//
//	public void setAttendanceDate(LocalDate attendanceDate) {
//		this.attendanceDate = attendanceDate;
//	}

	
    
    
    
}

  
    

