package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.AccountRequest;
import com.se.kltn.spamanagement.dto.response.AccountResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AuthService {
    Map<String,String> login(String username, String password);

    AccountResponse registerCustomer(AccountRequest accountRequest);

    AccountResponse registerEmployee(AccountRequest accountRequest, Long idEmployee);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
