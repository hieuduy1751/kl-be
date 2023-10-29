package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    @Operation(summary = "generate report")
    public String generateReport(@RequestParam(name = "fileFormat") String fileFormat) throws JRException, IOException {
        String fileLink = reportService.generateReport(null, fileFormat);
        return "redirect:/" + fileLink;
    }
}
