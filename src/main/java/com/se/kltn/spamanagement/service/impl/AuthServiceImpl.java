package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.AccountRequest;
import com.se.kltn.spamanagement.dto.response.AccountResponse;
import com.se.kltn.spamanagement.exception.BadRequestException;
import com.se.kltn.spamanagement.model.Account;
import com.se.kltn.spamanagement.constants.enums.Role;
import com.se.kltn.spamanagement.repository.AccountRepository;
import com.se.kltn.spamanagement.security.jwt.JwtProvider;
import com.se.kltn.spamanagement.service.AuthService;
import com.se.kltn.spamanagement.utils.MappingData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Map<String, String> login(String username, String password) {
        log.debug("login account: " + username);
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            String token = jwtProvider.createToken(username, role);
            Map<String, String> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("incorrect username or password");
        }
    }

    @Override
    public AccountResponse register(AccountRequest accountRequest) {
        log.debug("register new account");
        if (!accountRequest.getPasswordConfirm().equals(accountRequest.getPassword())) {
            throw new BadRequestException("password confirm is not same");
        } else if (accountRepository.findAccountByUsername(accountRequest.getUsername()).isPresent()) {
            throw new BadRequestException("username already exist");
        }
        Account account = MappingData.mapObject(accountRequest, Account.class);
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        account.setRole(Role.USER);
        Account accountRegister = this.accountRepository.save(account);
        return MappingData.mapObject(accountRegister, AccountResponse.class);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        log.debug("logout account");
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }
}
