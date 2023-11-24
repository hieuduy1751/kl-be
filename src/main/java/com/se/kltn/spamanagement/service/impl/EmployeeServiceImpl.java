package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.ErrorMessage;
import com.se.kltn.spamanagement.constants.enums.Gender;
import com.se.kltn.spamanagement.dto.request.EmployeeRequest;
import com.se.kltn.spamanagement.dto.response.EmployeeResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Employee;
import com.se.kltn.spamanagement.repository.EmployeeRepository;
import com.se.kltn.spamanagement.service.EmployeeService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.EMPLOYEE_NOT_FOUND;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        log.info("get employee by id");
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
        return MappingData.mapObject(employee, EmployeeResponse.class);
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        log.info("create employee");
        Employee employee = MappingData.mapObject(employeeRequest, Employee.class);
        employee.setCreatedDate(new Date());
        employee.setUpdatedDate(new Date());
        Employee employeeCreated = this.employeeRepository.save(employee);
        return MappingData.mapObject(employeeCreated, EmployeeResponse.class);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        log.info("update employee");
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
        NullUtils.updateIfPresent(employee::setFirstName, employeeRequest.getFirstName());
        NullUtils.updateIfPresent(employee::setLastName, employeeRequest.getLastName());
        NullUtils.updateIfPresent(employee::setGender, Gender.valueOf(employeeRequest.getGender()));
        NullUtils.updateIfPresent(employee::setAvatarUrl, employeeRequest.getAvatarUrl());
        NullUtils.updateIfPresent(employee::setAddress, employeeRequest.getAddress());
        NullUtils.updateIfPresent(employee::setEmail, employeeRequest.getEmail());
        NullUtils.updateIfPresent(employee::setBirthDay, employeeRequest.getBirthDay());
        NullUtils.updateIfPresent(employee::setSalaryGross, employeeRequest.getSalaryGross());
        employee.setUpdatedDate(new Date());
        return MappingData.mapObject(this.employeeRepository.save(employee), EmployeeResponse.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("delete employee");
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
        this.employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployeePaging(int page, int size) {
        log.info("get all employee paging");
        Pageable pageable = PageRequest.of(page, size);
        List<Employee> employeePage = this.employeeRepository.findAll(pageable).getContent();
        return MappingData.mapListObject(employeePage, EmployeeResponse.class);
    }

    @Override
    public List<EmployeeResponse> searchEmployeeByText(String text) {
        if (text == null) {
            return this.getAllEmployeePaging(0, 10);
        }
        return MappingData.mapListObject(this.employeeRepository.getEmployeesByText(text), EmployeeResponse.class);
    }
}
