package com.se.kltn.spamanagement.dto.projection;

import java.math.BigDecimal;

public interface TopCustomerStatisticInterface {

    Long getIdCustomer();

    String getFirstName();

    String getLastName();

    String getPhoneNumber();

    String getCustomerClass();

    BigDecimal getTotalSpending();
}
