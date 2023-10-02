package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.AppointmentDetailRequest;
import com.se.kltn.spamanagement.dto.response.AppointmentDetailResponse;

import java.util.List;

public interface AppointmentDetailService {

    AppointmentDetailResponse createAppointmentDetail(Long idCustomer, Long idAppointment, AppointmentDetailRequest appointmentDetailRequest);

    AppointmentDetailResponse updateAppointmentDetail(Long idCustomer, Long idAppointment, AppointmentDetailRequest appointmentDetailRequest);

    List<AppointmentDetailResponse> getListAppointmentDetail(int page, int size);

    void deleteAppointmentDetail(Long idCustomer, Long idAppointment);
}
