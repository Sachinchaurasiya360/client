package com.example.csvvalidator.service;

import com.example.csvvalidator.model.ValidationError;
import com.example.csvvalidator.model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvValidationService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public List<ValidationError> validateCsvFile(MultipartFile file) throws IOException {
        List<ValidationError> errors = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            
            int rowNumber = 1; // Start from 1 since header is row 0
            
            for (CSVRecord csvRecord : csvParser) {
                rowNumber++;
                
                // Get values from CSV record
                String name = csvRecord.get("Name");
                String dateOfBirth = csvRecord.get("DateOfBirth");
                
                // Validate name
                if (name == null || name.trim().isEmpty()) {
                    errors.add(new ValidationError(rowNumber, "Name must not be empty"));
                }
                
                // Validate date of birth
                if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
                    errors.add(new ValidationError(rowNumber, "DateOfBirth must not be empty"));
                } else {
                    if (!isValidDateFormat(dateOfBirth.trim())) {
                        errors.add(new ValidationError(rowNumber, "DateOfBirth must be in yyyy-MM-dd format"));
                    } else if (!isValidDate(dateOfBirth.trim())) {
                        errors.add(new ValidationError(rowNumber, "DateOfBirth is not a valid date"));
                    }
                }
            }
        }
        
        return errors;
    }
    
    private boolean isValidDateFormat(String dateString) {
        try {
            LocalDate.parse(dateString, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    private boolean isValidDate(String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
            // Additional validation: check if it's a reasonable date
            LocalDate minDate = LocalDate.of(1900, 1, 1);
            LocalDate maxDate = LocalDate.now();
            return !date.isBefore(minDate) && !date.isAfter(maxDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
