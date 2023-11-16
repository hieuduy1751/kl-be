package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.constants.enums.Category;
import com.se.kltn.spamanagement.constants.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopMostPopularProductResponse {

    private Long id;

    private String name;

    private Double price;

    private String category;

    private String description;

    private Integer numOfAppointment;

}
