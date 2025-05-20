package com.twozo.smapp.model.dto;

public class ResponseDto {
    private String message;

    public ResponseDto() {}

    public ResponseDto(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}

