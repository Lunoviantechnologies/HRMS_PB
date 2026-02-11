package com.example.PaySlips;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payslips")
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private PayslipPdfService payslipPdfService;

    /**
     * 🔹 Generate or Fetch Payslip (Month + Year)
     * EMPLOYEE → own payslip
     * ADMIN / HR → any employee
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getPayslip(
            @PathVariable Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {

        validateMonthYear(month, year);

        Payslip payslip = payslipService
                .generateOrGetPayslip(employeeId, month, year);

        if (payslip == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Payslip not found for given employee / month / year");
        }

        return ResponseEntity.ok(payslip);
    }

    /**
     * 🔹 Payslip History (All previous months)
     */
    @GetMapping("/{employeeId}/history")
    public ResponseEntity<?> getPayslipHistory(
            @PathVariable Long employeeId) {

        List<Payslip> history =
                payslipService.getPayslipHistory(employeeId);

        if (history.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(history);
    }

    /**
     * 🔹 Download Payslip PDF
     */
    @GetMapping("/{employeeId}/download")
    public ResponseEntity<?> downloadPayslip(
            @PathVariable Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {

        validateMonthYear(month, year);

        Payslip payslip =
                payslipService.generateOrGetPayslip(employeeId, month, year);

        if (payslip == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Payslip not available to download");
        }

        byte[] pdfBytes = payslipPdfService.generatePdf(payslip);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=payslip_" + month + "_" + year + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    // ================= PRIVATE HELPERS =================

    private void validateMonthYear(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }

        YearMonth now = YearMonth.now();
        YearMonth requested = YearMonth.of(year, month);

        if (requested.isAfter(now)) {
            throw new IllegalArgumentException("Payslip for future month not allowed");
        }
    }
}
