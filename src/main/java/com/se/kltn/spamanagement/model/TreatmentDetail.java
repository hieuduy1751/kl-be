package com.se.kltn.spamanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "treatmentDetails")
public class TreatmentDetail {
    @EmbeddedId
    private TreatmentDetailId treatmentDetailId;

    @MapsId("treatmentId")
    private Treatment treatment;

    @MapsId("customerId")
    private Customer customer;



}
