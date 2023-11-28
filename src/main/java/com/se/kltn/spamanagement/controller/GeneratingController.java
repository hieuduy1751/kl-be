package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.service.GeneratingService;
import io.swagger.v3.oas.annotations.Operation;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/generating")
public class GeneratingController {

    private final GeneratingService reportService;

    @Autowired
    public GeneratingController(GeneratingService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(value = "/invoice/{idInvoice}")
    @Operation(summary = "generate invoice")
    public ResponseEntity<String> generateInvoice(@RequestParam(name = "fileFormat", defaultValue = "pdf") String fileFormat,
                                  @PathVariable Long idInvoice,
                                  @RequestParam(name = "uploadDir") String uploadDir) throws JRException, IOException {
        String fileLink = reportService.generateInvoice(fileFormat, idInvoice, uploadDir);
        return ResponseEntity.ok().body(fileLink);
    }
}
