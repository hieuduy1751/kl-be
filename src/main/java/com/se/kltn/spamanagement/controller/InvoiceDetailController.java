package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.InvoiceDetailRequest;
import com.se.kltn.spamanagement.service.InvoiceDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice-detail")
public class InvoiceDetailController {

    private final InvoiceDetailService invoiceDetailService;

    @Autowired
    public InvoiceDetailController(InvoiceDetailService invoiceDetailService) {
        this.invoiceDetailService = invoiceDetailService;
    }

    @PostMapping
    @Operation(summary = "create invoice detail")
    public ResponseEntity<Object> createInvoiceDetail(@RequestParam("invoiceId") Long invoiceId,
                                                      @RequestParam("productId") Long productId,
                                                      @RequestBody InvoiceDetailRequest invoiceDetailRequest) {
        return ResponseEntity.ok().body(this.invoiceDetailService.createInvoiceDetail(invoiceId, productId, invoiceDetailRequest));
    }

    @PutMapping
    @Operation(summary = "update invoice detail")
    public ResponseEntity<Object> updateInvoiceDetail(@RequestParam("invoiceId") Long invoiceId,
                                                      @RequestParam("productId") Long productId,
                                                      @RequestBody InvoiceDetailRequest invoiceDetailRequest) {
        return ResponseEntity.ok().body(this.invoiceDetailService.updateInvoiceDetail(invoiceId, productId, invoiceDetailRequest));
    }

    @DeleteMapping
    @Operation(summary = "delete invoice detail")
    public ResponseEntity<String> deleteInvoiceDetail(@RequestParam("invoiceId") Long invoiceId,
                                                      @RequestParam("productId") Long productId) {
        this.invoiceDetailService.deleteInvoiceDetail(invoiceId, productId);
        return ResponseEntity.ok().body("Invoice detail deleted");
    }
}
