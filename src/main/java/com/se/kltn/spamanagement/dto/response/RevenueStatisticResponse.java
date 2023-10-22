package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.constants.enums.TimeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevenueStatisticResponse {

    private Double revenue;

    private TimeType timeType;

    private Integer totalQuantityInvoice;

    private Integer totalQuantityService;

    private Integer totalQuantityProduct;

    private Integer totalQuantityTreatment;

    private Double totalServiceRevenue;

    private Double totalProductRevenue;

    private Double totalTreatmentRevenue;

}
