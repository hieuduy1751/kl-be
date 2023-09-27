package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.TreatmentRequest;
import com.se.kltn.spamanagement.dto.response.TreatmentResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Treatment;
import com.se.kltn.spamanagement.repository.TreatmentRepository;
import com.se.kltn.spamanagement.service.TreatmentService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.TREATMENT_NOT_FOUND;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;

    @Autowired
    public TreatmentServiceImpl(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public TreatmentResponse createTreatment(TreatmentRequest treatmentRequest) {
        Treatment treatment = MappingData.mapObject(treatmentRequest, Treatment.class);
        return MappingData.mapObject(this.treatmentRepository.save(treatment), TreatmentResponse.class);
    }

    @Override
    public TreatmentResponse updateTreatment(Long id, TreatmentRequest treatmentRequest) {
        Treatment treatment = this.treatmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(TREATMENT_NOT_FOUND));
        NullUtils.updateIfPresent(treatment::setName, treatmentRequest.getName());
        NullUtils.updateIfPresent(treatment::setDescription, treatmentRequest.getDescription());
        return MappingData.mapObject(this.treatmentRepository.save(treatment), TreatmentResponse.class);
    }

    @Override
    public List<TreatmentResponse> getListTreatment(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Treatment> treatments = this.treatmentRepository.findAll(pageable).getContent();
        return MappingData.mapListObject(treatments, TreatmentResponse.class);
    }

    @Override
    public void deleteTreatment(Long id) {
        Treatment treatment = this.treatmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(TREATMENT_NOT_FOUND));
        this.treatmentRepository.delete(treatment);
    }
}
