package com.se.kltn.spamanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    private Long id;

    private Date time;

    private String note;

    private Date createdDate;

    private Date updatedDate;

}
