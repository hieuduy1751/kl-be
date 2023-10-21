package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.constants.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String username;
    private Role role;
}
