package com.example.LeaveRequest;

import java.util.List;
import java.util.Optional;

import com.example.EmployeeRegisteration.Employee;
import com.example.LeaveRequest.LeaveRequest.LeaveStatus;

public interface LeaveRequestServiceInfo {

    //LeaveRequest applyLeave(LeaveRequestDTO leaveRequestDTO);
	LeaveRequest applyLeave(String employeeEmail,LeaveRequest leaveRequest);	
	
    List<LeaveRequest> getAllLeaves();
    LeaveRequest getLeaveById(Long id);
    
    
    Optional<Employee> getAllLeavesByID(Long id);
    
    
    //LeaveRequest updateLeaveStatus(Long id, LeaveStatus status);
    boolean updateLeaveStatus(Long id, LeaveRequest.LeaveStatus status);

}
