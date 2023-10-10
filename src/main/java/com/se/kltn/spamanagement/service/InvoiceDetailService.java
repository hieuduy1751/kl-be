package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.InvoiceDetailRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceDetailResponse;

public interface InvoiceDetailService {

    InvoiceDetailResponse createInvoiceDetail(InvoiceDetailRequest invoiceDetailRequest);

    InvoiceDetailResponse updateInvoiceDetail(Long id, InvoiceDetailRequest invoiceDetailRequest);

    void deleteInvoiceDetail(Long id);
}
