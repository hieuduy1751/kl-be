package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.constants.enums.PaymentMethod;
import com.se.kltn.spamanagement.constants.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceUpdateRequest {
    private Date dueDate;

    private String note;

    private PaymentMethod paymentMethod;

    private Status status;

    private Long customerId;

    private Long employeeId;
}
