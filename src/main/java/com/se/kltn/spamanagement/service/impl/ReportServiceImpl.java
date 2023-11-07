package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.enums.Category;
import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

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

    private final ProductRepository productRepository;

    @Autowired
    public ReportServiceImpl(InvoiceServiceImpl invoiceService, ProductServiceImpl productService, ProductRepository productRepository) {
        this.invoiceService = invoiceService;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    private JasperPrint getJasperPrint(List<ProductResponse> objects, String resourceLocation) throws FileNotFoundException, JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(ResourceUtils.getFile(resourceLocation).getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(objects);
        return JasperFillManager.fillReport(jasperReport, null, dataSource);
    }

    private Path getUploadPath(String fileFormat, JasperPrint jasperPrint) throws IOException, JRException {
        String uploadDir = StringUtils.cleanPath("./generated-reports");
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        if (fileFormat.equalsIgnoreCase("pdf")) {
            String filePath = uploadPath.resolve("productReport.pdf").toString();
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
        }
        return uploadPath;
    }

    private String getPdfFileLink(String uploadPath) {
        return uploadPath + "/" + "productReport.pdf";
    }

    @Override
    public String generateReport(LocalDate localDate, String fileFormat) throws JRException, IOException {
        List<ProductResponse> objects = this.productService.getProductsByCategory(Category.PRODUCT.name(), 0, 10);
        String resourceLocation = "src/main/resources/report/productReport.jrxml";
        JasperPrint jasperPrint = getJasperPrint(objects, resourceLocation);
        Path uploadPath = getUploadPath(fileFormat, jasperPrint);
        return getPdfFileLink(uploadPath.toString());
    }

}
