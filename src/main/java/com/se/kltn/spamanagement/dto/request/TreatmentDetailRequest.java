package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.constants.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDetailRequest {

    private String note;

    private String imageBefore;

    private String imageCurrent;

    private String imageResult;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private Status status;

    private Long idEmployee;
}
