package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.CustomerRequest;
import com.se.kltn.spamanagement.dto.response.CustomerResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Customer;
import com.se.kltn.spamanagement.repository.AccountRepository;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.service.CustomerService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.CUSTOMER_NOT_FOUND;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        log.debug("Get customer by id: " + id);
        Customer customer = this.customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
        return MappingData.mapObject(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerResponse) {
        log.debug("Create customer");
        Customer customer = MappingData.mapObject(customerResponse, Customer.class);
        customer.setCreatedDate(new Date());
        customer.setUpdatedDate(new Date());
        return MappingData.mapObject(this.customerRepository.save(customer), CustomerResponse.class);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        log.debug("Update customer has id: " + id);
        Customer customer = this.customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
        NullUtils.updateIfPresent(customer::setFirstName, customerRequest.getFirstName());
        NullUtils.updateIfPresent(customer::setLastName, customerRequest.getLastName());
        NullUtils.updateIfPresent(customer::setGender, customerRequest.getGender());
        NullUtils.updateIfPresent(customer::setAvatarUrl, customerRequest.getAvatarUrl());
        NullUtils.updateIfPresent(customer::setAddress, customerRequest.getAddress());
        NullUtils.updateIfPresent(customer::setEmail, customerRequest.getEmail());
        NullUtils.updateIfPresent(customer::setBirthDay, customerRequest.getBirthDay());
        NullUtils.updateIfPresent(customer::setCustomerClass, customerRequest.getCustomerClass());
        customer.setUpdatedDate(new Date());
        return MappingData.mapObject(this.customerRepository.save(customer), CustomerResponse.class);
    }

    @Override
    public void deleteCustomer(Long id) {
        log.debug("Delete customer has id: " + id);
        Customer customer = this.customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
        if (customer.getAccount() != null) {
            this.accountRepository.delete(customer.getAccount());
        }
        this.customerRepository.delete(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomer(int page, int size) {
        log.debug("Get all customer");
        Pageable pageable = PageRequest.of(page, size);
        List<Customer> customers = this.customerRepository.findAll(pageable).getContent();
        return MappingData.mapListObject(customers, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> getCustomerByText(String text) {
        log.debug("find customer by text: " + text);
        if (text == null) {
            return getAllCustomer(0, 10);
        }
        return MappingData.mapListObject(this.customerRepository.getCustomersByText(text), CustomerResponse.class);
    }

    @Override
    public CustomerResponse getCustomerByUsername(String username) {
        log.debug("find customer by username: " + username);
        Customer customer = this.customerRepository.findCustomerByAccount_Username(username).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
        return MappingData.mapObject(customer, CustomerResponse.class);
    }
}
