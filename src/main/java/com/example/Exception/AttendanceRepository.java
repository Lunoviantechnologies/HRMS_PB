package com.example.Exception;

//import java.awt.print.Pageable;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

   List<Attendance> findByIdAndAttendanceDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
//
//	Optional<Attendance> findByEmployeeEmailAndAttendanceDate(String email, LocalDate now);
//
   Page<Attendance> findAll(Pageable pageable);  

    Optional<Attendance> findByEmployeeEmailAndAttendanceDate(String employeeEmail, LocalDate attendanceDate);

    Optional<Attendance> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);
    List<Attendance> findByEmployee_IdAndAttendanceDateBetween(
            Long employeeId, LocalDate start, LocalDate end);
    
    

}
