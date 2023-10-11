package com.se.kltn.spamanagement.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class InvoiceDetailId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long invoiceId;

    private Long productId;
}
