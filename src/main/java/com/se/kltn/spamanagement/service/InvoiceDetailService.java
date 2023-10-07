package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.InvoiceDetailRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceDetailResponse;

public interface InvoiceDetailService {

    InvoiceDetailResponse createInvoiceDetail(Long idInvoice, Long idProduct, InvoiceDetailRequest invoiceDetailRequest);

    InvoiceDetailResponse updateInvoiceDetail(Long idInvoice, Long idProduct, InvoiceDetailRequest invoiceDetailRequest);

    void deleteInvoiceDetail(Long idInvoice, Long idProduct);
}
