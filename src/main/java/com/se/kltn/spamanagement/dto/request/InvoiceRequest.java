package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    private String note;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank(message = "customerId is required")
    private Long customerId;

    @NotBlank(message = "employeeId is required")
    private Long employeeId;
}
