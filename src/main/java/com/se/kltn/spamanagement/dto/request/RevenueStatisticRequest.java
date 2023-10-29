package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.constants.enums.TimeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueStatisticRequest {

    private Date startDate;

    private Date endDate;

    private String timeType;
}
