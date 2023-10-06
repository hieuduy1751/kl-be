package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.InvoiceRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {

    InvoiceResponse createInvoice(InvoiceRequest invoiceRequest);

    InvoiceResponse updateInvoice(Long id, InvoiceRequest invoiceRequest);

    List<InvoiceResponse> getInvoicesByCustomer(Long idCustomer);

    void deleteInvoice(Long id);

    List<InvoiceResponse> getAllInvoice(int page, int size);
}
