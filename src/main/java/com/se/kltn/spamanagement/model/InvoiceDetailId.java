package com.se.kltn.spamanagement.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InvoiceDetailId implements Serializable {
    @Serial
    private static final long serialVersionUID = 4751710654285987677L;
    private long invoiceId;
    private long productId;
}
