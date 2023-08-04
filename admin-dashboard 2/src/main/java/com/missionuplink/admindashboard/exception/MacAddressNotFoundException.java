package com.missionuplink.admindashboard.exception;

import org.springframework.http.HttpStatus;

public class MacAddressNotFoundException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public MacAddressNotFoundException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MacAddressNotFoundException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
