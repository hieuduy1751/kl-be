package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.RevenueStatisticRequest;
import com.se.kltn.spamanagement.dto.response.RevenueStatisticResponse;

public interface StatisticService {

    RevenueStatisticResponse getRevenueStatistic(RevenueStatisticRequest statisticRequest);

}
