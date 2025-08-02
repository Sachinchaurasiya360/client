# CSV Upload & Date Validation

A Spring Boot web application that accepts CSV file uploads and validates data for missing fields and invalid date formats.

## Features

- **REST API** for CSV file upload and validation
- **Real-time validation** of CSV data
- **User-friendly web interface** with drag-and-drop file upload
- **Comprehensive error reporting** with row-wise validation results
- **Date format validation** (yyyy-MM-dd format required)
- **Missing field detection** for Name and DateOfBirth columns

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Quick Start

### 1. Clone or Download the Project

```bash
# If using Git
git clone <repository-url>
cd csv-upload-validator

# Or download and extract the ZIP file
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Test the Application

1. Open your browser and go to `http://localhost:8080`
2. Upload the provided `sample_users.csv` file to see validation in action
3. The sample file contains various validation errors for testing

## API Endpoints

### POST /api/validate-csv

Accepts a CSV file and returns validation results.

**Request:**

- Method: POST
- Content-Type: multipart/form-data
- Body: CSV file with "file" parameter

**Response:**

```json
{
  "status": "success|error",
  "errors": [
    {
      "row": 2,
      "message": "DateOfBirth must be in yyyy-MM-dd format"
    }
  ]
}
```

## CSV Format Requirements

Your CSV file must have the following structure:

```csv
Name,DateOfBirth
John Doe,1990-05-15
Jane Smith,1985-07-20
```

### Validation Rules

1. **Name column**: Must not be empty
2. **DateOfBirth column**:
   - Must not be empty
   - Must be in `yyyy-MM-dd` format (e.g., `1990-05-15`)
   - Must be a valid date
   - Must be between 1900-01-01 and current date

## Sample Test Data

Use the provided `sample_users.csv` file which contains:

- Valid entries
- Invalid date formats (1994/03/30, 30-04-1991, etc.)
- Empty fields
- Invalid dates (1993-02-30)

## Testing

Run the test suite:

```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/example/csvvalidator/
│   │   ├── CsvValidatorApplication.java
│   │   ├── controller/
│   │   │   ├── CsvValidationController.java
│   │   │   └── HomeController.java
│   │   ├── model/
│   │   │   ├── User.java
│   │   │   ├── ValidationError.java
│   │   │   └── ValidationResponse.java
│   │   └── service/
│   │       └── CsvValidationService.java
│   └── resources/
│       ├── application.properties
│       └── templates/
│           └── index.html
└── test/
    └── java/com/example/csvvalidator/
        └── service/
            └── CsvValidationServiceTest.java
```

## Configuration

The application can be configured via `application.properties`:

- `server.port`: Server port (default: 8080)
- `spring.servlet.multipart.max-file-size`: Maximum file size (default: 10MB)
- `spring.servlet.multipart.max-request-size`: Maximum request size (default: 10MB)

## Technologies Used

- **Backend**: Spring Boot 3.2.0, Java 17
- **CSV Processing**: Apache Commons CSV
- **Frontend**: HTML5, JavaScript, CSS3
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Testing**: JUnit 5

## Error Handling

The application provides comprehensive error handling:

- File format validation (must be CSV)
- File size limits
- Row-by-row validation with specific error messages
- Graceful handling of malformed CSV files

## Browser Support

The web interface supports modern browsers with:

- HTML5 File API support
- Drag and drop functionality
- Fetch API for AJAX requests

## Troubleshooting

### Common Issues

1. **File Upload Fails**: Check file size (max 10MB) and ensure it's a CSV file
2. **Port Already in Use**: Change the port in `application.properties`
3. **Java Version**: Ensure Java 17 or higher is installed

### Debug Mode

Enable debug logging by setting:

```properties
logging.level.com.example.csvvalidator=DEBUG
```

## License

This project is for educational purposes as part of an intern assignment.
