package com.example.AdmineController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.AdmineEntity.AdmineEntity;
import com.example.AdmineEntity.LoginRequest;
import com.example.AdmineEntity.leavepolicy;
import com.example.AdmineInfo.Admineinfo;
import com.example.Adminerepository.leavePolicyRepo;
import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeService;
import com.example.Exception.Attendance;
import com.example.Exception.AttendanceRepository;
import com.example.Exception.AttendanceService;
import com.example.Security.JwtUtil;

import java.time.Duration;

@RestController
@CrossOrigin("*")
public class AdmineControllerClass {

    @Autowired
    private Admineinfo admineinfo;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private leavePolicyRepo leavePolicyrepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // ✅ LOGIN (Admin + Employee)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Admin login
        AdmineEntity admin = admineinfo.getAdminByEmail(email);
        if (admin != null && password.equals(admin.getPassword())) {
            String token = jwtUtil.generateAccessToken(admin.getEmail(), "ADMIN");
            return ResponseEntity.ok(new JwtResponse("Bearer " + token, "ADMIN", admin.getEmail()));
        }

        // Employee login
        Employee employee = employeeService.login(email);
        if (employee != null && passwordEncoder.matches(password, employee.getPassword())) {
            String token = jwtUtil.generateAccessToken(employee.getEmailId(), "EMPLOYEE");
            return ResponseEntity.ok(new JwtResponse("Bearer " + token, "EMPLOYEE", employee.getEmailId()));
        }

        return ResponseEntity.status(401).body("Invalid email or password");
    }

    // ✅ ADD LEAVE POLICY
    @PostMapping("/leave_policy")
    public ResponseEntity<?> addLeavePolicy(@RequestBody leavepolicy leavepolicy) {
        leavePolicyrepo.save(leavepolicy);
        return ResponseEntity.status(200).body("Leave policy added successfully");
    }

    // ✅ UPDATE LEAVE POLICY
    @PutMapping("/leave_policy/{id}")
    public ResponseEntity<?> updateLeavePolicy(@PathVariable int id, @RequestBody leavepolicy updatedPolicy) {
        Optional<leavepolicy> existingPolicyOpt = leavePolicyrepo.findById(id);

        if (existingPolicyOpt.isPresent()) {
            leavepolicy existingPolicy = existingPolicyOpt.get();

            existingPolicy.setAnnualLeaves(updatedPolicy.getAnnualLeaves());
            existingPolicy.setCasualLeaves(updatedPolicy.getCasualLeaves());
            existingPolicy.setSickLeaves(updatedPolicy.getSickLeaves());
            existingPolicy.setMaternityLeaves(updatedPolicy.getMaternityLeaves());
            existingPolicy.setCarryForwarelimit(updatedPolicy.getCarryForwarelimit());

            leavePolicyrepo.save(existingPolicy);
            return ResponseEntity.ok(existingPolicy);
        } else {
            return ResponseEntity.status(404).body("Leave Policy not found with id " + id);
        }
    }

    // ✅ GET ALL LEAVE POLICIES
    @GetMapping("/leavePolicy_table")
    public ResponseEntity<?> getAllLeavePolicies() {
        List<leavepolicy> all = leavePolicyrepo.findAll();
        return ResponseEntity.ok(all);
    }

    // ✅ ADMIN — UPDATE EMPLOYEE ATTENDANCE
    @PostMapping(value = "/attendance/update/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateEmployeeAttendance(
            @PathVariable Long id,
//            @RequestParam(value = "location", required = false) String location,
//            @RequestParam(value = "punchOutLocation", required = false) String punchOutLocation,
//            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "employeeEmail", required = false) String employeeEmail,
            @RequestParam(value = "attendanceDate", required = false) String attendanceDateStr,
            @RequestParam(value = "punchInTime", required = false) String punchInTimeStr,
            @RequestParam(value = "punchOutTime", required = false) String punchOutTimeStr
//            @RequestParam(value = "breakStartTime", required = false) String breakStartTimeStr,
//            @RequestParam(value = "breakEndTime", required = false) String breakEndTimeStr,
           // @RequestParam(value = "status", required = false) String status,
          //  @RequestParam(value = "adminUpdatedBy", required = false) String adminUpdatedBy
            //@RequestParam(value = "photo", required = false) MultipartFile photo
    ) {
        try {
            Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
            if (optionalAttendance.isEmpty()) {
                return ResponseEntity.status(404).body("❌ Attendance record not found with ID: " + id);
            }

            Attendance attendance = optionalAttendance.get();

            // ✅ Update fields if provided
//            if (location != null) attendance.setLocation(location);
//            if (punchOutLocation != null) attendance.setPunchOutLocation(punchOutLocation);
//            if (name != null) attendance.setName(name);
            if (employeeEmail != null) attendance.setEmployeeEmail(employeeEmail);

            if (attendanceDateStr != null && !attendanceDateStr.isEmpty()) {
                attendance.setAttendanceDate(LocalDate.parse(attendanceDateStr));
            }

            if (punchInTimeStr != null && !punchInTimeStr.isEmpty()) {
                attendance.setPunchInTime(LocalDateTime.parse(punchInTimeStr));
            }

            if (punchOutTimeStr != null && !punchOutTimeStr.isEmpty()) {
                attendance.setPunchOutTime(LocalDateTime.parse(punchOutTimeStr));
            }

//            if (breakStartTimeStr != null && !breakStartTimeStr.isEmpty()) {
//                attendance.setBreakStartTime(LocalDateTime.parse(breakStartTimeStr));
//            }
//
//            if (breakEndTimeStr != null && !breakEndTimeStr.isEmpty()) {
//                attendance.setBreakEndTime(LocalDateTime.parse(breakEndTimeStr));
//            }

//            if (photo != null && !photo.isEmpty()) {
//                attendance.setPhoto(photo.getBytes());
//            }

          //  if (status != null) attendance.setStatus(status);

            // ✅ Automatically recalculate working time
            if (attendance.getPunchInTime() != null && attendance.getPunchOutTime() != null) {
                long totalMinutes = Duration.between(attendance.getPunchInTime(), attendance.getPunchOutTime()).toMinutes();
                long breakMinutes = 0;
                if (attendance.getBreakStartTime() != null && attendance.getBreakEndTime() != null) {
                    breakMinutes = Duration.between(attendance.getBreakStartTime(), attendance.getBreakEndTime()).toMinutes();
                }
                attendance.setWorkingMinutes(totalMinutes - breakMinutes);
            }

            // ✅ Mark as admin updated
            attendance.setAdminUpdated(true);
           // attendance.setAdminUpdatedBy(adminUpdatedBy != null ? adminUpdatedBy : "System Admin");
            attendance.setAdminUpdatedOn(LocalDateTime.now());

            Attendance saved = attendanceRepository.save(attendance);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error updating attendance: " + e.getMessage());
        }
    }

    // ✅ ADMIN — Mark Absent as Present
    @PutMapping("/attendance/mark-present")
    public ResponseEntity<?> markAbsentToPresent(
            @RequestParam String employeeEmail,
            @RequestParam String date,
            @RequestParam String adminEmail) {

        try {
            Attendance updated = attendanceService.markAbsentToPresent(
                    employeeEmail,
                    LocalDate.parse(date),
                    adminEmail);

            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error marking attendance: " + e.getMessage());
        }
    }
}
