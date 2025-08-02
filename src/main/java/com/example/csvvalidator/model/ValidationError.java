package com.example.csvvalidator.model;

public class ValidationError {
    private int row;
    private String message;

    public ValidationError() {}

    public ValidationError(int row, String message) {
        this.row = row;
        this.message = message;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
