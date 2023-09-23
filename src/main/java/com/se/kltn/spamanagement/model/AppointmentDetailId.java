package com.se.kltn.spamanagement.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AppointmentDetailId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long appointmentId;

    private Long customerId;
}