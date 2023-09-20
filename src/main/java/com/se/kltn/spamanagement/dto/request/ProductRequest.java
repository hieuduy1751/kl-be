package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class ProductRequest {
    private String name;

    private Double price;

    private Integer quantity;

    private Status status;

    private String imageUrl;

}
