package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.model.enums.Category;
import com.se.kltn.spamanagement.model.enums.Status;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.Date;

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

    private String category;

    private String description;
}
