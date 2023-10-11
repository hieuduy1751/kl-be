package com.se.kltn.spamanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.se.kltn.spamanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private String note;

    private String paymentMethod;

    @NotBlank(message = "customerId is required")
    private Long customerId;

    @NotBlank(message = "employeeId is required")
    private Long employeeId;
}
