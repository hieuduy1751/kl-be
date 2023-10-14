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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
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
        invoiceDetail.setProduct(this.productRepository.findById(idProduct).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
        ));
        invoiceDetail.setTotalPrice(invoiceDetail.getProduct().getPrice() * invoiceDetail.getTotalQuantity());
        return mapToResponse(this.invoiceDetailRepository.save(invoiceDetail));
    }


    @Override
    public InvoiceDetailResponse updateInvoiceDetail(InvoiceDetailId invoiceDetailId, InvoiceDetailRequest invoiceDetailRequest) {
        InvoiceDetail invoiceDetail = this.invoiceDetailRepository.findById(invoiceDetailId).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_DETAIL_NOT_FOUND));
        NullUtils.updateIfPresent(invoiceDetail::setTotalQuantity, invoiceDetailRequest.getTotalQuantity());
        invoiceDetail.setTotalPrice(invoiceDetail.getProduct().getPrice() * invoiceDetail.getTotalQuantity());
        return mapToResponse(this.invoiceDetailRepository.save(invoiceDetail));
    }

    @Override
    public void deleteInvoiceDetail(InvoiceDetailId invoiceDetailId) {
        InvoiceDetail invoiceDetail = this.invoiceDetailRepository.findById(invoiceDetailId).orElseThrow(
                () -> new ResourceNotFoundException(INVOICE_DETAIL_NOT_FOUND));
        this.invoiceDetailRepository.delete(invoiceDetail);
    }

    @Override
    public List<InvoiceDetailResponse> getInvoiceDetailsByInvoiceId(Long invoiceId) {
        List<InvoiceDetail> invoiceDetails = this.invoiceDetailRepository.findInvoiceDetailsByInvoice_Id(invoiceId);
        return MappingData.mapListObject(invoiceDetails, InvoiceDetailResponse.class);
    }

    public InvoiceDetailResponse mapToResponse(InvoiceDetail invoiceDetail) {
        InvoiceDetailResponse response = MappingData.mapObject(invoiceDetail, InvoiceDetailResponse.class);
        response.setProductResponse(MappingData.mapObject(invoiceDetail.getProduct(), ProductResponse.class));
        return response;
    }

}
