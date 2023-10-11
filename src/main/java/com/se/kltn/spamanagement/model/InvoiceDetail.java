package com.se.kltn.spamanagement.model;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_details")
public class InvoiceDetail {

    @EmbeddedId
    private InvoiceDetailId id;

    @MapsId("invoiceId")
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_price")
    private Double totalPrice;

}
