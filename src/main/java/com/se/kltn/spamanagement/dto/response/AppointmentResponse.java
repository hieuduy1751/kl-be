package com.se.kltn.spamanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date time;

    private String note;

    private Date createdDate;

    private Date updatedDate;

}
