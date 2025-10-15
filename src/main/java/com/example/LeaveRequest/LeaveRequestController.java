package com.example.LeaveRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeRegisteration.Employee;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/leaves")
@CrossOrigin("*")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestServiceInfo leaveRequestService;
        
    @PostMapping("/apply-leave/{employeeEmail}")
    public ResponseEntity<?> applyLeave(@PathVariable String employeeEmail, @RequestBody LeaveRequest leaveRequest) {
        try {
            LeaveRequest createdLeave = leaveRequestService.applyLeave(employeeEmail, leaveRequest);
            return ResponseEntity.ok(createdLeave);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    //restrict
    @GetMapping("/AllLeaves/{Empid}")
    public ResponseEntity<Optional<Employee>> getAllLeaveById(@PathVariable Long Empid) {
    	
        return ResponseEntity.ok(leaveRequestService.getAllLeavesByID(Empid));
    }
    //restrict
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaves());
    }
    @GetMapping("/Leavestatus/{id}")
    public ResponseEntity<LeaveRequest> getLeaveById(@PathVariable Long id) {
        return ResponseEntity.ok(leaveRequestService.getLeaveById(id));
    }

    
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateLeaveStatus(
            @PathVariable Long id,
            @RequestBody LeaveStatusUpdateRequest request) {

        boolean updated = leaveRequestService.updateLeaveStatus(id, request.getStatus());
        if (updated) {
            return ResponseEntity.ok("Leave status updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Leave Request not found.");
        }
    }



  
}