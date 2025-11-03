package com.example.save_template_backend.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class PromotionHmService {

    private static final String TEMPLATE_DIR = "src/main/resources/templates/";

    // Save HTML to file
    public void saveHtmlToFile(String html, String filename) {
        try {
            Path filePath = Paths.get(TEMPLATE_DIR + filename + ".html");
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, html);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save HTML file: " + e.getMessage());
        }
    }

    // Save HTML and generate PDF
    public byte[] saveAndGeneratePdf(String html, String filename) {
        try {
            Path htmlPath = Paths.get(TEMPLATE_DIR + filename + ".html");
            Files.createDirectories(htmlPath.getParent());
            Files.writeString(htmlPath, html);
            return generatePdfFromHtml(htmlPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save and generate PDF: " + e.getMessage());
        }
    }

    // Generate PDF from HTML file
    private byte[] generatePdfFromHtml(Path htmlFilePath) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            String html = Files.readString(htmlFilePath)
                    .replaceAll("<br>", "<br/>")
                    .replaceAll("<img([^>]+)(?<!/)>", "<img$1/>");

            // Replace placeholders with static sample data
            Map<String, String> data = new HashMap<>();
            data.put("district_name", "Nellore District");
            data.put("officer_name", "Sri V. Prasad, DEO");
            data.put("rc_number", "RC/2025/0456");
            data.put("date", "03-Nov-2025");
            data.put("teacher_name", "Smt. K. Anitha");
            data.put("zone_name", "3");
            data.put("zone_number", "3");
            data.put("mandal_name", "Kavali");
            data.put("employee_id", "EMP33445");
            data.put("designation", "School Assistant (Science)");
            data.put("subject", "Physical Science");
            data.put("working_school", "ZPHS, Kavali");
            data.put("promoted_as", "Head Master");
            data.put("posted_school", "ZPHS, Alluru");

            for (Map.Entry<String, String> entry : data.entrySet()) {
                html = html.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            String fullHtml = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset='UTF-8'/>
              <style>
                body { font-family: "Times New Roman", serif; line-height: 1.6; font-size: 14px; }
                .ql-align-center { text-align: center; }
                .ql-align-right { text-align: right; }
                .ql-align-justify { text-align: justify; }
                ul { margin-left: 20px; }
              </style>
            </head>
            <body>""" + html + "</body></html>";

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(fullHtml, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new IOException("PDF generation failed: " + e.getMessage());
        }
    }
}
