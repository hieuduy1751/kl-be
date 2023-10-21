package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.AppointmentRequest;
import com.se.kltn.spamanagement.dto.response.AppointmentResponse;
import com.se.kltn.spamanagement.exception.BadRequestException;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Appointment;
import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.repository.AppointmentRepository;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.repository.EmployeeRepository;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.service.AppointmentService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final ProductRepository productRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, ProductRepository productRepository) {
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = MappingData.mapObject(appointmentRequest, Appointment.class);
        checkDateBefore(appointment.getTime());
        if (appointmentRequest.getStatus() == null) {
            appointment.setStatus(Status.WAITING);
        }
        appointment.setCreatedDate(new Date());
        appointment.setUpdatedDate(new Date());
        appointment.setEmployee(this.employeeRepository.findById(appointmentRequest.getIdEmployee()).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND)
        ));
        appointment.setCustomer(this.customerRepository.findById(appointmentRequest.getIdCustomer()).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        ));
        appointment.setProduct(this.productRepository.findById(appointmentRequest.getIdProduct()).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
        ));
        return MappingData.mapObject(this.appointmentRepository.save(appointment), AppointmentResponse.class);
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest appointmentRequest) {
        Appointment appointment = this.appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_NOT_FOUND));
        appointment.setUpdatedDate(new Date());
        checkDateBefore(appointmentRequest.getTime());
        NullUtils.updateIfPresent(appointment::setTime, appointmentRequest.getTime());
        NullUtils.updateIfPresent(appointment::setStatus, appointmentRequest.getStatus());
        NullUtils.updateIfPresent(appointment::setNote, appointmentRequest.getNote());
        return MappingData.mapObject(this.appointmentRepository.save(appointment), AppointmentResponse.class);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = this.appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_NOT_FOUND));
        this.appointmentRepository.delete(appointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointment(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return MappingData.mapListObject(this.appointmentRepository.findAll(pageable).getContent(), AppointmentResponse.class);
    }

    @Override
    public List<AppointmentResponse> getAllAppointmentByCustomer(Long idCustomer, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return MappingData.mapListObject(this.appointmentRepository.findAppointmentsByCustomer_Id(idCustomer, pageable), AppointmentResponse.class);
    }

    private void checkDateBefore(Date date) {
        if (date != null && date.before(new Date())) {
            throw new BadRequestException("Date request is before now");
        }
    }
}
