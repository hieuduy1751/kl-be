package com.se.kltn.spamanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.se.kltn.spamanagement.constants.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private String note;

    @NotBlank(message = "paymentMethod is require and must be CAST or CREDIT_CARD or BANKING")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "customerId is required")
    private Long customerId;

    @NotBlank(message = "employeeId is required")
    private Long employeeId;

    private List<InvoiceDetailRequest> invoiceDetailRequests;
}
