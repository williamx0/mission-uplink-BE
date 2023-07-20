package com.missionuplink.admindashboard.exception;

import org.springframework.http.HttpStatus;

public class AdminException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public AdminException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AdminException(String message, HttpStatus status, String message1) {
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
