package com.example.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initialCleanup() {
        cleanOldNotifications();
    }

    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void cleanOldNotifications() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
        notificationRepository.deleteByCreatedAtBefore(cutoffDate);
        System.out.println("🧹 Old notifications cleaned up successfully at: " + LocalDateTime.now());
    }

    public void createAndSend(String senderEmail, String receiverEmail, String actionType, String message) {
        NotificationMessage notification = new NotificationMessage();
        notification.setSenderEmail(senderEmail);
        notification.setReceiverEmail(receiverEmail);
        notification.setActionType(actionType);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        if (messagingTemplate != null) {
            messagingTemplate.convertAndSendToUser(receiverEmail, "/queue/notifications", notification);
        }
    }

    public List<NotificationMessage> getNotificationsForUser(String email) {
        return notificationRepository.findByReceiverEmail(email);
    }
}
