package com.se.kltn.spamanagement.model;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "treatment_details")
public class TreatmentDetail {
    @EmbeddedId
    private TreatmentDetailId treatmentDetailId;

    @MapsId("treatmentId")
    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @MapsId("customerId")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JoinColumn(name = "image_before")
    private String imageBefore;

    @JoinColumn(name = "image_current")
    private String imageCurrent;

    @JoinColumn(name = "image_result")
    private String imageResult;

    private String note;

    @Column(name = "created_date")
    private Date createdDate;

}
