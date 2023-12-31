package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.AppointmentRequest;
import com.se.kltn.spamanagement.dto.response.AppointmentResponse;

import java.text.ParseException;
import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) throws ParseException;

    AppointmentResponse updateAppointment(Long id, AppointmentRequest appointmentRequest);

    void deleteAppointment(Long id);

    List<AppointmentResponse> getAllAppointment(int page, int size);

    List<AppointmentResponse> getAllAppointmentByCustomer(Long idCustomer, int page, int size);

}
