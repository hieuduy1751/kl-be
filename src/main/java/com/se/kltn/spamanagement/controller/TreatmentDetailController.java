package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.TreatmentDetailRequest;
import com.se.kltn.spamanagement.model.TreatmentDetailId;
import com.se.kltn.spamanagement.service.TreatmentDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/treatment-detail")
public class TreatmentDetailController {

    private final TreatmentDetailService treatmentDetailService;

    @Autowired
    public TreatmentDetailController(TreatmentDetailService treatmentDetailService) {
        this.treatmentDetailService = treatmentDetailService;
    }

    @PostMapping
    @Operation(summary = "add treatment detail")
    public ResponseEntity<Object> addTreatmentDetail(@RequestParam("customerId") Long customerId,
                                                     @RequestParam("productId") Long productId,
                                                     @RequestBody TreatmentDetailRequest treatmentDetailRequest) {
        return ResponseEntity.ok().body(this.treatmentDetailService.addTreatmentDetail(new TreatmentDetailId(productId, customerId), treatmentDetailRequest));
    }

    @PutMapping
    @Operation(summary = "update treatment detail")
    public ResponseEntity<Object> updateTreatmentDetail(@RequestParam("customerId") Long customerId,
                                                        @RequestParam("productId") Long productId,
                                                        @Valid @RequestBody TreatmentDetailRequest treatmentDetailRequest) {
        return ResponseEntity.ok().body(this.treatmentDetailService.updateTreatmentDetail(new TreatmentDetailId(productId, customerId), treatmentDetailRequest));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "get treatment detail by customer")
    public ResponseEntity<Object> getTreatmentDetailByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(this.treatmentDetailService.getTreatmentDetailByCustomer(customerId));
    }

    @GetMapping
    @Operation(summary = "get list treatment detail")
    public ResponseEntity<Object> getListTreatmentDetail(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                                         @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.treatmentDetailService.getListTreatmentDetail(page, size));
    }

    @DeleteMapping
    @Operation(summary = "delete treatment detail")
    public ResponseEntity<String> deleteTreatmentDetail(@RequestBody TreatmentDetailId treatmentDetailId) {
        this.treatmentDetailService.deleteTreatmentDetail(treatmentDetailId);
        return ResponseEntity.ok().body("Treatment detail deleted");
    }
}
