package com.se.kltn.spamanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RatingRequest {

    @NotBlank(message = "comment is require")
    private String comment;

    @NotNull(message = "rating point is require")
    @Range(min = 0, max = 5, message = "point is start from 0 to 5")
    private Integer ratingPoint;

    @NotNull(message = "id customer is require")
    private Long idCustomer;
}
