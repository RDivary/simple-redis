package com.divary.simpleredis.config;

import com.divary.simpleredis.dto.response.Response;
import com.divary.simpleredis.exception.ErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {ErrorException.class})
    public ResponseEntity<Response<Object>> save(ErrorException e) {

        return ResponseEntity.status(e.getHttpStatus()).body(getResponseBody(e.getHttpStatus(), e.getMessage()));
    }

    private Response getResponseBody(HttpStatus httpStatus, String message) {

        return new Response(message, null, httpStatus);
    }
}
