package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.constants.enums.ProductType;
import com.se.kltn.spamanagement.constants.enums.Status;
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

    @NotBlank(message = "product type is require")
    private ProductType productType;

    private String description;

    private String supplier;

    private Status status;
}
