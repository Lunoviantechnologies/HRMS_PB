package com.example.PaySlips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payslip")
public class PayslipController {

    @Autowired
    private PayslipService payslipService;


    
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getPayslip(
            @PathVariable Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {
        try {
            PayslipDTO payslip = payslipService.generatePayslip(employeeId, month, year);

            if (payslip == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("{\"error\": \"Employee not found with ID: " + employeeId + "\"}");
            }

            return ResponseEntity.ok(payslip);

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Unexpected error: " + ex.getMessage() + "\"}");
        }
    }
}
