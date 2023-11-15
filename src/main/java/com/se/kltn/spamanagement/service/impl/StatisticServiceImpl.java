package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.enums.Category;
import com.se.kltn.spamanagement.constants.enums.TimeType;
import com.se.kltn.spamanagement.dto.projection.TopCustomerStatisticInterface;
import com.se.kltn.spamanagement.dto.request.RevenueStatisticRequest;
import com.se.kltn.spamanagement.dto.response.RevenueStatisticResponse;
import com.se.kltn.spamanagement.dto.response.TopCustomerStatisticResponse;
import com.se.kltn.spamanagement.model.Customer;
import com.se.kltn.spamanagement.model.Invoice;
import com.se.kltn.spamanagement.model.InvoiceDetail;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.repository.InvoiceDetailRepository;
import com.se.kltn.spamanagement.repository.InvoiceRepository;
import com.se.kltn.spamanagement.service.StatisticService;
import com.se.kltn.spamanagement.utils.DateByType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.se.kltn.spamanagement.constants.enums.Status.PAID;

@Service
@Log4j2
public class StatisticServiceImpl implements StatisticService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceDetailRepository invoiceDetailRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public StatisticServiceImpl(InvoiceRepository invoiceRepository, InvoiceDetailRepository invoiceDetailRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public RevenueStatisticResponse getRevenueStatistic(RevenueStatisticRequest statisticRequest) {
        log.info("Get revenue statistic");
        switch (statisticRequest.getTimeType()) {
            case "DAY":
                return getRevenueStatisticByDay();
            case "MONTH":
                return getRevenueStatisticByMonth();
            case "YEAR":
                return getRevenueStatisticByYear();
            case "WEEK":
                return getRevenueStatisticByWeek();
            default:
                return getRevenueStatisticByDate(statisticRequest.getStartDate(), statisticRequest.getEndDate());
        }
    }

    @Override
    public List<TopCustomerStatisticResponse> getTopCustomerSpendingStatistic(int numOfRow) {
//        List<Customer> customers = this.customerRepository.getCustomersByInvoiceIsPaid();
//        List<TopCustomerStatisticResponse> customerStatisticResponseList = new ArrayList<>();
//        for (Customer customer : customers) {
//            Double totalSpending = 0.0;
//            TopCustomerStatisticResponse topCustomerStatisticResponse = new TopCustomerStatisticResponse();
//            for (Invoice invoice : customer.getInvoices()) {
//                totalSpending += invoice.getTotalAmount();
//            }
//            topCustomerStatisticResponse.setIdCustomer(customer.getId());
//            topCustomerStatisticResponse.setCustomerClass(customer.getCustomerClass().name());
//            topCustomerStatisticResponse.setTotalSpending(BigDecimal.valueOf(totalSpending));
//            topCustomerStatisticResponse.setFirstName(customer.getFirstName());
//            topCustomerStatisticResponse.setLastName(customer.getLastName());
//            topCustomerStatisticResponse.setPhoneNumber(customer.getPhoneNumber());
//            customerStatisticResponseList.add(topCustomerStatisticResponse);
//        }
//        return customerStatisticResponseList.sort((topCustomerStatisticResponse, t1) -> );
        List<TopCustomerStatisticInterface> interfaceList = this.customerRepository.getCustomersByInvoiceIsPaid(numOfRow);
        List<TopCustomerStatisticResponse> customerStatisticResponseList = new ArrayList<>();
        for (TopCustomerStatisticInterface customerStatisticInterface : interfaceList) {
            TopCustomerStatisticResponse topCustomerStatisticResponse = getTopCustomerStatisticResponse(customerStatisticInterface);
            customerStatisticResponseList.add(topCustomerStatisticResponse);
        }
        return customerStatisticResponseList;
    }

    private static TopCustomerStatisticResponse getTopCustomerStatisticResponse(TopCustomerStatisticInterface customerStatisticInterface) {
        TopCustomerStatisticResponse topCustomerStatisticResponse = new TopCustomerStatisticResponse();
        topCustomerStatisticResponse.setIdCustomer(customerStatisticInterface.getIdCustomer());
        topCustomerStatisticResponse.setCustomerClass(customerStatisticInterface.getCustomerClass());
        topCustomerStatisticResponse.setTotalSpending(customerStatisticInterface.getTotalSpending());
        topCustomerStatisticResponse.setFirstName(customerStatisticInterface.getFirstName());
        topCustomerStatisticResponse.setLastName(customerStatisticInterface.getLastName());
        topCustomerStatisticResponse.setPhoneNumber(customerStatisticInterface.getPhoneNumber());
        return topCustomerStatisticResponse;
    }

    private RevenueStatisticResponse getRevenueStatisticByDate(Date startDate, Date endDate) {
        log.info("Get revenue statistic by date");
        Timestamp startDateTimestamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(startDate));
        Timestamp endDateTimestamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(endDate));
        List<Invoice> invoices = invoiceRepository.findInvoicesByDateBetweenAndStatus(startDateTimestamp, endDateTimestamp, PAID.name());
        return getRevenueStatisticResponse(invoices);
    }

    private RevenueStatisticResponse getRevenueStatisticByDay() {
        log.info("Get revenue statistic by day");
        List<Invoice> invoices = getInvoicesByDate(DateByType.startOfDay());
        RevenueStatisticResponse response = getRevenueStatisticResponse(invoices);
        response.setTimeType(TimeType.DAY);
        return response;
    }

    private RevenueStatisticResponse getRevenueStatisticByWeek() {
        log.info("Get revenue statistic by week");
        List<Invoice> invoices = getInvoicesByDate(DateByType.firstDayOfCurrentWeek());
        RevenueStatisticResponse response = getRevenueStatisticResponse(invoices);
        response.setTimeType(TimeType.WEEK);
        return response;
    }


    private RevenueStatisticResponse getRevenueStatisticByYear() {
        log.info("Get revenue statistic by year");
        List<Invoice> invoices = getInvoicesByDate(DateByType.firstDayOfCurrentYear());
        RevenueStatisticResponse response = getRevenueStatisticResponse(invoices);
        response.setTimeType(TimeType.YEAR);
        return response;
    }

    private RevenueStatisticResponse getRevenueStatisticByMonth() {
        log.info("Get revenue statistic by month");
        List<Invoice> invoices = getInvoicesByDate(DateByType.firstDayOfCurrentMonth());
        RevenueStatisticResponse response = getRevenueStatisticResponse(invoices);
        response.setTimeType(TimeType.MONTH);
        return response;
    }


    private RevenueStatisticResponse getRevenueStatisticResponse(List<Invoice> invoices) {
        log.info("Process get revenue statistic response");
        Double totalRevenue = getTotalRevenue(invoices);
        Integer totalQuantity = invoices.size();
        //TODO: get total quantity of each product type
        Map<String, Object> totalQuantityAndRevenue = getTotalRevenueAndQuantityByType(invoices);
        Integer totalService = (Integer) totalQuantityAndRevenue.get("totalService");
        Integer totalProduct = (Integer) totalQuantityAndRevenue.get("totalProduct");
        Integer totalTreatment = (Integer) totalQuantityAndRevenue.get("totalTreatment");
        //TODO: get total revenue of each product type
        Double totalServiceRevenue = (Double) totalQuantityAndRevenue.get("totalServiceRevenue");
        Double totalProductRevenue = (Double) totalQuantityAndRevenue.get("totalProductRevenue");
        Double totalTreatmentRevenue = (Double) totalQuantityAndRevenue.get("totalTreatmentRevenue");
        return RevenueStatisticResponse.builder()
                .revenue(totalRevenue)
                .totalQuantityInvoice(totalQuantity)
                .totalQuantityService(totalService)
                .totalQuantityProduct(totalProduct)
                .totalQuantityTreatment(totalTreatment)
                .totalServiceRevenue(totalServiceRevenue)
                .totalProductRevenue(totalProductRevenue)
                .totalTreatmentRevenue(totalTreatmentRevenue)
                .build();
    }

    private Map<String, Object> getTotalRevenueAndQuantityByType(List<Invoice> invoices) {
        log.info("Get total product type revenue");
        Double totalServiceRevenue = 0.0;
        Double totalProductRevenue = 0.0;
        Double totalTreatmentRevenue = 0.0;
        Integer totalService = 0;
        Integer totalProduct = 0;
        Integer totalTreatment = 0;
        for (Invoice invoice : invoices) {
            List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findInvoiceDetailsByInvoice_Id(invoice.getId());
            for (InvoiceDetail invoiceDetail : invoiceDetails) {
                if (invoiceDetail.getProduct().getCategory().equals(Category.SERVICE)) {
                    totalServiceRevenue += invoiceDetail.getTotalPrice();
                    totalService += invoiceDetail.getTotalQuantity();
                } else if (invoiceDetail.getProduct().getCategory().equals(Category.PRODUCT)) {
                    totalProductRevenue += invoiceDetail.getTotalPrice();
                    totalProduct += invoiceDetail.getTotalQuantity();
                } else if (invoiceDetail.getProduct().getCategory().equals(Category.TREATMENT)) {
                    totalTreatmentRevenue += invoiceDetail.getTotalPrice();
                    totalTreatment += invoiceDetail.getTotalQuantity();
                }
            }
        }
        Map<String, Object> totalRevenue = new HashMap<>();
        totalRevenue.put("totalServiceRevenue", totalServiceRevenue);
        totalRevenue.put("totalProductRevenue", totalProductRevenue);
        totalRevenue.put("totalTreatmentRevenue", totalTreatmentRevenue);
        totalRevenue.put("totalService", totalService);
        totalRevenue.put("totalProduct", totalProduct);
        totalRevenue.put("totalTreatment", totalTreatment);
        return totalRevenue;
    }

    private Double getTotalRevenue(List<Invoice> invoices) {
        log.info("Get total revenue");
        Double totalRevenue = 0.0;
        for (Invoice invoice : invoices) {
            totalRevenue += invoice.getTotalAmount();
        }
        return totalRevenue;
    }

    private List<Invoice> getInvoicesByDate(String firstOf) {
        log.info("Get invoices by date and status is PAID");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String currentDate = formatter.format(new Date());
        Timestamp firstOfTimestamp = Timestamp.valueOf(firstOf);
        Timestamp currentDateTimestamp = Timestamp.valueOf(currentDate);
        return invoiceRepository.findInvoicesByDateBetweenAndStatus(firstOfTimestamp, currentDateTimestamp, PAID.name());
    }
}
