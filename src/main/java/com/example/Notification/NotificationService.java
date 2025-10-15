package com.example.Notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void createAndSend(String senderEmail, String receiverEmail, String actionType, String message) {
        // Build the message
        NotificationMessage notification = new NotificationMessage();
        notification.setSenderEmail(senderEmail);
        notification.setReceiverEmail(receiverEmail);
        notification.setActionType(actionType);
        notification.setMessage(buildMessage(actionType, message));

        // Save in DB
        NotificationMessage saved = notificationRepository.save(notification);

        // Send via WebSocket
        messagingTemplate.convertAndSendToUser(
                receiverEmail,               // user destination
                "/queue/notifications",      // endpoint frontend should subscribe to
                saved                        // send the saved notification
        );
    }


//        // --- Debug logs ---
//        System.out.println("🔔 Sending notification:");
//        System.out.println("   ReceiverEmail: " + receiverEmail);
//        System.out.println("   ActionType: " + actionType);
//        System.out.println("   Message: " + message);

        // Send via STOMP /user/queue/notifications
       

//        return saved;
//    }

    private String buildMessage(String actionType, String details) {
        switch (actionType.toUpperCase()) {
            // Admin → Employee
            case "EMPLOYEE_ADDED":
                return "You have been added to the system. Welcome " + details + "!";
            case "EMPLOYEE_UPDATED":
                return "Your profile has been updated: " + details;
            case "LEAVE_APPROVED":
                return "Your leave request " + details + " has been approved.";
            case "LEAVE_REJECTED":
                return "Your leave request " + details + " has been rejected.";
            case "PAYSLIP_GENERATED":
                return "Your payslip for " + details + " is now available.";

            // Employee → Admin
            case "LEAVE_APPLIED":
                return "Employee " + details + " has applied for leave.";
            case "PAYSLIP_DOWNLOADED":
                return "Employee " + details + " has downloaded their payslip.";
            case "PROFILE_UPDATED":
                return "Employee " + details + " updated their profile.";

            default:
                return "New notification: " + details;
        }
    }

    public List<NotificationMessage> getNotificationsForUser(String email) {
        return notificationRepository.findByReceiverEmail(email);
    }
}