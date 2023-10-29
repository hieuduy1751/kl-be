package com.se.kltn.spamanagement.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    String generateReport(LocalDate localDate, String fileFormat) throws JRException, IOException;
}
