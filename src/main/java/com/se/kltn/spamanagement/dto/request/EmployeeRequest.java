package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.constants.enums.Gender;
import com.se.kltn.spamanagement.constants.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "gender is required")
    private Gender gender;

    private String avatarUrl;

    private Position position;

    private String address;

    private String phoneNumber;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthDay;

    private Double salaryGross;
}
