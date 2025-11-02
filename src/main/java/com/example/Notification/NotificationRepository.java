package com.example.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationMessage, Long> {

    // ✅ Find notifications for a specific receiver
    List<NotificationMessage> findByReceiverEmail(String receiverEmail);

    // ✅ Automatically delete old notifications (used by cleanup task)
    void deleteByCreatedAtBefore(LocalDateTime dateTime);
}
