package com.se.kltn.spamanagement.exception.global;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Date timeStamp;

    private String message;

    private String error;
}
