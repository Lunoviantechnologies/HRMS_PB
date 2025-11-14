package com.example.Notification;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Adminerepository.AdmineRepo;
import com.example.AdmineEntity.AdmineEntity;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AdmineRepo adminRepo;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest request) {

        String senderEmail = request.getSenderEmail();
        String receiverEmail = request.getReceiverEmail();
        String actionType = request.getActionType();
        String message = request.getMessage();

        if (senderEmail == null || message == null) {
            return ResponseEntity.badRequest().body("Sender email or message cannot be null");
        }

        // --- Employee applies leave → notify all admins ---
        if ("LEAVE_APPLIED".equalsIgnoreCase(actionType)) {
            List<String> adminEmails = adminRepo.findByRole("ADMIN")
                    .stream()
                    .map(AdmineEntity::getEmail)
                    .toList();

            adminEmails.forEach(adminEmail ->
                    notificationService.createAndSend(senderEmail, adminEmail, actionType, message)
            );

            return ResponseEntity.ok("Leave request sent to all admins");
        }

        // --- Admin approves/rejects leave → notify specific employee ---
        if (List.of("LEAVE_APPROVED", "LEAVE_REJECTED", "EMPLOYEE_UPDATED", "PAYSLIP_GENERATED")
                .contains(actionType.toUpperCase())) {

            if (receiverEmail != null && receiverEmail.contains("@")) {
                notificationService.createAndSend(senderEmail, receiverEmail, actionType, message);
                return ResponseEntity.ok("Notification sent to employee");
            } else {
                return ResponseEntity.badRequest().body("Invalid receiver email");
            }
        }

        // --- Other employee actions → notify admins ---
        if (List.of("PROFILE_UPDATED", "PAYSLIP_DOWNLOADED").contains(actionType.toUpperCase())) {
            List<String> adminEmails = adminRepo.findByRole("ADMIN")
                    .stream()
                    .map(AdmineEntity::getEmail)
                    .toList();

            adminEmails.forEach(adminEmail ->
                    notificationService.createAndSend(senderEmail, adminEmail, actionType, message)
            );

            return ResponseEntity.ok("Notification sent to admins");
        }

        return ResponseEntity.badRequest().body("Unknown actionType");
    }

    @GetMapping("/{email}")
    public List<NotificationMessage> getNotifications(@PathVariable String email) {
        return notificationService.getNotificationsForUser(email);
    }
}
