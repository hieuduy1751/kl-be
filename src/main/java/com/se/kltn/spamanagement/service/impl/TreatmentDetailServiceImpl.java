package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.TreatmentDetailRequest;
import com.se.kltn.spamanagement.dto.response.TreatmentDetailResponse;
import com.se.kltn.spamanagement.dto.response.TreatmentResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Customer;
import com.se.kltn.spamanagement.model.Treatment;
import com.se.kltn.spamanagement.model.TreatmentDetail;
import com.se.kltn.spamanagement.model.TreatmentDetailId;
import com.se.kltn.spamanagement.model.enums.Status;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.repository.TreatmentDetailRepository;
import com.se.kltn.spamanagement.repository.TreatmentRepository;
import com.se.kltn.spamanagement.service.TreatmentDetailService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
public class TreatmentDetailServiceImpl implements TreatmentDetailService {

    private final TreatmentDetailRepository treatmentDetailRepository;

    private final CustomerRepository customerRepository;

    private final TreatmentRepository treatmentRepository;

    @Autowired
    public TreatmentDetailServiceImpl(TreatmentDetailRepository treatmentDetailRepository, CustomerRepository customerRepository, TreatmentRepository treatmentRepository) {
        this.treatmentDetailRepository = treatmentDetailRepository;
        this.customerRepository = customerRepository;
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public TreatmentResponse addTreatmentDetail(TreatmentDetailId treatmentDetailId, TreatmentDetailRequest treatmentDetailRequest) {
        TreatmentDetail treatmentDetail = MappingData.mapObject(treatmentDetailRequest, TreatmentDetail.class);
        treatmentDetail.setTreatment(getTreatmentById(treatmentDetailId.getTreatmentId()));
        treatmentDetail.setCustomer(getCustomerById(treatmentDetailId.getCustomerId()));
        treatmentDetail.setTreatmentDetailId(treatmentDetailId);
        treatmentDetail.setStatus(Status.NEW);
        return MappingData.mapObject(this.treatmentDetailRepository.save(treatmentDetail), TreatmentResponse.class);
    }


    @Override
    public TreatmentResponse updateTreatmentDetail(TreatmentDetailId treatmentDetailId, TreatmentDetailRequest treatmentDetailRequest) {
        TreatmentDetail treatmentDetail = this.treatmentDetailRepository.findById(treatmentDetailId).orElseThrow(
                () -> new ResourceNotFoundException(TREATMENT_DETAIL_NOT_FOUND));
        NullUtils.updateIfPresent(treatmentDetail::setNote, treatmentDetailRequest.getNote());
        NullUtils.updateIfPresent(treatmentDetail::setImageBefore, treatmentDetailRequest.getImageBefore());
        NullUtils.updateIfPresent(treatmentDetail::setImageCurrent, treatmentDetailRequest.getImageCurrent());
        NullUtils.updateIfPresent(treatmentDetail::setImageResult, treatmentDetailRequest.getImageAfter());
        return MappingData.mapObject(this.treatmentDetailRepository.save(treatmentDetail), TreatmentResponse.class);
    }

    @Override
    public TreatmentDetailResponse getTreatmentDetailByCustomer(Long customerId) {
        return null;
    }

    @Override
    public List<TreatmentDetailResponse> getListTreatmentDetail(int page, int size) {
        return null;
    }

    @Override
    public void deleteTreatmentDetail(TreatmentDetailId treatmentDetailId) {

    }

    private Customer getCustomerById(Long customerId) {
        return this.customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
    }

    private Treatment getTreatmentById(Long treatmentId) {
        return this.treatmentRepository.findById(treatmentId).orElseThrow(() -> new ResourceNotFoundException(TREATMENT_NOT_FOUND));
    }
}
