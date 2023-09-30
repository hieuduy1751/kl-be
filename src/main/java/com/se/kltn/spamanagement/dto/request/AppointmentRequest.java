package com.se.kltn.spamanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    @NotNull(message = "time is required")
    private Date time;

    private String note;

}
