package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class ProductResponse {

    private Long id;

    private String name;

    private Double price;

    private Integer quantity;

    private Status status;

    private String imageUrl;

    private Date createdDate;

    private Date updatedDate;
}
