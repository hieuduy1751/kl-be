package com.se.kltn.spamanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private String firstName;

    private String lastName;

    private String gender;

    private String avatarUrl;

    private String position;

    private String address;

    private String phone;

    private String email;

    private String birthDay;

    private Double salaryGross;

    private Date createdDate;

    private Date updatedDate;
}
