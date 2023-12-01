package com.se.kltn.spamanagement.model;

import com.se.kltn.spamanagement.constants.enums.Status;
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

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

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

    @Column(name = "updated_date")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
