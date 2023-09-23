package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.CustomerRequest;
import com.se.kltn.spamanagement.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse getCustomerById(Long id);

    CustomerResponse createCustomer(CustomerRequest customerResponse);

    CustomerResponse updateCustomer(Long id, CustomerRequest customerResponse);

    void deleteCustomer(Long id);

    List<CustomerResponse> getAllCustomer(int page, int size);
}
