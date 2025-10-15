package com.example.LeaveRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;
import com.example.Exception.ResourceNotFoundException;
import com.example.LeaveRequest.LeaveRequest.LeaveStatus;
import com.example.Notification.NotificationService;

import jakarta.transaction.Transactional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestServiceInfo {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired NotificationService notificationService;
    public LeaveRequest applyLeave(String employeeEmail, LeaveRequest leaveRequest) {
        Employee employee = employeeRepository.findByEmailId(employeeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Employee ID: " + employeeEmail));

        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        
       
         LeaveRequest save = leaveRequestRepository.save(leaveRequest);
         
//         notificationService.sendToAdmin("Employee "+employee.getFirstName() +" "+employee.getLastName()+
//        		 "has requested leave from "+leaveRequest.getStartDate()+ "to "+leaveRequest.getEndDate());
         return save;
    }    

    @Override
    public List<LeaveRequest> getAllLeaves() {
        return leaveRequestRepository.findAll();
    }

    @Override
    public LeaveRequest getLeaveById(Long id) {
        Optional<LeaveRequest> leave = leaveRequestRepository.findById(id);
        if (!leave.isPresent()) {
            throw new RuntimeException("Leave not found with id: " + id);
        }
        return leave.get();
    } 
	@Override
	public Optional<Employee> getAllLeavesByID(Long id) {
		return employeeRepository.findById(id);
	}

	    @Override
	    @Transactional
	    public boolean updateLeaveStatus(Long id, LeaveRequest.LeaveStatus status) {
	        Optional<LeaveRequest> optional = leaveRequestRepository.findById(id);
	        if (optional.isPresent()) {
	            LeaveRequest leaveRequest = optional.get();
	            leaveRequest.setStatus(status);
	            leaveRequestRepository.save(leaveRequest); // This triggers the update
	            return true;
	        } else {
	            return false;
	        }
	    }

}
