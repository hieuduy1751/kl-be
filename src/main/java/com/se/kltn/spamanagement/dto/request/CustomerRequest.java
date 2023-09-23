package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.model.enums.CustomerClass;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    private String address;

    private String phoneNumber;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    private Date birthDay;

    private CustomerClass customerClass;
}
