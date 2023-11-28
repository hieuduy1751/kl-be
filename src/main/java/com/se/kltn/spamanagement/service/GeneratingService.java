package com.se.kltn.spamanagement.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GeneratingService {

    String generateInvoice(String fileFormat, Long invoiceId, String uploadPath) throws JRException, IOException;
}
