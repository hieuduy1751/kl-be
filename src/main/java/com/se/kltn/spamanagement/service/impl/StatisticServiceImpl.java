package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.enums.Category;
import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.dto.request.RevenueStatisticRequest;
import com.se.kltn.spamanagement.dto.response.RevenueStatisticResponse;
import com.se.kltn.spamanagement.model.Invoice;
import com.se.kltn.spamanagement.model.InvoiceDetail;
import com.se.kltn.spamanagement.repository.InvoiceDetailRepository;
import com.se.kltn.spamanagement.repository.InvoiceRepository;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.service.StatisticService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.enums.Status.PAID;
import static com.se.kltn.spamanagement.constants.enums.TimeType.*;
import static com.se.kltn.spamanagement.utils.DateByType.firstDayOfWeek;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final InvoiceRepository invoiceRepository;

    private final ProductRepository productRepository;

    private final InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    public StatisticServiceImpl(InvoiceRepository invoiceRepository, ProductRepository productRepository, InvoiceDetailRepository invoiceDetailRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
    }

    @Override
    public RevenueStatisticResponse getRevenueStatistic(RevenueStatisticRequest statisticRequest) {
        switch (statisticRequest.getTimeType()) {
            case DAY:
//                return getRevenueStatisticByDay();
//            case MONTH:
//                return getRevenueStatisticByMonth();
//            case YEAR:
//                return getRevenueStatisticByYear();
            case WEEK:
                return getRevenueStatisticByWeek();
            default:
//                return getRevenueStatisticByDate(statisticRequest);
                return null;
        }
    }

//    private RevenueStatisticResponse getRevenueStatisticByDate(RevenueStatisticRequest statisticRequest) {
//    }

    private RevenueStatisticResponse getRevenueStatisticByWeek() {
        List<Invoice> invoices = invoiceRepository.findInvoicesByUpdatedDateBetweenAndStatus(
                firstDayOfWeek(), new Date(), PAID);
        Double totalRevenue = getTotalRevenue(invoices);
        Integer totalQuantity = invoices.size();
        Integer totalService = getTotalService(invoices);
        return null;
    }

    private Integer getTotalService(List<Invoice> invoices) {
        Integer totalService = 0;
        for (Invoice invoice : invoices) {
            List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findInvoiceDetailsByInvoice_Id(invoice.getId());
            for (InvoiceDetail invoiceDetail : invoiceDetails) {
                if (invoiceDetail.getProduct().getCategory().equals(Category.SERVICE)) {
                    totalService++;
                }
            }
        }
        return totalService;
    }


//    private RevenueStatisticResponse getRevenueStatisticByYear(RevenueStatisticRequest statisticRequest) {
//    }
//
//    private RevenueStatisticResponse getRevenueStatisticByMonth(RevenueStatisticRequest statisticRequest) {
//    }
//
//    private RevenueStatisticResponse getRevenueStatisticByDay(RevenueStatisticRequest statisticRequest) {
//    }

    private Double getTotalRevenue(List<Invoice> invoices) {
        Double totalRevenue = 0.0;
        for (Invoice invoice : invoices) {
            totalRevenue += invoice.getTotalAmount();
        }
        return totalRevenue;
    }

}
