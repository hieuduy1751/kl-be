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
    public ResponseEntity<Object> createInvoiceDetail(@RequestBody InvoiceDetailRequest invoiceDetailRequest) {
        return ResponseEntity.ok().body(this.invoiceDetailService.createInvoiceDetail(invoiceDetailRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update invoice detail")
    public ResponseEntity<Object> updateInvoiceDetail(@PathVariable Long id,
                                                      @RequestBody InvoiceDetailRequest invoiceDetailRequest) {
        return ResponseEntity.ok().body(this.invoiceDetailService.updateInvoiceDetail(id, invoiceDetailRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete invoice detail")
    public ResponseEntity<String> deleteInvoiceDetail(@PathVariable Long id) {
        this.invoiceDetailService.deleteInvoiceDetail(id);
        return ResponseEntity.ok().body("Invoice detail deleted");
    }
}
