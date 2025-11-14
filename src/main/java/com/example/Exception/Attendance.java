package com.example.Exception;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.EmployeeRegisteration.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String punchOutLocation;
    private String name;

    @Lob
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "employee_email", nullable = false)
    private String employeeEmail;

    @Column(name = "punch_in_time")
    private LocalDateTime punchInTime;

    @Column(name = "punch_out_time")
    private LocalDateTime punchOutTime; 

    @Column(name = "break_start_time")
    private LocalDateTime breakStartTime;

    @Column(name = "break_end_time")
    private LocalDateTime breakEndTime;

    @Column(name = "working_minutes")
    private Long workingMinutes;

    @Column(name = "admin_updated")
    private Boolean adminUpdated = false;

    @Column(name = "status")
    private String status = "ABSENT"; // Default: ABSENT

    @Column(name = "admin_updated_by")
    private String adminUpdatedBy;

    @Column(name = "admin_updated_on")
    private LocalDateTime adminUpdatedOn;

    // --- Calculate total working time ---
    public long calculateNetWorkingMinutes() {
        if (punchInTime == null || punchOutTime == null)
            return 0;

        long totalMinutes = Duration.between(punchInTime, punchOutTime).toMinutes();
        long breakMinutes = 0;

        if (breakStartTime != null && breakEndTime != null) {
            breakMinutes = Duration.between(breakStartTime, breakEndTime).toMinutes();
        }

        return Math.max(0, totalMinutes - breakMinutes);
    }

    public double getNetWorkingHours() {
        if (workingMinutes == null) return 0.0;
        return workingMinutes / 60.0;
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

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }

    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }

    public LocalDateTime getPunchInTime() { return punchInTime; }
    public void setPunchInTime(LocalDateTime punchInTime) { this.punchInTime = punchInTime; }

    public LocalDateTime getPunchOutTime() { return punchOutTime; }
    public void setPunchOutTime(LocalDateTime punchOutTime) { this.punchOutTime = punchOutTime; }

    public LocalDateTime getBreakStartTime() { return breakStartTime; }
    public void setBreakStartTime(LocalDateTime breakStartTime) { this.breakStartTime = breakStartTime; }

    public LocalDateTime getBreakEndTime() { return breakEndTime; }
    public void setBreakEndTime(LocalDateTime breakEndTime) { this.breakEndTime = breakEndTime; }

    public Long getWorkingMinutes() { return workingMinutes; }
    public void setWorkingMinutes(Long workingMinutes) { this.workingMinutes = workingMinutes; }

    public Boolean getAdminUpdated() { return adminUpdated; }
    public void setAdminUpdated(Boolean adminUpdated) { this.adminUpdated = adminUpdated; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAdminUpdatedBy() { return adminUpdatedBy; }
    public void setAdminUpdatedBy(String adminUpdatedBy) { this.adminUpdatedBy = adminUpdatedBy; }

    public LocalDateTime getAdminUpdatedOn() { return adminUpdatedOn; }
    public void setAdminUpdatedOn(LocalDateTime adminUpdatedOn) { this.adminUpdatedOn = adminUpdatedOn; }

	public String getPunchOutLocation() {
		return punchOutLocation;
	}

	public void setPunchOutLocation(String punchOutLocation) {
		this.punchOutLocation = punchOutLocation;
	}
}
