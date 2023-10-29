package com.se.kltn.spamanagement.service;

import java.time.LocalDate;

public interface ReportService {
    String generateReport(LocalDate localDate, String fileFormat);
}
