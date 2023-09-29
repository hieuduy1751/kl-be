package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.TreatmentDetailRequest;
import com.se.kltn.spamanagement.dto.response.CustomerResponse;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public TreatmentDetailResponse addTreatmentDetail(TreatmentDetailId treatmentDetailId, TreatmentDetailRequest treatmentDetailRequest) {
        TreatmentDetail treatmentDetail = MappingData.mapObject(treatmentDetailRequest, TreatmentDetail.class);
        treatmentDetail.setTreatment(getTreatmentById(treatmentDetailId.getTreatmentId()));
        treatmentDetail.setCustomer(getCustomerById(treatmentDetailId.getCustomerId()));
        treatmentDetail.setTreatmentDetailId(treatmentDetailId);
        treatmentDetail.setStatus(Status.NEW);
        return mapToTreatmentDetailResponse(this.treatmentDetailRepository.save(treatmentDetail));
    }


    @Override
    public TreatmentDetailResponse updateTreatmentDetail(TreatmentDetailId treatmentDetailId, TreatmentDetailRequest treatmentDetailRequest) {
        TreatmentDetail treatmentDetail = this.treatmentDetailRepository.findById(treatmentDetailId).orElseThrow(
                () -> new ResourceNotFoundException(TREATMENT_DETAIL_NOT_FOUND));
        NullUtils.updateIfPresent(treatmentDetail::setNote, treatmentDetailRequest.getNote());
        NullUtils.updateIfPresent(treatmentDetail::setImageBefore, treatmentDetailRequest.getImageBefore());
        NullUtils.updateIfPresent(treatmentDetail::setImageCurrent, treatmentDetailRequest.getImageCurrent());
        NullUtils.updateIfPresent(treatmentDetail::setImageResult, treatmentDetailRequest.getImageAfter());
        return mapToTreatmentDetailResponse(this.treatmentDetailRepository.save(treatmentDetail));
    }

    @Override
    public List<TreatmentDetailResponse> getTreatmentDetailByCustomer(Long customerId) {
        List<TreatmentDetail> treatmentDetails = this.treatmentDetailRepository.getTreatmentDetailsByCustomer_Id(customerId);
        return mappingTreatmentDetails(treatmentDetails);
    }


    @Override
    public List<TreatmentDetailResponse> getListTreatmentDetail(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<TreatmentDetail> treatmentDetails = this.treatmentDetailRepository.findAll(pageable).getContent();
        return mappingTreatmentDetails(treatmentDetails);
    }

    @Override
    public void deleteTreatmentDetail(TreatmentDetailId treatmentDetailId) {
        TreatmentDetail treatmentDetail = this.treatmentDetailRepository.findById(treatmentDetailId).orElseThrow(
                () -> new ResourceNotFoundException(TREATMENT_DETAIL_NOT_FOUND));
        this.treatmentDetailRepository.delete(treatmentDetail);
    }

    private Customer getCustomerById(Long customerId) {
        return this.customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
    }

    private Treatment getTreatmentById(Long treatmentId) {
        return this.treatmentRepository.findById(treatmentId).orElseThrow(() -> new ResourceNotFoundException(TREATMENT_NOT_FOUND));
    }

    private List<TreatmentDetailResponse> mappingTreatmentDetails(List<TreatmentDetail> treatmentDetails) {
        List<TreatmentDetailResponse> treatmentDetailResponses = MappingData.mapListObject(treatmentDetails, TreatmentDetailResponse.class);
        treatmentDetailResponses.forEach(
                treatmentDetailResponse -> {
                    treatmentDetailResponse.setCustomerResponse(MappingData.mapObject(treatmentDetails
                            .get(treatmentDetailResponses.indexOf(treatmentDetailResponse)).getCustomer(), CustomerResponse.class));
                    treatmentDetailResponse.setTreatmentResponse(MappingData.mapObject(treatmentDetails
                            .get(treatmentDetailResponses.indexOf(treatmentDetailResponse)).getTreatment(), TreatmentResponse.class));
                }
        );
        return treatmentDetailResponses;
    }

    private TreatmentDetailResponse mapToTreatmentDetailResponse(TreatmentDetail treatmentDetail) {
        TreatmentDetailResponse treatmentDetailResponse = MappingData.mapObject(treatmentDetail, TreatmentDetailResponse.class);
        treatmentDetailResponse.setCustomerResponse(MappingData.mapObject(treatmentDetail.getCustomer(), CustomerResponse.class));
        treatmentDetailResponse.setTreatmentResponse(MappingData.mapObject(treatmentDetail.getTreatment(), TreatmentResponse.class));
        return treatmentDetailResponse;
    }

}
