package com.divary.simpleredis.exception;


import org.springframework.http.HttpStatus;

public class ErrorException extends RuntimeException{

    private final HttpStatus httpStatus;

    public ErrorException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
