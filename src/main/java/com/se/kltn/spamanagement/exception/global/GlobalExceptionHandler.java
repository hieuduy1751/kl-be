package com.se.kltn.spamanagement.exception.global;

import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> resourceNotFoundHandler(ResourceNotFoundException notFoundException, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), notFoundException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
