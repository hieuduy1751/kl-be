package com.se.kltn.spamanagement.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class TreatmentDetailId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long treatmentId;

    private Long customerId;
}
