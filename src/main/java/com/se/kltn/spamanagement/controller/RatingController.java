package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.RatingRequest;
import com.se.kltn.spamanagement.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/product/{idProduct}")
    @Operation(summary = "add rating to product")
    public ResponseEntity<Object> addRatingToProduct(@Valid @RequestBody RatingRequest ratingRequest, @PathVariable Long idProduct) {
        return ResponseEntity.ok().body(this.ratingService.addRatingToProduct(ratingRequest, idProduct));
    }

    @PostMapping("/treatment/{idTreatment}")
    @Operation(summary = "add rating to treatment")
    public ResponseEntity<Object> addRatingTreatment(@Valid @RequestBody RatingRequest ratingRequest, @PathVariable Long idTreatment) {
        return ResponseEntity.ok().body(this.ratingService.addRatingToTreatment(ratingRequest, idTreatment));
    }

    @GetMapping("/product/{idProduct}")
    @Operation(summary = "get rating by product id")
    public ResponseEntity<Object> getRatingByProduct(@PathVariable Long idProduct) {
        return ResponseEntity.ok().body(this.ratingService.getRatingByProductId(idProduct));
    }

    @GetMapping("/treatment/{idTreatment}")
    @Operation(summary = "get rating by treatment id")
    public ResponseEntity<Object> getRatingByTreatment(@PathVariable Long idTreatment) {
        return ResponseEntity.ok().body(this.ratingService.getRatingByProductId(idTreatment));
    }
}
