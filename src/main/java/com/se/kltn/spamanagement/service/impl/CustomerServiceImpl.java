package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.ErrorMessage;
import com.se.kltn.spamanagement.dto.request.CustomerRequest;
import com.se.kltn.spamanagement.dto.response.CustomerResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Customer;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.service.CustomerService;
import com.se.kltn.spamanagement.utils.MappingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.CUSTOMER_NOT_FOUND;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
        return MappingData.mapObject(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerResponse) {
        Customer customer = MappingData.mapObject(customerResponse, Customer.class);
        customer.setCreatedDate(new Date());
        customer.setUpdatedDate(new Date());
        return MappingData.mapObject(this.customerRepository.save(customer), CustomerResponse.class);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerResponse) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
        customer.setFirstName(customerResponse.getFirstName());
        customer.setLastName(customerResponse.getLastName());
        customer.setAddress(customerResponse.getAddress());
        customer.setPhoneNumber(customerResponse.getPhoneNumber());
        customer.setEmail(customerResponse.getEmail());
        customer.setBirthDay(customerResponse.getBirthDay());
        customer.setUpdatedDate(new Date());
        return MappingData.mapObject(this.customerRepository.save(customer), CustomerResponse.class);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
        this.customerRepository.delete(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Customer> customers = this.customerRepository.findAll(pageable).getContent();
        return MappingData.mapListObject(customers, CustomerResponse.class);
    }
}
