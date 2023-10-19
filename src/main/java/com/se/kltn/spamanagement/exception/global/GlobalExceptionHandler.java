package com.se.kltn.spamanagement.exception.global;

import com.se.kltn.spamanagement.exception.BadRequestException;
import com.se.kltn.spamanagement.exception.JwtAuthenticationException;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.exception.SendEmailException;
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

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> badRequestExceptionHandler(BadRequestException e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({JwtAuthenticationException.class})
    public ResponseEntity<Object> jwtAuthenticationExceptionHandler(JwtAuthenticationException e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler({SendEmailException.class})
    public ResponseEntity<Object> sendEmailExceptionHandler(SendEmailException e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
