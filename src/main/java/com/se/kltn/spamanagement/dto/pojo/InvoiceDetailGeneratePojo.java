package com.se.kltn.spamanagement.dto.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailGeneratePojo {

    private Integer index;

    private String productName;

    private String productType;

    private Integer productQuantity;

    private String productPrice;

    private String totalPrice;
}
