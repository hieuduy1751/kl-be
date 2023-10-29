package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final InvoiceServiceImpl invoiceService;

    private final ProductServiceImpl productService;

    @Autowired
    public ReportServiceImpl(InvoiceServiceImpl invoiceService, ProductServiceImpl productService) {
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    private JasperPrint getJasperPrint(List<Object> objects, String resourceLocation) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(resourceLocation);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(objects);
        return JasperFillManager.fillReport(jasperReport, null, dataSource);
    }

    private Path getUploadPath(String fileFormat, JasperPrint jasperPrint, String fileName) throws IOException, JRException {
        String uploadDir = StringUtils.cleanPath("./generated-reports");
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        if (fileFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, uploadPath + fileName);
        }
        return uploadPath;
    }

    private String getPdfFileLink(String uploadPath, String fileName) {
        return uploadPath + "/" + fileName;
    }

    @Override
    public String generateReport(LocalDate localDate, String fileFormat) {
        return null;
    }
}
