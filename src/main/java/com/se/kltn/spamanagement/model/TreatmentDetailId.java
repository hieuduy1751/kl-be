package com.se.kltn.spamanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TreatmentDetailId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long treatmentId;
    private Long customerId;
}
