package com.graduationProject.gpManagementSystem.dto;

public class ApiResponse<T> {
    private String status;  // "success" or "error"
    private String message; // Human-readable message
    private T data;         // Response data (optional)

    public ApiResponse(String status, String message  , T data  ) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String status, String message  ) {
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}