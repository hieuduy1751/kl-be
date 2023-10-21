package com.se.kltn.spamanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailRequest {

    private Integer totalQuantity;

    @NotNull(message = "idProduct is require")
    private Long idProduct;

}
