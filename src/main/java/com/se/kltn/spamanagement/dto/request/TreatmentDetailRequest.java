package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDetailRequest {

    private String note;

    private String imageBefore;

    private String imageCurrent;

    private String imageAfter;
}
