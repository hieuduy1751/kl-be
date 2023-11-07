package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.CustomerRequest;
import com.se.kltn.spamanagement.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "get customer by id")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.customerService.getCustomerById(id));
    }

    @GetMapping
    @Operation(summary = "get list customer")
    public ResponseEntity<Object> getCustomers(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                               @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.customerService.getAllCustomer(page, size));
    }

    @PostMapping
    @Operation(summary = "create customer")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok().body(this.customerService.createCustomer(customerRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update customer")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok().body(this.customerService.updateCustomer(id, customerRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete customer")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        this.customerService.deleteCustomer(id);
        return ResponseEntity.ok().body("Customer deleted");
    }

    @GetMapping("search")
    @Operation(summary = "search customer by text")
    public ResponseEntity<Object> findCustomerByName(@RequestParam(required = false) String text) {
        return ResponseEntity.ok().body(this.customerService.getCustomerByText(text));
    }
}