

# CSV Upload & Date Validation Project

This is a Spring Boot web application for CSV file upload and validation.

## Project Structure

- Backend: Spring Boot REST API with CSV parsing and validation
- Frontend: Simple HTML page with JavaScript for file upload
- Validation: Checks for missing fields and invalid date formats (yyyy-MM-dd)

## Key Components

- `CsvValidationService`: Core validation logic
- `CsvValidationController`: REST API endpoints
- `ValidationError` & `ValidationResponse`: Data models for API responses
- Frontend uses HTML5 File API with drag-and-drop support

## Technologies Used

- Java 17
- Spring Boot 3.2.0
- Apache Commons CSV
- Thymeleaf
- Maven
