package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.ErrorMessage;
import com.se.kltn.spamanagement.dto.request.AppointmentDetailRequest;
import com.se.kltn.spamanagement.dto.response.AppointmentDetailResponse;
import com.se.kltn.spamanagement.dto.response.AppointmentResponse;
import com.se.kltn.spamanagement.dto.response.CustomerResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.AppointmentDetail;
import com.se.kltn.spamanagement.model.AppointmentDetailId;
import com.se.kltn.spamanagement.repository.AppointmentDetailRepository;
import com.se.kltn.spamanagement.service.AppointmentDetailService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.APPOINTMENT_DETAIL_NOT_FOUND;

@Service
public class AppointmentDetailServiceImpl implements AppointmentDetailService {

    private final AppointmentDetailRepository appointmentDetailRepository;

    @Autowired
    public AppointmentDetailServiceImpl(AppointmentDetailRepository appointmentDetailRepository) {
        this.appointmentDetailRepository = appointmentDetailRepository;
    }

    @Override
    public AppointmentDetailResponse createAppointmentDetail(Long idCustomer, Long idAppointment, AppointmentDetailRequest appointmentDetailRequest) {
        AppointmentDetail appointmentDetail = MappingData.mapObject(appointmentDetailRequest, AppointmentDetail.class);
        appointmentDetail.setAppointmentDetailId(new AppointmentDetailId(idAppointment, idCustomer));
        return mapToResponse(this.appointmentDetailRepository.save(appointmentDetail));
    }


    @Override
    public AppointmentDetailResponse updateAppointmentDetail(Long idCustomer, Long idAppointment, AppointmentDetailRequest appointmentDetailRequest) {
        AppointmentDetail appointmentDetail = this.appointmentDetailRepository.findById(new AppointmentDetailId(idAppointment, idCustomer)).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_DETAIL_NOT_FOUND));
        NullUtils.updateIfPresent(appointmentDetail::setStatus, appointmentDetailRequest.getStatus());
        return mapToResponse(this.appointmentDetailRepository.save(appointmentDetail));
    }

    @Override
    public List<AppointmentDetailResponse> getListAppointmentDetail(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<AppointmentDetail> appointmentDetails = this.appointmentDetailRepository.findAll(pageable).getContent();
        List<AppointmentDetailResponse> appointmentDetailResponses = MappingData.mapListObject(appointmentDetails, AppointmentDetailResponse.class);
        appointmentDetailResponses.forEach(
                appointmentDetailResponse -> {
                    appointmentDetailResponse.setCustomerResponse(MappingData.mapObject(appointmentDetails
                            .get(appointmentDetailResponses.indexOf(appointmentDetailResponse)), CustomerResponse.class));
                    appointmentDetailResponse.setAppointmentResponse(MappingData.mapObject(appointmentDetails
                            .get(appointmentDetailResponses.indexOf(appointmentDetailResponse)), AppointmentResponse.class));
                }
        );
        return appointmentDetailResponses;
    }

    @Override
    public void deleteAppointmentDetail(Long idCustomer, Long idAppointment) {
        AppointmentDetail appointmentDetail = this.appointmentDetailRepository.findById(new AppointmentDetailId(idAppointment, idCustomer)).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_DETAIL_NOT_FOUND));
        this.appointmentDetailRepository.delete(appointmentDetail);
    }

    private AppointmentDetailResponse mapToResponse(AppointmentDetail appointmentDetail) {
        AppointmentDetailResponse appointmentDetailResponse = MappingData.mapObject(appointmentDetail, AppointmentDetailResponse.class);
        appointmentDetailResponse.setAppointmentResponse(MappingData.mapObject(appointmentDetail.getAppointment(), AppointmentResponse.class));
        appointmentDetailResponse.setCustomerResponse(MappingData.mapObject(appointmentDetail.getCustomer(), CustomerResponse.class));
        return appointmentDetailResponse;
    }
}
