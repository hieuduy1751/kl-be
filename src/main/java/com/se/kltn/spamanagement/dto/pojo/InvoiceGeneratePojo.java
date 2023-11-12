package com.se.kltn.spamanagement.dto.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceGeneratePojo {

    private Long idInvoice;

    private String employeeName;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private String paymentMethod;

    private Double totalAmount;

    private Date createdDate;

}
