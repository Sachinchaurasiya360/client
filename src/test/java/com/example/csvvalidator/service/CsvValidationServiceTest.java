package com.example.csvvalidator.service;

import com.example.csvvalidator.model.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvValidationServiceTest {

    private final CsvValidationService csvValidationService = new CsvValidationService();

    @Test
    void testValidCsv() throws Exception {
        String csvContent = "Name,DateOfBirth\nJohn Doe,1990-05-15\nJane Smith,1985-07-20";
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.csv", "text/csv", csvContent.getBytes());

        List<ValidationError> errors = csvValidationService.validateCsvFile(file);

        assertTrue(errors.isEmpty());
    }

    @Test
    void testInvalidDateFormats() throws Exception {
        String csvContent = "Name,DateOfBirth\nJohn Doe,1990/05/15\nJane Smith,15-05-1990\nBob Johnson,not-a-date";
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.csv", "text/csv", csvContent.getBytes());

        List<ValidationError> errors = csvValidationService.validateCsvFile(file);

        assertEquals(3, errors.size());
        assertEquals(2, errors.get(0).getRow());
        assertTrue(errors.get(0).getMessage().contains("yyyy-MM-dd format"));
    }

    @Test
    void testEmptyFields() throws Exception {
        String csvContent = "Name,DateOfBirth\n,1990-05-15\nJohn Doe,\n,";
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.csv", "text/csv", csvContent.getBytes());

        List<ValidationError> errors = csvValidationService.validateCsvFile(file);

        assertEquals(4, errors.size()); // Empty name (row 2), empty date (row 3), empty name and date (row 4)
    }

    @Test
    void testInvalidDate() throws Exception {
        String csvContent = "Name,DateOfBirth\nJohn Doe,1993-02-30"; // February 30th doesn't exist
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.csv", "text/csv", csvContent.getBytes());

        List<ValidationError> errors = csvValidationService.validateCsvFile(file);

        assertEquals(1, errors.size());
        assertTrue(errors.get(0).getMessage().contains("not a valid date"));
    }
}
