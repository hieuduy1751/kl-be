package com.se.kltn.spamanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {

    private Long id;

    private String comment;

    private Integer ratingPoint;

    private LocalDate createdDate;

}
