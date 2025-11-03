package com.example.save_template_backend.controller;

import com.example.save_template_backend.service.PromotionNonHmService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotion-nonhm")
@CrossOrigin(origins = "http://localhost:3000")
public class PromotionNonHmController {

    private final PromotionNonHmService promotionNonHmService;

    public PromotionNonHmController(PromotionNonHmService promotionNonHmService) {
        this.promotionNonHmService = promotionNonHmService;
    }

    @PostMapping("/save-html")
    public ResponseEntity<String> saveHtml(@RequestBody TemplateRequest request) {
        promotionNonHmService.saveHtmlToFile(request.getHtml(), request.getFilename());
        return ResponseEntity.ok("HTML saved successfully!");
    }

    @PostMapping("/save-and-pdf")
    public ResponseEntity<byte[]> saveAndGeneratePdf(@RequestBody TemplateRequest request) {
        byte[] pdfBytes = promotionNonHmService.saveAndGeneratePdf(request.getHtml(), request.getFilename());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(request.getFilename() + ".pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    static class TemplateRequest {
        private String html;
        private String filename;

        public String getHtml() { return html; }
        public void setHtml(String html) { this.html = html; }

        public String getFilename() { return filename; }
        public void setFilename(String filename) { this.filename = filename; }
    }
}

