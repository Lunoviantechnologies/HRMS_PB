package com.example.Exception;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired 
    EmployeeRepository employeeRepository;

    
    // ✅ Punch In / Punch Out
    @PostMapping("/marks")
    public ResponseEntity<?> markAttendance(
            @RequestParam("location") String location,
            @RequestParam("email") String email,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

    	System.out.println("1");
        Optional<Employee> empOpt = employeeRepository.findByEmailId(email);
        if (empOpt.isEmpty()) {
        	System.out.println("2");
            return ResponseEntity.badRequest().body("❌ Employee not found with email 0: " + email);
        }
        Employee emp = empOpt.get();

        Optional<Attendance> todayAttendanceOpt =
                attendanceRepository.findByEmployeeEmailAndAttendanceDate(email, LocalDate.now());

        Attendance attendance;
        if (todayAttendanceOpt.isEmpty()) {
            // Punch In
            attendance = new Attendance();
            attendance.setEmployee(emp);
            attendance.setEmployeeEmail(emp.getEmailId());
            attendance.setLocation(location);
            if (photo != null && !photo.isEmpty()) {
                attendance.setPhoto(photo.getBytes());
            }
            attendance.setAttendanceDate(LocalDate.now());
            attendance.setPunchInTime(LocalDateTime.now());
            attendanceRepository.save(attendance);

            return ResponseEntity.ok("✅ Punch-In successful at " + attendance.getPunchInTime());
        } else {
            attendance = todayAttendanceOpt.get();

            if (attendance.getPunchOutTime() != null) {
                return ResponseEntity.badRequest().body("⚠️ Already punched out today!");
            }

            attendance.setPunchOutTime(LocalDateTime.now());
            attendanceRepository.save(attendance);

            return ResponseEntity.ok("✅ Punch-Out successful at " + attendance.getPunchOutTime() +
                    " | Net Working Hours: " + attendance.getNetWorkingHours());
        }
    }
    @PostMapping("/manualAttendance")
    public ResponseEntity<?> addManualAttendance(
            @RequestParam("employeeEmail") String email,
            @RequestParam("attendanceDate") String attendanceDate, // format: yyyy-MM-dd
            @RequestParam("punchInTime") String punchInTime,       // format: yyyy-MM-dd'T'HH:mm:ss
            @RequestParam("punchOutTime") String punchOutTime      // format: yyyy-MM-dd'T'HH:mm:ss
    ) {
        System.out.println("✅ Admin manual attendance entry for: " + email);

        // ✅ Step 1: Validate employee
        Optional<Employee> empOpt = employeeRepository.findByEmailId(email);
        if (empOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Employee not found with email: " + email);
        }

        Employee emp = empOpt.get();

        // ✅ Step 2: Create and populate attendance
        Attendance attendance = new Attendance();
        attendance.setEmployee(emp);
        attendance.setEmployeeEmail(email);
        attendance.setStatus("PRESENT");
        attendance.setAdminUpdated(true);
        attendance.setAdminUpdatedBy("ADMIN");

        try {
            attendance.setAttendanceDate(LocalDate.parse(attendanceDate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Invalid date format. Use yyyy-MM-dd");
        }

        try {
            attendance.setPunchInTime(LocalDateTime.parse(punchInTime));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Invalid punchInTime format. Use yyyy-MM-dd'T'HH:mm:ss");
        }

        try {
            attendance.setPunchOutTime(LocalDateTime.parse(punchOutTime));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Invalid punchOutTime format. Use yyyy-MM-dd'T'HH:mm:ss");
        }

        attendanceRepository.save(attendance);

        return ResponseEntity.ok("✅ Manual attendance saved successfully for employee: " + email);
    }

 // ✅ Start Break
    @PostMapping("/break/start")
    public ResponseEntity<?> startBreak(@RequestParam("email") String email) {
        Optional<Employee> empOpt = employeeRepository.findByEmailId(email.trim());
        if (empOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Employee not found with email: " + email);
        }
        Employee emp = empOpt.get();

        Optional<Attendance> todayAttendanceOpt =
                attendanceRepository.findByEmployeeIdAndAttendanceDate(emp.getId(), LocalDate.now());

        if (todayAttendanceOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ No attendance record found for today!");
        }

        Attendance attendance = todayAttendanceOpt.get();
        if (attendance.getBreakStartTime() != null) {
            return ResponseEntity.badRequest().body("⚠️ Break already started!");
        }

        attendance.setBreakStartTime(LocalDateTime.now());
        attendanceRepository.save(attendance);

        return ResponseEntity.ok("✅ Break started at " + attendance.getBreakStartTime());
    }

    // ✅ End Break
    @PostMapping("/break/end")
    public ResponseEntity<?> endBreak(@RequestParam("email") String email) {
        Optional<Employee> empOpt = employeeRepository.findByEmailId(email.trim()); // trim spaces
        if (empOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Employee not found with email: " + email);
        }
        Employee emp = empOpt.get();

        Optional<Attendance> todayAttendanceOpt =
                attendanceRepository.findByEmployeeIdAndAttendanceDate(emp.getId(), LocalDate.now());

        if (todayAttendanceOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ No attendance record found for today!");
        }

        Attendance attendance = todayAttendanceOpt.get();
        if (attendance.getBreakStartTime() == null) {
            return ResponseEntity.badRequest().body("⚠️ Break not started yet!");
        }
        if (attendance.getBreakEndTime() != null) {
            return ResponseEntity.badRequest().body("⚠️ Break already ended!");
        }

        attendance.setBreakEndTime(LocalDateTime.now());
        attendanceRepository.save(attendance);

        return ResponseEntity.ok("✅ Break ended at " + attendance.getBreakEndTime());
    }


    // ✅ Get All Attendance Records
    @GetMapping("/all")
    public List<?> getAllAttendance() {
        return attendanceRepository.findAll().stream().map(att -> new Object() {
            public final Long id = att.getId();
            public final String employeeEmail = att.getEmployeeEmail();
            public final LocalDate date = att.getAttendanceDate();
            public final LocalDateTime punchIn = att.getPunchInTime();
            public final LocalDateTime punchOut = att.getPunchOutTime();
            public final String location = att.getLocation();  // <--- Add this
            public final byte[] photo=att.getPhoto();
            public final LocalDateTime breakStart = att.getBreakStartTime();
            public final LocalDateTime breakEnd = att.getBreakEndTime();
            public final double netWorkingHours = att.getNetWorkingHours();
        }).collect(Collectors.toList());
    }
    
    
    
    

    
//      @PostMapping("/marks")
//      public ResponseEntity<?> markAttendance2(
//              @RequestParam("location") String location,
//              @RequestParam("email") String email,
//              @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {
//
//          // 🔎 Find employee
//          Optional<Employee> empOpt = employeeRepository.findByEmailId(email);
//          if (empOpt.isEmpty()) {
//              return ResponseEntity.badRequest().body("❌ Employee not found with email: " + email);
//          }
//          Employee emp = empOpt.get();
//
//          // 🔎 Check if attendance already exists for today
//          Optional<Attendance> todayAttendanceOpt =
//                  attendanceRepository.findByEmployeeEmailAndAttendanceDate(email, LocalDate.now());
//
//          Attendance attendance;
//          if (todayAttendanceOpt.isEmpty()) {
//              // ✅ First time today → Punch In
//              attendance = new Attendance();
//              attendance.setEmployee(emp);
//              attendance.setEmployeeEmail(emp.getEmailId());
//             
//              attendance.setLocation(location);
//              if (photo != null && !photo.isEmpty()) {
//                  attendance.setPhoto(photo.getBytes());
//              }
//              attendance.setAttendanceDate(LocalDate.now());
//              attendance.setPunchInTime(LocalDateTime.now());
//              attendanceRepository.save(attendance);
//
//              return ResponseEntity.ok("✅ Punch-In successful at " + attendance.getPunchInTime());
//          } else {
//              attendance = todayAttendanceOpt.get();
//
//              if (attendance.getPunchOutTime() != null) {
//                  return ResponseEntity.badRequest().body("⚠️ Already punched out today!");
//              }
//
//              attendance.setPunchOutTime(LocalDateTime.now());
//              attendanceRepository.save(attendance);
//
//              return ResponseEntity.ok("✅ Punch-Out successful at " + attendance.getPunchOutTime());
//          }
//      }
//      
//      @GetMapping("/all")
//      public List<Attendance> getAllAttendance() {
//      return attendanceRepository.findAll();
// }
   
}
