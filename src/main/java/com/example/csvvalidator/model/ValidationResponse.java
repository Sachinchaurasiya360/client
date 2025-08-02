package com.example.csvvalidator.model;

import java.util.List;

public class ValidationResponse {
    private String status;
    private List<ValidationError> errors;

    public ValidationResponse() {}

    public ValidationResponse(String status, List<ValidationError> errors) {
        this.status = status;
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }
}
