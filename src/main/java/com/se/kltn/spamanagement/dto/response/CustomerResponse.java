package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.constants.enums.CustomerClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

        private Long id;

        private String firstName;

        private String lastName;

        private String gender;

        private String avatarUrl;

        private String address;

        private String phoneNumber;

        private String email;

        private String birthDay;

        private CustomerClass customerClass;
}
