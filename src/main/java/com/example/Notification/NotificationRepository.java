package com.example.Notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationMessage, Long> {
    List<NotificationMessage> findByReceiverEmail(String email);
}