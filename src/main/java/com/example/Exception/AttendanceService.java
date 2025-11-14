package com.example.Exception;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Save after punch in/out
    public Attendance save(Attendance attendance) {
        attendance.setWorkingMinutes(attendance.calculateNetWorkingMinutes());
        return attendanceRepository.save(attendance);
    }

    // Admin manually updates networking hours (e.g., 8:59 → 9:00)
    public Attendance updateNetworkingHours(Long attendanceId, long newWorkingMinutes) {
        Attendance existing = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found for ID: " + attendanceId));

        existing.setWorkingMinutes(newWorkingMinutes);
        existing.setAdminUpdated(true);
        existing.setAdminUpdatedOn(LocalDateTime.now());
        existing.setStatus("PRESENT");

        return attendanceRepository.save(existing);
    }

    // Admin marks absent → present
    public Attendance markAbsentToPresent(String employeeEmail, LocalDate date, String adminEmail) {
        Optional<Attendance> optional = attendanceRepository.findByEmployeeEmailAndAttendanceDate(employeeEmail, date);

        if (optional.isEmpty()) {
            throw new RuntimeException("No attendance found for " + employeeEmail + " on " + date);
        }

        Attendance attendance = optional.get();
        attendance.setStatus("PRESENT");
        attendance.setWorkingMinutes(540L); // Default 9 hours
        attendance.setAdminUpdated(true);
        attendance.setAdminUpdatedBy(adminEmail);
        attendance.setAdminUpdatedOn(LocalDateTime.now());

        return attendanceRepository.save(attendance);
    }

    // General update
    public Attendance updateAttendance(Long attendanceId, Attendance updatedAttendance) {
        Attendance existing = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found for ID: " + attendanceId));

        if (updatedAttendance.getPunchInTime() != null)
            existing.setPunchInTime(updatedAttendance.getPunchInTime());

        if (updatedAttendance.getPunchOutTime() != null)
            existing.setPunchOutTime(updatedAttendance.getPunchOutTime());

        if (updatedAttendance.getBreakStartTime() != null)
            existing.setBreakStartTime(updatedAttendance.getBreakStartTime());

        if (updatedAttendance.getBreakEndTime() != null)
            existing.setBreakEndTime(updatedAttendance.getBreakEndTime());

        existing.setWorkingMinutes(existing.calculateNetWorkingMinutes());
        return attendanceRepository.save(existing);
    }
}
