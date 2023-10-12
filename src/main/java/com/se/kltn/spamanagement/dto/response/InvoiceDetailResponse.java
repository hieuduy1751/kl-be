package com.se.kltn.spamanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceDetailResponse {

    private ProductResponse productResponse;

    private Integer totalQuantity;

    private Double totalPrice;
}
