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

    @PostMapping("/report")
    @Operation(summary = "generate report")
    public String generateReport(@RequestParam(name = "fileFormat") String fileFormat) throws JRException, IOException {
        String fileLink = reportService.generateReport(fileFormat);
        return "redirect:/" + fileLink;
    }

    @GetMapping("/invoice/{idInvoice}")
    @Operation(summary = "generate invoice")
    public String generateInvoice(@RequestParam(name = "fileFormat") String fileFormat, @PathVariable Long idInvoice) throws JRException, IOException {
        String fileLink = reportService.generateInvoice(fileFormat, idInvoice);
        return "redirect:/" + fileLink;
    }
}
