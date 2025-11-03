//package com.example.save_template_backend;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import java.nio.file.Path;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/templates")
//@CrossOrigin(origins = "*")
//public class TemplateController {
//
//    @Autowired
//    private PdfService pdfService;
//
//    @PostMapping("/save-html")
//    public ResponseEntity<String> saveHtml(@RequestBody Map<String, String> body) {
//        String html = body.get("html");
//        String filename = body.getOrDefault("filename", "generated");
//        try {
//            Path saved = pdfService.saveHtmlToResources(html, filename + ".html");
//            return ResponseEntity.ok("Saved to: " + saved.toAbsolutePath());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/save-and-pdf")
//    public ResponseEntity<byte[]> saveAndGeneratePdf(@RequestBody Map<String, String> body) {
//        try {
//            String html = body.get("html");
//            String filename = body.getOrDefault("filename", "generated");
//
//            Map<String, String> placeholders = new HashMap<>();
//            placeholders.put("district_name", "Anantapur");
//            placeholders.put("officer_name", "Mr. Ramesh Kumar");
//            placeholders.put("rc_number", "RC-56789/AP");
//            placeholders.put("date", "03-11-2025");
//            placeholders.put("teacher_name", "Smt. Lakshmi Devi");
//            placeholders.put("school_name", "Z.P. High School, Anantapur");
//            placeholders.put("designation", "School Assistant");
//            placeholders.put("subject", "Mathematics");
//            placeholders.put("mandal_name", "Dharmavaram");
//            placeholders.put("employee_id", "EMP123456");
//            placeholders.put("working_school", "Govt High School, Kadiri");
//            placeholders.put("promoted_as", "Headmaster Gr.II");
//            placeholders.put("posted_school", "MPUP School, Hindupur");
//
//            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
//                html = html.replace("{{" + entry.getKey() + "}}", entry.getValue());
//            }
//
//            Path savedHtml = pdfService.saveHtmlToResources(html, filename + ".html");
//            byte[] pdf = pdfService.generatePdfFromHtml(savedHtml);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDisposition(ContentDisposition.attachment().filename(filename + ".pdf").build());
//            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//}
