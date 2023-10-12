package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.InvoiceDetailRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceDetailResponse;
import com.se.kltn.spamanagement.model.InvoiceDetail;
import com.se.kltn.spamanagement.model.InvoiceDetailId;

public interface InvoiceDetailService {

    InvoiceDetailResponse createInvoiceDetail(InvoiceDetail invoiceDetail, Long idProduct);

    InvoiceDetailResponse updateInvoiceDetail(InvoiceDetailId invoiceDetailId, InvoiceDetailRequest invoiceDetailRequest);

    void deleteInvoiceDetail(InvoiceDetailId invoiceDetailId);
}
