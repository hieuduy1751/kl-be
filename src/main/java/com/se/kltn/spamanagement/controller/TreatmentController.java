package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.TreatmentRequest;
import com.se.kltn.spamanagement.service.TreatmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping
    @Operation(summary = "get list treatment")
    public ResponseEntity<Object> getListTreatment(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                                   @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.treatmentService.getListTreatment(page, size));
    }

    @PostMapping
    @Operation(summary = "create treatment")
    public ResponseEntity<Object> createTreatment(@Valid @RequestBody TreatmentRequest treatmentRequest) {
        return ResponseEntity.ok().body(this.treatmentService.createTreatment(treatmentRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update treatment")
    public ResponseEntity<Object> updateTreatment(@PathVariable Long id, TreatmentRequest treatmentRequest) {
        return ResponseEntity.ok().body(this.treatmentService.updateTreatment(id, treatmentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTreatment(@PathVariable Long id){
        this.treatmentService.deleteTreatment(id);
        return ResponseEntity.ok().body("Treatment deleted");
    }
}
