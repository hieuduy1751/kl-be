package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.InvoiceRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Invoice;
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

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = MappingData.mapObject(invoiceRequest, Invoice.class);
        invoice.setCustomer(this.customerRepository.findById(invoiceRequest.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        ));
        invoice.setEmployee(this.employeeRepository.findById(invoiceRequest.getEmployeeId()).orElseThrow(
                () -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND)
        ));
        invoice.setCreatedDate(new Date());
        invoice.setUpdatedDate(new Date());
        return MappingData.mapObject(this.invoiceRepository.save(invoice), InvoiceResponse.class);
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest invoiceRequest) {
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_NOT_FOUND));
        NullUtils.updateIfPresent(invoice::setNote, invoiceRequest.getNote());
        NullUtils.updateIfPresent(invoice::setStatus, invoiceRequest.getStatus());
        invoice.setUpdatedDate(new Date());
        return MappingData.mapObject(this.invoiceRepository.save(invoice), InvoiceResponse.class);
    }

    @Override
    public List<InvoiceResponse> getInvoicesByCustomer(Long idCustomer) {
        List<Invoice> invoices = this.invoiceRepository.findInvoicesByCustomer_Id(idCustomer).orElse(null);
        assert invoices != null;
        return MappingData.mapListObject(invoices, InvoiceResponse.class);
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_NOT_FOUND));
        this.invoiceRepository.delete(invoice);
    }

    @Override
    public List<InvoiceResponse> getAllInvoice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return MappingData.mapListObject(this.invoiceRepository.findAll(pageable).getContent(), InvoiceResponse.class);
    }
}
