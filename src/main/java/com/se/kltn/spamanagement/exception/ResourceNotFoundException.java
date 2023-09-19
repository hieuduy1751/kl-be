package com.se.kltn.spamanagement.exception;

import com.se.kltn.spamanagement.exception.global.ErrorResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Getter
public class ResourceNotFoundException extends RuntimeException{

}

