package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.RevenueStatisticRequest;
import com.se.kltn.spamanagement.dto.response.RevenueStatisticResponse;
import com.se.kltn.spamanagement.dto.response.TopCustomerStatisticResponse;
import com.se.kltn.spamanagement.dto.response.TopMostPopularProductResponse;

import java.util.List;

public interface StatisticService {

    RevenueStatisticResponse getRevenueStatistic(RevenueStatisticRequest statisticRequest);

    List<TopCustomerStatisticResponse> getTopCustomerSpendingStatistic(int numOfRow);

    List<TopMostPopularProductResponse> getTopMostPopularProduct(int numOfRow);

}
