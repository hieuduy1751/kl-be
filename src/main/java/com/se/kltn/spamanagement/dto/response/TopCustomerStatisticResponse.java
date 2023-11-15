package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.constants.enums.CustomerClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopCustomerStatisticResponse {

    private Long idCustomer;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String customerClass;

    private BigDecimal totalSpending;

}
