package com.se.kltn.spamanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SendEmailException extends RuntimeException{
    private final String message;

    public SendEmailException(String message) {
        this.message = message;
    }

}
