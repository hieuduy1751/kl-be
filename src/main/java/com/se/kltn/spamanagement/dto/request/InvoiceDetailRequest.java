package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailRequest {

    private Integer totalQuantity;

    @NotNull(message = "idInvoice is require")
    private Long idInvoice;

    @NotNull(message = "idProduct is require")
    private Long idProduct;

    private Long idTreatment;

}
