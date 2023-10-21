package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.constants.enums.PaymentMethod;
import com.se.kltn.spamanagement.constants.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    private Long id;

    private Double totalAmount;

    private Date dueDate;

    private String note;

    private PaymentMethod paymentMethod;

    private Status status;

    private Date createdDate;

    private Date updatedDate;

    private List<InvoiceDetailResponse> invoiceDetailResponses;
}
