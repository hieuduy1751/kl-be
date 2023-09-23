package com.se.kltn.spamanagement.controller.admin;

import com.se.kltn.spamanagement.dto.request.EmployeeRequest;
import com.se.kltn.spamanagement.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "get employee by id")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.employeeService.getEmployeeById(id));
    }

    @GetMapping
    @Operation(summary = "get list employee")
    public ResponseEntity<Object> getEmployees(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                               @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.employeeService.getAllEmployeePaging(page, size));
    }

    @PostMapping
    @Operation(summary = "create employee")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok().body(this.employeeService.createEmployee(employeeRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update employee")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok().body(this.employeeService.updateEmployee(id, employeeRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete employee")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body("Employee deleted");
    }
}
