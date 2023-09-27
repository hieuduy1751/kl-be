package com.se.kltn.spamanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentResponse {

    private Long id;

    private String name;

    private String description;
}
