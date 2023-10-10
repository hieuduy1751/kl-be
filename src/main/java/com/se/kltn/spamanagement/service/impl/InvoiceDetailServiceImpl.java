package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.InvoiceDetailRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceDetailResponse;
import com.se.kltn.spamanagement.dto.response.InvoiceResponse;
import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.InvoiceDetail;
import com.se.kltn.spamanagement.repository.InvoiceDetailRepository;
import com.se.kltn.spamanagement.repository.InvoiceRepository;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.repository.TreatmentRepository;
import com.se.kltn.spamanagement.service.InvoiceDetailService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService {

    private final InvoiceDetailRepository invoiceDetailRepository;

    private final InvoiceRepository invoiceRepository;

    private final TreatmentRepository treatmentRepository;

    private final ProductRepository productRepository;

    @Autowired
    public InvoiceDetailServiceImpl(InvoiceDetailRepository invoiceDetailRepository, InvoiceRepository invoiceRepository, TreatmentRepository treatmentRepository, ProductRepository productRepository) {
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.invoiceRepository = invoiceRepository;
        this.treatmentRepository = treatmentRepository;
        this.productRepository = productRepository;
    }


    @Override
    public InvoiceDetailResponse createInvoiceDetail(InvoiceDetailRequest invoiceDetailRequest) {
        InvoiceDetail invoiceDetail = MappingData.mapObject(invoiceDetailRequest, InvoiceDetail.class);
        invoiceDetail.setInvoice(this.invoiceRepository.findById(invoiceDetailRequest.getIdInvoice()).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_NOT_FOUND)
        ));
        invoiceDetail.setProduct(this.productRepository.findById(invoiceDetailRequest.getIdProduct()).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
        ));
        invoiceDetail.setTreatment(this.treatmentRepository.findById(invoiceDetailRequest.getIdTreatment()).orElseThrow(
                () -> new ResourceNotFoundException(TREATMENT_NOT_FOUND)
        ));

        invoiceDetail.setTotalPrice(invoiceDetail.getProduct().getPrice() * invoiceDetail.getTotalQuantity());
        return mapToResponse(this.invoiceDetailRepository.save(invoiceDetail));
    }


    @Override
    public InvoiceDetailResponse updateInvoiceDetail(Long id, InvoiceDetailRequest invoiceDetailRequest) {
        InvoiceDetail invoiceDetail = this.invoiceDetailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_DETAIL_NOT_FOUND));
        NullUtils.updateIfPresent(invoiceDetail::setTotalQuantity, invoiceDetailRequest.getTotalQuantity());
        invoiceDetail.setTotalPrice(invoiceDetail.getProduct().getPrice() * invoiceDetail.getTotalQuantity());
        return mapToResponse(this.invoiceDetailRepository.save(invoiceDetail));
    }

    @Override
    public void deleteInvoiceDetail(Long id) {
        InvoiceDetail invoiceDetail = this.invoiceDetailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_DETAIL_NOT_FOUND));
        this.invoiceDetailRepository.delete(invoiceDetail);
    }

    private InvoiceDetailResponse mapToResponse(InvoiceDetail invoiceDetail) {
        InvoiceDetailResponse response = MappingData.mapObject(invoiceDetail, InvoiceDetailResponse.class);
        response.setInvoiceResponse(MappingData.mapObject(invoiceDetail.getInvoice(), InvoiceResponse.class));
        response.setProductResponse(MappingData.mapObject(invoiceDetail.getProduct(), ProductResponse.class));
        return response;
    }
}
