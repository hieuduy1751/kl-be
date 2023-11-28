package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.enums.Gender;
import com.se.kltn.spamanagement.constants.enums.Position;
import com.se.kltn.spamanagement.dto.request.EmployeeRequest;
import com.se.kltn.spamanagement.dto.response.EmployeeResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Account;
import com.se.kltn.spamanagement.model.Employee;
import com.se.kltn.spamanagement.repository.AccountRepository;
import com.se.kltn.spamanagement.repository.EmployeeRepository;
import com.se.kltn.spamanagement.service.EmployeeService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.EMPLOYEE_NOT_FOUND;

@Service
@Transactional
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AccountRepository accountRepository) {
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        log.info("get employee by id");
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
        EmployeeResponse employeeResponse = MappingData.mapObject(employee, EmployeeResponse.class);
        checkEmployeeAccount(employeeResponse, employee);
        return employeeResponse;
    }


    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        log.info("create employee");
        Employee employee = MappingData.mapObject(employeeRequest, Employee.class);
        employee.setCreatedDate(new Date());
        employee.setUpdatedDate(new Date());
        Employee employeeCreated = this.employeeRepository.save(employee);
        EmployeeResponse employeeResponse = MappingData.mapObject(employeeCreated, EmployeeResponse.class);
        checkEmployeeAccount(employeeResponse, employeeCreated);
        return employeeResponse;
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        log.info("update employee");
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
        NullUtils.updateIfPresent(employee::setFirstName, employeeRequest.getFirstName());
        NullUtils.updateIfPresent(employee::setLastName, employeeRequest.getLastName());
        NullUtils.updateIfPresent(employee::setGender, employeeRequest.getGender());
        NullUtils.updateIfPresent(employee::setAvatarUrl, employeeRequest.getAvatarUrl());
        NullUtils.updateIfPresent(employee::setAddress, employeeRequest.getAddress());
        NullUtils.updateIfPresent(employee::setPosition, employeeRequest.getPosition());
        NullUtils.updateIfPresent(employee::setEmail, employeeRequest.getEmail());
        NullUtils.updateIfPresent(employee::setBirthDay, employeeRequest.getBirthDay());
        NullUtils.updateIfPresent(employee::setSalaryGross, employeeRequest.getSalaryGross());
        employee.setUpdatedDate(new Date());
        EmployeeResponse employeeResponse = MappingData.mapObject(this.employeeRepository.save(employee), EmployeeResponse.class);
        checkEmployeeAccount(employeeResponse, employee);
        return employeeResponse;
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("delete employee");
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
        if (employee.getAccount() != null) {
            this.accountRepository.delete(employee.getAccount());
        }
        this.employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployeePaging(int page, int size) {
        log.info("get all employee paging");
        Pageable pageable = PageRequest.of(page, size);
        List<Employee> employeePage = this.employeeRepository.findAll(pageable).getContent();
        return mappingEmployeeResponse(employeePage);
    }


    @Override
    public List<EmployeeResponse> searchEmployeeByText(String text) {
        if (text == null) {
            return this.getAllEmployeePaging(0, 10);
        }
        return mappingEmployeeResponse(this.employeeRepository.getEmployeesByText(text));
    }

    private void checkEmployeeAccount(EmployeeResponse employeeResponse, Employee employee) {
        if (employee.getAccount() == null) {
            employeeResponse.setUsername(null);
        } else {
            employeeResponse.setUsername(employee.getAccount().getUsername());
        }
    }

    private List<EmployeeResponse> mappingEmployeeResponse(List<Employee> employeeList) {
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for (Employee employee : employeeList) {
            Account account = this.accountRepository.findAccountByEmployee_Id(employee.getId()).orElse(null);
            EmployeeResponse employeeResponse = MappingData.mapObject(employee, EmployeeResponse.class);
            if (account == null) {
                employeeResponse.setUsername(null);
            } else {
                employeeResponse.setUsername(account.getUsername());
            }
            employeeResponses.add(employeeResponse);
        }
        return employeeResponses;
    }
}
