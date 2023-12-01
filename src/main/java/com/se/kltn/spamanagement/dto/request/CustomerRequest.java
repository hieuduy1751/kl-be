package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.constants.enums.CustomerClass;
import com.se.kltn.spamanagement.constants.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotNull(message = "gender is required")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String avatarUrl;

    private String address;

    private String phoneNumber;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthDay;

    @Enumerated(EnumType.STRING)
    private CustomerClass customerClass;
}
