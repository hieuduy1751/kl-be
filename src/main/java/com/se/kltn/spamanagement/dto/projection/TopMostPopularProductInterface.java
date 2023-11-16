package com.se.kltn.spamanagement.dto.projection;

public interface TopMostPopularProductInterface {
    Long getId();

    String getName();

    Double getPrice();

    String getCategory();

    String getDescription();

    Integer getNumOfAppointment();
}
