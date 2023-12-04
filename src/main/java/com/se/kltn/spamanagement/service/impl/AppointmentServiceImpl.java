package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.AppointmentRequest;
import com.se.kltn.spamanagement.dto.response.AppointmentResponse;
import com.se.kltn.spamanagement.exception.BadRequestException;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Appointment;
import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.model.Employee;
import com.se.kltn.spamanagement.repository.AppointmentRepository;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.repository.EmployeeRepository;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.service.AppointmentService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
@Log4j2
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
        log.debug("create appointment");
        Appointment appointment = MappingData.mapObject(appointmentRequest, Appointment.class);
        appointment.setTime(convertDate(appointmentRequest.getTime()));
        checkDateBefore(appointment.getTime());
        if (appointmentRequest.getStatus() == null) {
            appointment.setStatus(Status.WAITING);
        }
        appointment.setCreatedDate(new Date());
        appointment.setUpdatedDate(new Date());
        //
        checkEmployeeIsBusy(appointmentRequest.getTime(),appointment);
        appointment.setCustomer(this.customerRepository.findById(appointmentRequest.getIdCustomer()).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        ));
        appointment.setProduct(this.productRepository.findById(appointmentRequest.getIdProduct()).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
        ));
        Appointment appointmentSaved = this.appointmentRepository.save(appointment);
        AppointmentResponse appointmentResponse = MappingData.mapObject(appointmentSaved, AppointmentResponse.class);
        appointmentResponse.setReference(getReference(appointmentSaved));
        return appointmentResponse;
    }

    private void checkEmployeeIsBusy(Date time, Appointment appointment) {
        time.setHours(time.getHours() -1);
        System.err.println(time);
        LocalDateTime startDate = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        time.setHours(time.getHours() +2);
        System.err.println(time);
        LocalDateTime endDate = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<Employee> employees = this.employeeRepository.findEmployeesNotInAppointmentTime(startDate, endDate);
        if (employees.isEmpty()) {
            throw new BadRequestException("All employees are busy");
        }
        appointment.setEmployee(employees.get(0));
    }


    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest appointmentRequest) {
        Appointment appointment = this.appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_NOT_FOUND));
        appointment.setUpdatedDate(new Date());
        checkDateBefore(appointmentRequest.getTime());
        if (appointmentRequest.getTime() != null) {
            appointment.setTime(convertDate(appointmentRequest.getTime()));
        }
        NullUtils.updateIfPresent(appointment::setStatus, appointmentRequest.getStatus());
        NullUtils.updateIfPresent(appointment::setNote, appointmentRequest.getNote());
        NullUtils.updateIfPresent(appointment::setTime, appointmentRequest.getTime());
        Appointment appointmentUpdated = this.appointmentRepository.save(appointment);
        AppointmentResponse appointmentResponse = MappingData.mapObject(appointmentUpdated, AppointmentResponse.class);
        appointmentResponse.setReference(getReference(appointmentUpdated));
        return appointmentResponse;
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
        List<AppointmentResponse> appointmentResponses = MappingData.mapListObject(this.appointmentRepository.findAll(pageable).getContent(), AppointmentResponse.class);
        return mapReference(appointmentResponses);
    }

    @Override
    public List<AppointmentResponse> getAllAppointmentByCustomer(Long idCustomer, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<AppointmentResponse> appointmentResponses = MappingData.mapListObject(this.appointmentRepository.findAppointmentsByCustomer_Id(idCustomer, pageable), AppointmentResponse.class);
        return mapReference(appointmentResponses);
    }

    private List<AppointmentResponse> mapReference(List<AppointmentResponse> appointmentResponses) {
        for (AppointmentResponse appointmentResponse : appointmentResponses) {
            appointmentResponse.setReference(getReference(this.appointmentRepository.findById(appointmentResponse.getId()).orElseThrow(
                    () -> new ResourceNotFoundException(APPOINTMENT_NOT_FOUND))));
        }
        return appointmentResponses;
    }

    private void checkDateBefore(Date date) {
        if (date != null && date.before(new Date())) {
            throw new BadRequestException("Date request is before now");
        }
    }

    private Date convertDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strDate = formatter.format(date);
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Map<String, String> getReference(Appointment appointmentSaved) {
        Map<String, String> map = new HashMap<>();
        map.put("customerId", String.valueOf(appointmentSaved.getCustomer().getId()));
        map.put("employeeId", String.valueOf(appointmentSaved.getEmployee().getId()));
        map.put("productId", String.valueOf(appointmentSaved.getProduct().getId()));
        map.put("customerName", appointmentSaved.getCustomer().getFirstName() + " " + appointmentSaved.getCustomer().getLastName());
        map.put("employeeName", appointmentSaved.getEmployee().getFirstName() + " " + appointmentSaved.getEmployee().getLastName());
        map.put("productName", appointmentSaved.getProduct().getName());
        return map;
    }
}
