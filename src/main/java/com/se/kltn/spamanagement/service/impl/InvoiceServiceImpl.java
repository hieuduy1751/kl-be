package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.InvoiceCreateRequest;
import com.se.kltn.spamanagement.dto.request.InvoiceDetailRequest;
import com.se.kltn.spamanagement.dto.request.InvoiceUpdateRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceDetailResponse;
import com.se.kltn.spamanagement.dto.response.InvoiceResponse;
import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Invoice;
import com.se.kltn.spamanagement.model.InvoiceDetail;
import com.se.kltn.spamanagement.model.enums.Status;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.repository.EmployeeRepository;
import com.se.kltn.spamanagement.repository.InvoiceRepository;
import com.se.kltn.spamanagement.service.InvoiceService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final InvoiceDetailServiceImpl invoiceDetailService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, InvoiceDetailServiceImpl invoiceDetailService) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.invoiceDetailService = invoiceDetailService;
    }

    @Override
    public InvoiceResponse createInvoice(InvoiceCreateRequest invoiceRequest) {
        Invoice invoice = MappingData.mapObject(invoiceRequest, Invoice.class);
        invoice.setCustomer(this.customerRepository.findById(invoiceRequest.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        ));
        invoice.setEmployee(this.employeeRepository.findById(invoiceRequest.getEmployeeId()).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND)
        ));
        invoice.setStatus(Status.UNPAID);
        invoice.setCreatedDate(new Date());
        invoice.setUpdatedDate(new Date());
        return saveInvoiceDetail(this.invoiceRepository.save(invoice), invoiceRequest);
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceUpdateRequest invoiceRequest) {
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_NOT_FOUND));
        NullUtils.updateIfPresent(invoice::setNote, invoiceRequest.getNote());
        NullUtils.updateIfPresent(invoice::setDueDate, invoiceRequest.getDueDate());
        NullUtils.updateIfPresent(invoice::setPaymentMethod, invoiceRequest.getPaymentMethod());
        invoice.setUpdatedDate(new Date());
        Double totalAmount = 0.0;
        for (InvoiceDetail invoiceDetail : invoice.getInvoiceDetails()) {
            totalAmount += invoiceDetail.getTotalPrice();
        }
        invoice.setTotalAmount(totalAmount);
        return MappingData.mapObject(this.invoiceRepository.save(invoice), InvoiceResponse.class);
    }

    @Override
    public List<InvoiceResponse> getInvoicesByCustomer(Long idCustomer) {
        List<Invoice> invoices = this.invoiceRepository.findInvoicesByCustomer_Id(idCustomer);
        return mapToResponse(invoices);
    }


    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_NOT_FOUND));
        for (InvoiceDetail invoiceDetail : invoice.getInvoiceDetails()) {
            this.invoiceDetailService.deleteInvoiceDetail(invoiceDetail.getId());
        }
        this.invoiceRepository.delete(invoice);
    }

    @Override
    public List<InvoiceResponse> getAllInvoice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mapToResponse(this.invoiceRepository.findAll(pageable).getContent());
    }

    private InvoiceResponse saveInvoiceDetail(Invoice invoiceSaved, InvoiceCreateRequest invoiceRequest) {
        List<InvoiceDetailRequest> invoiceDetailRequests = invoiceRequest.getInvoiceDetailRequests();
        List<InvoiceDetailResponse> detailResponseList = new ArrayList<>();
        Double totalAmount = 0.0;
        for (InvoiceDetailRequest invoiceDetailRequest : invoiceDetailRequests) {
            InvoiceDetail invoiceDetail = MappingData.mapObject(invoiceDetailRequest, InvoiceDetail.class);
            invoiceDetail.setInvoice(invoiceSaved);
            InvoiceDetailResponse invoiceDetailResponse = invoiceDetailService.createInvoiceDetail(invoiceDetail, invoiceDetailRequest.getIdProduct());
            detailResponseList.add(invoiceDetailResponse);
            totalAmount += invoiceDetailResponse.getTotalPrice();
        }
        invoiceSaved.setTotalAmount(totalAmount);
        this.invoiceRepository.save(invoiceSaved);
        InvoiceResponse invoiceResponse = MappingData.mapObject(invoiceSaved, InvoiceResponse.class);
        invoiceResponse.setInvoiceDetailResponses(detailResponseList);
        return invoiceResponse;
    }

    private List<InvoiceResponse> mapToResponse(List<Invoice> invoices) {
        List<InvoiceResponse> invoiceResponses = MappingData.mapListObject(invoices, InvoiceResponse.class);
        invoiceResponses.forEach(invoiceResponse -> {
            invoices.forEach(invoice -> {
                if (invoiceResponse.getId().equals(invoice.getId())) {
                    List<InvoiceDetailResponse> invoiceDetailResponses = MappingData.mapListObject(invoice.getInvoiceDetails(), InvoiceDetailResponse.class);
                    invoiceDetailResponses.forEach(invoiceDetailResponse -> {
                        invoice.getInvoiceDetails().forEach(
                                invoiceDetail -> {
                                    invoiceDetailResponse.setProductResponse(MappingData.mapObject(invoiceDetail.getProduct(), ProductResponse.class));
                                }
                        );
                    });
                    invoiceResponse.setInvoiceDetailResponses(invoiceDetailResponses);
                }
            });
        });
        return invoiceResponses;
    }
}
