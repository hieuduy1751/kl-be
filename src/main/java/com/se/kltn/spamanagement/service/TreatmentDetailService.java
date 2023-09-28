package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.TreatmentDetailRequest;
import com.se.kltn.spamanagement.dto.response.TreatmentDetailResponse;
import com.se.kltn.spamanagement.dto.response.TreatmentResponse;
import com.se.kltn.spamanagement.model.TreatmentDetail;
import com.se.kltn.spamanagement.model.TreatmentDetailId;

import java.util.List;

public interface TreatmentDetailService {

    TreatmentResponse addTreatmentDetail(TreatmentDetailId treatmentDetailId, TreatmentDetailRequest treatmentRequest);

    TreatmentResponse updateTreatmentDetail(TreatmentDetailId treatmentDetailId, TreatmentDetailRequest treatmentDetailRequest);

    TreatmentDetailResponse getTreatmentDetailByCustomer(Long customerId);

    List<TreatmentDetailResponse> getListTreatmentDetail(int page, int size);

    void deleteTreatmentDetail(TreatmentDetailId treatmentDetailId);
}
