package com.twozo.smapp.model;

public class ApiResponse {
    private String message;

    public ApiResponse() {}

    public ApiResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}

