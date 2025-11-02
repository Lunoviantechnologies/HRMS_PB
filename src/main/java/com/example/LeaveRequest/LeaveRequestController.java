package com.example.LeaveRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EmployeeRegisteration.Employee;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin("*")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestServiceInfo leaveRequestService;

    // ✅ Employee applies for leave
    @PostMapping("/apply-leave/{employeeEmail}")
    public ResponseEntity<?> applyLeave(@PathVariable String employeeEmail, @RequestBody LeaveRequest leaveRequest) {
        try {
            LeaveRequest createdLeave = leaveRequestService.applyLeave(employeeEmail, leaveRequest);
            return ResponseEntity.ok(createdLeave);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while applying leave: " + e.getMessage());
        }
    }

    // ✅ Get all leaves of one employee by ID
    @GetMapping("/AllLeaves/{Empid}")
    public ResponseEntity<Optional<Employee>> getAllLeaveById(@PathVariable Long Empid) {
        return ResponseEntity.ok(leaveRequestService.getAllLeavesByID(Empid));
    }

    // ✅ Get all leaves (admin view)
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaves());
    }

    // ✅ Get leave details by leave ID
    @GetMapping("/Leavestatus/{id}")
    public ResponseEntity<LeaveRequest> getLeaveById(@PathVariable Long id) {
        return ResponseEntity.ok(leaveRequestService.getLeaveById(id));
    }

    // ✅ Update leave status (Admin approves/rejects)
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateLeaveStatus(
            @PathVariable Long id,
            @RequestBody LeaveStatusUpdateRequest request) {
        try {
            boolean updated = leaveRequestService.updateLeaveStatus(id, request.getStatus());
            if (updated) {
                return ResponseEntity.ok("✅ Leave status updated successfully for ID: " + id + " (" + request.getStatus() + ")");
            } else {
                return ResponseEntity.status(404).body("❌ Leave Request not found for ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("⚠️ Error updating leave status: " + e.getMessage());
        }
    }
}
