package com.se.kltn.spamanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentRequest {

    @NotBlank(message = "name of treatment is require")
    private String name;

    private String description;
}
