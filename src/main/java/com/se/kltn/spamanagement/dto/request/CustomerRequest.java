package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.constants.enums.CustomerClass;
import com.se.kltn.spamanagement.constants.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    @NotBlank(message = "gender is required")
    private String gender;

    private String avatarUrl;

    private String address;

    private String phoneNumber;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthDay;

    private CustomerClass customerClass;
}
