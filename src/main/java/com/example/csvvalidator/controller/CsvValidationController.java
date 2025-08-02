package com.example.csvvalidator.controller;

import com.example.csvvalidator.model.ValidationError;
import com.example.csvvalidator.model.ValidationResponse;
import com.example.csvvalidator.service.CsvValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CsvValidationController {
    
    @Autowired
    private CsvValidationService csvValidationService;
    
    @PostMapping("/validate-csv")
    public ResponseEntity<ValidationResponse> validateCsv(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                ValidationResponse response = new ValidationResponse("error", 
                    List.of(new ValidationError(0, "File is empty")));
                return ResponseEntity.badRequest().body(response);
            }
            
            // Check if file is CSV
            String contentType = file.getContentType();
            if (contentType == null || (!contentType.equals("text/csv") && !contentType.equals("application/vnd.ms-excel"))) {
                // Also check file extension
                String filename = file.getOriginalFilename();
                if (filename == null || !filename.toLowerCase().endsWith(".csv")) {
                    ValidationResponse response = new ValidationResponse("error", 
                        List.of(new ValidationError(0, "File must be a CSV file")));
                    return ResponseEntity.badRequest().body(response);
                }
            }
            
            List<ValidationError> errors = csvValidationService.validateCsvFile(file);
            
            if (errors.isEmpty()) {
                return ResponseEntity.ok(new ValidationResponse("success", errors));
            } else {
                return ResponseEntity.ok(new ValidationResponse("error", errors));
            }
            
        } catch (IOException e) {
            ValidationResponse response = new ValidationResponse("error", 
                List.of(new ValidationError(0, "Error reading file: " + e.getMessage())));
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ValidationResponse response = new ValidationResponse("error", 
                List.of(new ValidationError(0, "Unexpected error: " + e.getMessage())));
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
