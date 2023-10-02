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
@Table(name = "appointment_details")
public class AppointmentDetail {
    @EmbeddedId
    private AppointmentDetailId appointmentDetailId;

    @MapsId("appointmentId")
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @MapsId("customerId")
    @ManyToOne
    @JoinColumn(name = "customerI_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Status status;
}
