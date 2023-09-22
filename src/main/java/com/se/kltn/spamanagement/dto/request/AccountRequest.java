package com.se.kltn.spamanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AccountRequest {
    @NotBlank(message = "username is required")
    @Size(min = 4, max = 50, message = "username size character is between 4 and 50")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 6, max = 16, message = "password size character is between 6 and 16")
    private String password;

    @NotBlank(message = "password confirm is require and must be same with password")
    private String passwordConfirm;
}
