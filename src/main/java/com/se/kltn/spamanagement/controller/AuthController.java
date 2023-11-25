package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.AccountRequest;
import com.se.kltn.spamanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "login user")
    public ResponseEntity<Object> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok().body(this.authService.login(username, password));
    }

    @PostMapping("/register-customer")
    @Operation(summary = "register new account for customer")
    public ResponseEntity<Object> registerCustomer(@Valid @RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok().body(this.authService.registerCustomer(accountRequest));
    }

    @PostMapping("/register-employee/{idEmployee}")
    @Operation(summary = "register new account for employee")
    public ResponseEntity<Object> registerCustomer(@Valid @RequestBody AccountRequest accountRequest, @PathVariable Long idEmployee) {
        return ResponseEntity.ok().body(this.authService.registerEmployee(accountRequest, idEmployee));
    }

    @PostMapping("/logout")
    @Operation(summary = "logout account")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        this.authService.logout(request, response);
    }
}
