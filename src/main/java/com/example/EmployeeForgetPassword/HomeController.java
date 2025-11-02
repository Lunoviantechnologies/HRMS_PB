package com.example.EmployeeForgetPassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private SendOtpToMailService otpToMailService;

    // ✅ Send OTP to email
    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendOtpToMail(@PathVariable("email") String email) {
        try {
            otpToMailService.sendOtpService(email);
            return ResponseEntity.ok("OTP sent successfully to: " + email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to send OTP.");
        }
    }

    // ✅ Validate OTP
    @PostMapping("/validateOtp")
    public ResponseEntity<String> validateOtp(@RequestBody OtpRequest otpRequest) {
        boolean isValid = otpToMailService.validateOtp(otpRequest.getEmail(), otpRequest.getOtp());
        if (isValid) {
            return ResponseEntity.ok("OTP is valid. You can now reset your password.");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP!");
        }
    }

    // ✅ Update Password after OTP validation
    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody OtpRequest resetRequest) {
        boolean success = otpToMailService.updatePassword(
                resetRequest.getEmail(),
                resetRequest.getOtp(),
                resetRequest.getNewPassword()
        );

        if (success) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP. Password not updated.");
        }
    }
}