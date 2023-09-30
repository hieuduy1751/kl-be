package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.AppointmentRequest;
import com.se.kltn.spamanagement.dto.response.AppointmentResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Appointment;
import com.se.kltn.spamanagement.repository.AppointmentRepository;
import com.se.kltn.spamanagement.service.AppointmentService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.APPOINTMENT_NOT_FOUND;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = MappingData.mapObject(appointmentRequest, Appointment.class);
        appointment.setCreatedDate(new Date());
        appointment.setUpdatedDate(new Date());
        return MappingData.mapObject(this.appointmentRepository.save(appointment), AppointmentResponse.class);
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest appointmentRequest) {
        Appointment appointment=this.appointmentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(APPOINTMENT_NOT_FOUND));
        appointment.setUpdatedDate(new Date());
        NullUtils.updateIfPresent(appointment::setTime, appointmentRequest.getTime());
        NullUtils.updateIfPresent(appointment::setNote, appointmentRequest.getNote());
        return MappingData.mapObject(this.appointmentRepository.save(appointment), AppointmentResponse.class);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment=this.appointmentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(APPOINTMENT_NOT_FOUND));
        this.appointmentRepository.delete(appointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointment(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        return MappingData.mapListObject(this.appointmentRepository.findAll(pageable).getContent(),AppointmentResponse.class);
    }
}
