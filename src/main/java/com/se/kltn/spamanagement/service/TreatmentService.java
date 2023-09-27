package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.TreatmentRequest;
import com.se.kltn.spamanagement.dto.response.TreatmentResponse;

import java.util.List;

public interface TreatmentService {
    TreatmentResponse createTreatment(TreatmentRequest treatmentRequest);

    TreatmentResponse updateTreatment(Long id, TreatmentRequest treatmentRequest);

    List<TreatmentResponse> getListTreatment(int page, int size);

    void deleteTreatment(Long id);
}
