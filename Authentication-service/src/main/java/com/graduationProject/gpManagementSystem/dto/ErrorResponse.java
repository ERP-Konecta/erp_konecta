package com.graduationProject.gpManagementSystem.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private String details;
    private HttpStatus httpStatus;

    public ErrorResponse(LocalDateTime timestamp, String message, String details, HttpStatus httpStatus) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    
    
    
    // private String errorCode;
    // private String errorMessage;
    // private String timestamp;




}