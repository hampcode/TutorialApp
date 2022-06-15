package com.hampcode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncorrectRequestException extends RuntimeException{
    public IncorrectRequestException(String message){
        super(message);
    }
}
