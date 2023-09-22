package com.se.kltn.spamanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BadRequestException extends RuntimeException {
    private final String message;

    public BadRequestException(String message) {
        this.message = message;
    }
}
