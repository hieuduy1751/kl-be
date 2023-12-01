package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.TreatmentDetailRequest;
import com.se.kltn.spamanagement.dto.response.TreatmentDetailResponse;
import com.se.kltn.spamanagement.model.TreatmentDetailId;

import java.util.List;

public interface TreatmentDetailService {

    TreatmentDetailResponse addTreatmentDetail(TreatmentDetailId treatmentDetailId, TreatmentDetailRequest treatmentRequest);

    TreatmentDetailResponse updateTreatmentDetail(TreatmentDetailId treatmentDetailId, TreatmentDetailRequest treatmentDetailRequest);

    List<TreatmentDetailResponse> getTreatmentDetailByCustomer(Long customerId);

    List<TreatmentDetailResponse> getListTreatmentDetail(int page, int size);

    void deleteTreatmentDetail(TreatmentDetailId treatmentDetailId);
}
