package com.se.kltn.spamanagement.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GeneratingService {
    String generateReport(String fileFormat) throws JRException, IOException;

    String generateInvoice(String fileFormat, Long invoiceId) throws JRException, IOException;
}
