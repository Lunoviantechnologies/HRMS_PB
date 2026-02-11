package com.example.PaySlips;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

@Service
public class PayslipPdfService {

    public byte[] generatePdf(Payslip p) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            // ===== Company Header =====
            doc.add(new Paragraph("LUNOVIAN TECHNOLOGIES Pvt Ltd")
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            doc.add(new Paragraph("Payslip for " + p.getMonth() + "/" + p.getYear())
                    .setTextAlignment(TextAlignment.CENTER));

            doc.add(new Paragraph(" "));

            // ===== Employee Details =====
            doc.add(new Paragraph("Employee Name  : " + p.getEmployeeName()));
            doc.add(new Paragraph("Employee Email : " + p.getEmployeeEmail()));
            doc.add(new Paragraph("Employee ID    : " + p.getEmployeeId()));

            doc.add(new Paragraph(" "));

            // ===== Attendance Details =====
            doc.add(new Paragraph("Working Days   : " + p.getWorkingDays()));
            doc.add(new Paragraph("Present Days   : " + p.getPresentDays()));
            doc.add(new Paragraph("Half Days      : " + p.getHalfDays()));
            doc.add(new Paragraph("Paid Days      : " + p.getPaidDays()));
            doc.add(new Paragraph("Loss of Pay    : " + p.getLopDays()));

            doc.add(new Paragraph(" "));

            // ===== Salary Details =====
            doc.add(new Paragraph("Basic Salary   : ₹" + p.getBasicSalary()));
            doc.add(new Paragraph("Per Day Salary : ₹" + p.getPerDaySalary()));
            doc.add(new Paragraph("Final Salary   : ₹" + p.getFinalSalary())
                    .setBold());

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("This is a system generated payslip.")
                    .setFontSize(9)
                    .setTextAlignment(TextAlignment.CENTER));

            doc.close();
            return out.toByteArray();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to generate payslip PDF", e);
        }
    }
}
