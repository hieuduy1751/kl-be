package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.InvoiceDetailRequest;
import com.se.kltn.spamanagement.dto.response.InvoiceDetailResponse;
import com.se.kltn.spamanagement.dto.response.InvoiceResponse;
import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Invoice;
import com.se.kltn.spamanagement.model.InvoiceDetail;
import com.se.kltn.spamanagement.model.InvoiceDetailId;
import com.se.kltn.spamanagement.repository.InvoiceDetailRepository;
import com.se.kltn.spamanagement.repository.InvoiceRepository;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.service.InvoiceDetailService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
@Log4j2
public class InvoiceDetailServiceImpl implements InvoiceDetailService {

    private final InvoiceDetailRepository invoiceDetailRepository;


    private final ProductRepository productRepository;

    @Autowired
    public InvoiceDetailServiceImpl(InvoiceDetailRepository invoiceDetailRepository, ProductRepository productRepository) {
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.productRepository = productRepository;
    }

    @Override
    public InvoiceDetailResponse createInvoiceDetail(InvoiceDetail invoiceDetail, Long idProduct) {
        log.info("Create invoice detail");
        invoiceDetail.setProduct(this.productRepository.findById(idProduct).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
        ));
        invoiceDetail.setTotalPrice(invoiceDetail.getProduct().getPrice() * invoiceDetail.getTotalQuantity());
        InvoiceDetail invoiceDetailSaved = this.invoiceDetailRepository.saveAndFlush(invoiceDetail);
        return mapToResponse(invoiceDetailSaved);
    }


    @Override
    public InvoiceDetailResponse updateInvoiceDetail(InvoiceDetailId invoiceDetailId, InvoiceDetailRequest invoiceDetailRequest) {
        log.info("Update invoice detail");
        InvoiceDetail invoiceDetail = this.invoiceDetailRepository.findById(invoiceDetailId).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_DETAIL_NOT_FOUND));
        NullUtils.updateIfPresent(invoiceDetail::setTotalQuantity, invoiceDetailRequest.getTotalQuantity());
        invoiceDetail.setTotalPrice(invoiceDetail.getProduct().getPrice() * invoiceDetail.getTotalQuantity());
        return mapToResponse(this.invoiceDetailRepository.save(invoiceDetail));
    }

    @Override
    public void deleteInvoiceDetail(InvoiceDetailId invoiceDetailId) {
        log.info("Delete invoice detail");
        InvoiceDetail invoiceDetail = this.invoiceDetailRepository.findById(invoiceDetailId).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_DETAIL_NOT_FOUND));
        this.invoiceDetailRepository.delete(invoiceDetail);
    }

    @Override
    public List<InvoiceDetailResponse> getInvoiceDetailsByInvoiceId(Long invoiceId) {
        log.info("Get invoice details by invoice id");
        List<InvoiceDetail> invoiceDetails = this.invoiceDetailRepository.findInvoiceDetailsByInvoice_Id(invoiceId);
        return MappingData.mapListObject(invoiceDetails, InvoiceDetailResponse.class);
    }

    public InvoiceDetailResponse mapToResponse(InvoiceDetail invoiceDetail) {
        log.info("Map to invoice detail response");
        InvoiceDetailResponse response = MappingData.mapObject(invoiceDetail, InvoiceDetailResponse.class);
        response.setProductResponse(MappingData.mapObject(invoiceDetail.getProduct(), ProductResponse.class));
        return response;
    }

}
