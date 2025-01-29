package com.transformer.transformer.controller;

import com.transformer.transformer.model.JsonDocument;
import com.transformer.transformer.service.PojoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pojo")
public class PojoController {

    private final PojoService pojoService;

    public PojoController(PojoService pojoService) {
        this.pojoService = pojoService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePojo(@RequestBody JsonDocument document) {
        try {
            pojoService.saveAndGeneratePojo(document);
            return ResponseEntity.ok("POJO generated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}