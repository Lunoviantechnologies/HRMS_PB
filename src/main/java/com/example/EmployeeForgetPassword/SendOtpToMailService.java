package com.example.EmployeeForgetPassword;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EmployeeRegisteration.Employee;
import com.example.EmployeeRegisteration.EmployeeRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendOtpToMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // In-memory OTP store (email -> OTP)
    private final Map<String, String> otpStore = new HashMap<>();
     // Sends an OTP to the given email if the employee exists.   
    public void sendOtpService(String email) {
        //Optional<Employee> byEmailId = employeeRepository.findByEmailId(email);
        Optional<Employee> byEmailId = employeeRepository.findByEmailId(email);

        if (byEmailId.isPresent()) {
            String otp = generateOtp();
            otpStore.put(email, otp);
            try {
                sendOtpToMail(email, otp);
            } catch (MessagingException e) {
                throw new RuntimeException("Unable to send OTP", e);
            }
        } else {
            throw new IllegalArgumentException("Email not found. Please enter a registered email.");
        }
    }    
     //Validates the provided OTP against the stored one.
    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        return storedOtp != null && storedOtp.equals(otp);
    }   
      //Updates the employee's password after validating the OTP.     
    public boolean updatePassword(String email, String otp, String newPassword) {
        if (validateOtp(email, otp)) {
            Optional<Employee> employeeOpt = employeeRepository.findByEmailId(email);
            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                employee.setPassword(passwordEncoder.encode(newPassword));
                employeeRepository.save(employee);
                otpStore.remove(email); // Invalidate OTP after use
                return true;
            } else {
                throw new IllegalArgumentException("Employee not found.");
            }
        } else {
            return false; // Invalid OTP
        }
    }   
     //Generates a 6-digit numeric OTP.     
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    } 
     // Sends the OTP to the user's email using JavaMailSender.
         private void sendOtpToMail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setSubject("Password Reset OTP");
        helper.setText("Dear User,\n\nYour OTP for password reset is: " + otp + "\n\nThis OTP is valid for 10 minutes.\n\nThank you.");
        System.out.println();
        javaMailSender.send(mimeMessage);
    }
       
}
