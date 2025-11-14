package com.example.LeaveRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;
import com.example.Exception.ResourceNotFoundException;
import com.example.LeaveRequest.LeaveRequest.LeaveStatus;
import com.example.Notification.NotificationService;
import com.example.Notification.EmailService;

import jakarta.transaction.Transactional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestServiceInfo {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    // ✅ Load dynamically from application.properties
    @Value("${hrms.admin.email}")
    private String ADMIN_EMAIL;

    /** ✅ Employee applies for leave */
    @Override
    public LeaveRequest applyLeave(String employeeEmail, LeaveRequest leaveRequest) {
        Employee employee = employeeRepository.findByEmailId(employeeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Employee Email: " + employeeEmail));

        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus(LeaveStatus.PENDING);

        LeaveRequest savedLeave = leaveRequestRepository.save(leaveRequest);

        // ✅ Notify HR dynamically
        notificationService.createAndSend(
                employeeEmail,
                ADMIN_EMAIL,
                "LEAVE_APPLIED",
                employee.getFirstName() + " " + employee.getLastName() +
                        " requested leave from " + leaveRequest.getStartDate() +
                        " to " + leaveRequest.getEndDate()
        );

        return savedLeave;
    }

    /** ✅ Admin updates leave status and sends correct dynamic email */
    @Override
    @Transactional
    public boolean updateLeaveStatus(Long id, LeaveStatus status) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("❌ Leave Request not found for ID: " + id));

        leaveRequest.setStatus(status);
        leaveRequestRepository.save(leaveRequest);

        // ✅ Get linked employee
        Employee employee = leaveRequest.getEmployee();
        if (employee == null) {
            throw new ResourceNotFoundException("❌ Employee not linked to Leave Request ID: " + id);
        }

        // ✅ Get email from DB
        String employeeEmail = employee.getEmailId();
        if (employeeEmail == null || employeeEmail.isEmpty()) {
            throw new ResourceNotFoundException("❌ Employee email not found for ID: " + employee.getId());
        }

        // ✅ Prepare email content
        String subject = "Your Leave Request Has Been " + status.name();
        String body = "Dear " + employee.getFirstName() + ",\n\n" +
                "Your leave request from " + leaveRequest.getStartDate() + " to " +
                leaveRequest.getEndDate() + " has been " + status.name().toLowerCase() + ".\n\n" +
                "📅 Type: " + leaveRequest.getLeaveType() + "\n" +
                "📝 Reason: " + leaveRequest.getReason() + "\n\n" +
                "Regards,\nHR Department";

        // ✅ Send email dynamically to the employee (no hardcode)
        emailService.sendEmail(employeeEmail, subject, body);

        // ✅ Notify HR/admin as well
        notificationService.createAndSend(
                ADMIN_EMAIL,
                employeeEmail,
                (status == LeaveStatus.ACCEPTED ? "LEAVE_APPROVED" : "LEAVE_REJECTED"),
                employee.getFirstName() + " " + employee.getLastName() +
                        " leave request has been " + status.name().toLowerCase()
        );

        return true;
    }

    /** ✅ Admin: Get all leaves */
    @Override
    public List<LeaveRequest> getAllLeaves() {
        return leaveRequestRepository.findAll();
    }

    /** ✅ Get leave by ID */
    @Override
    public LeaveRequest getLeaveById(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
    }

    /** ✅ Get employee by ID */
    @Override
    public Optional<Employee> getAllLeavesByID(Long id) {
        return employeeRepository.findById(id);
    }
}
