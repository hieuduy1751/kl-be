package com.se.kltn.spamanagement.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
public class ProductRequest {
    @NotBlank(message = "name product is require")
    private String name;

    @Range(min = 0, message = "price must not negative")
    private Double price;

    @NotNull(message = "quantity is require")
    @Range(min = 0, message = "quantity must not negative")
    private Integer quantity;

    private String imageUrl;

    @NotBlank(message = "category is require and must be PRODUCT or SERVICE or TREATMENT")
    private String category;

    private String description;

    private String supplier;

    private String status;
}
