package com.se.kltn.spamanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private String email;

    private Date birthDay;

    private Double salaryGross;
}
