package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDetailResponse {

    private AppointmentResponse appointmentResponse;

    private CustomerResponse customerResponse;

    @Enumerated(EnumType.STRING)
    private Status status;
}
