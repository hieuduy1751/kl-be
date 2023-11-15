package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.constants.enums.TimeType;
import com.se.kltn.spamanagement.dto.request.RevenueStatisticRequest;
import com.se.kltn.spamanagement.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/revenue")
    @Operation(summary = "get revenue statistic")
    public ResponseEntity<Object> getRevenueStatisticByDate(@RequestParam(required = false) String timeType,
                                                            @RequestParam(required = false) Date startDate,
                                                            @RequestParam(required = false) Date endDate) {
        return ResponseEntity.ok().body(this.statisticService.getRevenueStatistic(new RevenueStatisticRequest(startDate, endDate, timeType)));
    }

    @GetMapping("/topCustomer")
    @Operation(summary = "get top customer spending in spa statistic")
    public ResponseEntity<Object> getTopCustomerSpending(@RequestParam(defaultValue = "50") int numOfRow) {
        return ResponseEntity.ok().body(this.statisticService.getTopCustomerSpendingStatistic(numOfRow));
    }
}
