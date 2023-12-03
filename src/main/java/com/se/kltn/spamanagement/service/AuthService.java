package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.AccountRequest;
import com.se.kltn.spamanagement.dto.response.AccountResponse;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AuthService {
    Map<String,String> login(String username, String password);

    void registerCustomer(AccountRequest accountRequest, String email, String siteUrl) throws MessagingException;

    AccountResponse registerEmployee(AccountRequest accountRequest, Long idEmployee);

    void logout(HttpServletRequest request, HttpServletResponse response);

    boolean verify(String verificationCode);
}
