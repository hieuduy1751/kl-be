package com.se.kltn.spamanagement.model;

import com.se.kltn.spamanagement.enums.Status;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private Double price;

    private int quantity;

    private Status status;

    private String imageUrl;

    private Date createdDate;

    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
