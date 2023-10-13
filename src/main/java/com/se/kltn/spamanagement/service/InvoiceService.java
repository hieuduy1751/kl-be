package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.InvoiceCreateRequest;
import com.se.kltn.spamanagement.dto.request.InvoiceUpdateRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {

    InvoiceResponse createInvoice(InvoiceCreateRequest invoiceCreateRequest);

    InvoiceResponse updateInvoice(Long id, InvoiceUpdateRequest invoiceUpdateRequest);

    List<InvoiceResponse> getInvoicesByCustomer(Long idCustomer);

    void deleteInvoice(Long id);

    List<InvoiceResponse> getAllInvoice(int page, int size);
}
