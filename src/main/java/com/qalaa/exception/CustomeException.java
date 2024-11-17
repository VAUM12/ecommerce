package com.qalaa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomeException extends RuntimeException {

    public CustomeException(String message) {
        super(message);
    }
}
