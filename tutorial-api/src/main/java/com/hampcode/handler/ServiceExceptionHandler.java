package com.hampcode.handler;


import com.hampcode.exception.ExceptionResponse;
import com.hampcode.exception.GeneralServiceException;
import com.hampcode.exception.IncorrectRequestException;
import com.hampcode.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(IncorrectRequestException.class)
    public ResponseEntity<Object> handleIncorrectRequest(IncorrectRequestException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(NotFoundException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(GeneralServiceException.class)
    public ResponseEntity<Object> handleGeneralServiceException(GeneralServiceException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }
}