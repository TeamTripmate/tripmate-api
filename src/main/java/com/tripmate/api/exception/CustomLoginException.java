package com.tripmate.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomLoginException extends RuntimeException {

    private final HttpStatus status;

    public CustomLoginException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
