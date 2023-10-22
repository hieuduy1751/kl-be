package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.InvoiceCreateRequest;
import com.se.kltn.spamanagement.dto.request.InvoiceUpdateRequest;
import com.se.kltn.spamanagement.model.InvoiceDetailId;
import com.se.kltn.spamanagement.service.InvoiceDetailService;
import com.se.kltn.spamanagement.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;


    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    @Operation(summary = "get list invoice")
    public ResponseEntity<Object> getListInvoice(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                                 @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.invoiceService.getAllInvoice(page, size));
    }

    @GetMapping("/customer")
    @Operation(summary = "get list invoice by customer")
    public ResponseEntity<Object> getListInvoiceByCustomer(@RequestParam("customerId") Long customerId) {
        return ResponseEntity.ok().body(this.invoiceService.getInvoicesByCustomer(customerId));
    }

    @PostMapping
    @Operation(summary = "create invoice")
    public ResponseEntity<Object> createInvoice(@RequestBody InvoiceCreateRequest invoiceRequest) {
        return ResponseEntity.ok().body(this.invoiceService.createInvoice(invoiceRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update invoice")
    public ResponseEntity<Object> updateInvoice(@PathVariable("id") Long id,
                                                @RequestBody InvoiceUpdateRequest invoiceRequest) {
        return ResponseEntity.ok().body(this.invoiceService.updateInvoice(id, invoiceRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete invoice")
    public ResponseEntity<String> deleteInvoice(@PathVariable("id") Long id) {
        this.invoiceService.deleteInvoice(id);
        return ResponseEntity.ok().body("Invoice deleted");
    }
}
