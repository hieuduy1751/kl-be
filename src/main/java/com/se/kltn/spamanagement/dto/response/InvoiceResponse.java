package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    private Long id;

    private Double totalAmount;

    private Date dueDate;

    private String note;

    private String paymentMethod;

    private Status status;

    private Date createdDate;

    private Date updatedDate;
}
