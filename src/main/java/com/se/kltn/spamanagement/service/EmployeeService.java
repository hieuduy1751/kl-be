package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.EmployeeRequest;
import com.se.kltn.spamanagement.dto.response.EmployeeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest);

    void deleteEmployee(Long id);

    List<EmployeeResponse> getAllEmployeePaging(int page, int size);
}
