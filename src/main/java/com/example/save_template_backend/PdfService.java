//package com.example.save_template_backend;
//
//import org.springframework.stereotype.Service;
//import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
//import java.io.*;
//import java.nio.file.*;
//
//@Service
//public class PdfService {
//
//    public Path saveHtmlToResources(String html, String filename) throws IOException {
//        Path folder = Paths.get("src/main/resources/templates/");
//        if (!Files.exists(folder)) Files.createDirectories(folder);
//        Path path = folder.resolve(filename);
//        Files.writeString(path, html);
//        return path;
//    }
//
//    public byte[] generatePdfFromHtml(Path htmlFilePath) throws IOException {
//        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
//            String html = Files.readString(htmlFilePath)
//                    .replaceAll("<br>", "<br/>")
//                    .replaceAll("<img([^>]+)(?<!/)>", "<img$1/>");
//
//            String fullHtml = """
//            <!DOCTYPE html>
//            <html>
//            <head>
//              <meta charset='UTF-8'/>
//              <style>
//                body { font-family: "Times New Roman", serif; line-height: 1.6; }
//                .ql-align-center { text-align: center; }
//                .ql-align-right { text-align: right; }
//                .ql-align-justify { text-align: justify; }
//              </style>
//            </head>
//            <body>""" + html + "</body></html>";
//
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.withHtmlContent(fullHtml, null);
//            builder.toStream(os);
//            builder.run();
//            return os.toByteArray();
//        } catch (Exception e) {
//            throw new IOException("PDF generation failed: " + e.getMessage());
//        }
//    }
//}
