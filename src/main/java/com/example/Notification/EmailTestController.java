package com.example.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/api/test/send-email")
    public String sendTestEmail() {
        try {
            emailService.sendEmail(
                "murarisivaraju16@gmail.com",  // 👈 change to your real email
                "✅ Test Email from HRMS Project",
                "This is a test email sent from your Spring Boot HRMS project."
            );
            return "✅ Email sent successfully! Check your inbox.";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error sending email: " + e.getMessage();
        }
    }
}
