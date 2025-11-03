package com.example.save_template_backend.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class PromotionNonHmService {

    private static final String TEMPLATE_DIR = "src/main/resources/templates/";

    public void saveHtmlToFile(String html, String filename) {
        try {
            Path filePath = Paths.get(TEMPLATE_DIR + filename + ".html");
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, html);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save HTML file: " + e.getMessage());
        }
    }

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

    private byte[] generatePdfFromHtml(Path htmlFilePath) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            String html = Files.readString(htmlFilePath)
                    .replaceAll("<br>", "<br/>")
                    .replaceAll("<img([^>]+)(?<!/)>", "<img$1/>");

            // Map static data placeholders
            Map<String, String> data = new HashMap<>();
            data.put("district_name", "Krishna District");
            data.put("officer_name", "Sri Ramesh Kumar, DEO");
            data.put("rc_number", "RC/2025/0012");
            data.put("date", "03-Nov-2025");
            data.put("teacher_name", "Smt. L. Padmavathi");
            data.put("designation", "School Assistant");
            data.put("employee_id", "EMP78965");
            data.put("mandal_name", "Vuyyuru");
            data.put("working_school", "ZPHS, Vuyyuru");
            data.put("promoted_as", "Head Master");
            data.put("posted_school", "ZPHS, Pamarru");

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

