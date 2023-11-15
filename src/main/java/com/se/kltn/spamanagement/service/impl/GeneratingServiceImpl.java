package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.enums.Category;
import com.se.kltn.spamanagement.dto.pojo.InvoiceDetailGeneratePojo;
import com.se.kltn.spamanagement.dto.pojo.InvoiceGeneratePojo;
import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Customer;
import com.se.kltn.spamanagement.model.Invoice;
import com.se.kltn.spamanagement.model.InvoiceDetail;
import com.se.kltn.spamanagement.model.Product;
import com.se.kltn.spamanagement.repository.InvoiceRepository;
import com.se.kltn.spamanagement.service.GeneratingService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.se.kltn.spamanagement.constants.ErrorMessage.INVOICE_NOT_FOUND;

@Service
@Slf4j
public class GeneratingServiceImpl implements GeneratingService {

    private static final String RESOURCE_INVOICE = "src/main/resources/report/invoice.jrxml";

    private static final String RESOURCE_REPORT = "src/main/resources/report/invoice.jrxml";

    private final InvoiceServiceImpl invoiceService;

    private final ProductServiceImpl productService;

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public GeneratingServiceImpl(InvoiceServiceImpl invoiceService, ProductServiceImpl productService, InvoiceRepository invoiceRepository) {
        this.invoiceService = invoiceService;
        this.productService = productService;
        this.invoiceRepository = invoiceRepository;
    }

    private JasperPrint getJasperPrint(List<?> collection, Map<String, Object> parameters, String resourceLocation) throws FileNotFoundException, JRException {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(ResourceUtils.getFile(resourceLocation).getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collection);
            parameters.put("detailDataset", dataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return jasperPrint;
        } catch (Exception e) {
            e.printStackTrace();
            throw new JRException("Error generating JasperPrint.");
        }
    }

    private Path getUploadPath(String fileFormat, JasperPrint jasperPrint, String fileName) throws IOException, JRException {
        String uploadDir = StringUtils.cleanPath("../generated-file");
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        if (fileFormat.equalsIgnoreCase("pdf")) {
            String filePath = uploadPath.resolve(fileName).toString();
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
        }
        return uploadPath;
    }

    private String getPdfFileLink(String uploadPath) {
        return uploadPath + "/" + "productReport.pdf";
    }

    @Override
    public String generateReport(String fileFormat) throws JRException, IOException {
        List<ProductResponse> objects = this.productService.getProductsByCategory(Category.PRODUCT.name(), 0, 10);
        JasperPrint jasperPrint = getJasperPrint(objects, null, RESOURCE_REPORT);
        Path uploadPath = getUploadPath(fileFormat, jasperPrint, "report.pdf");
        return getPdfFileLink(uploadPath.toString());
    }

    @Override
    @Transactional
    public String generateInvoice(String fileFormat, Long invoiceId) throws JRException, IOException {
        log.info("Generating invoice to pdf with id: " + invoiceId);
        try {
            Invoice invoice = this.invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new ResourceNotFoundException(INVOICE_NOT_FOUND));

            InvoiceGeneratePojo invoiceGeneratePojo = getInvoicePojo(invoice);
            List<InvoiceDetailGeneratePojo> listInvoiceDetailPojo = getListInvoiceDetailPojo(invoice);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("invoice", invoiceGeneratePojo);
            String fileName = "invoice_" + invoiceGeneratePojo.getIdInvoice() + "_" + invoiceGeneratePojo.getCustomerName() + ".pdf";

            JasperPrint jasperPrint = getJasperPrint(listInvoiceDetailPojo, parameters, RESOURCE_INVOICE);
            Path uploadPath = getUploadPath(fileFormat, jasperPrint, fileName);

            // Log thông tin
            log.info("file invoice generated to pdf");

            return getPdfFileLink(uploadPath.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new JRException("Error generating JasperPrint.");
        }
    }

    private List<InvoiceDetailGeneratePojo> getListInvoiceDetailPojo(Invoice invoice) {
        List<InvoiceDetail> invoiceDetails = invoice.getInvoiceDetails();
        List<InvoiceDetailGeneratePojo> detailGeneratePojos = new ArrayList<>();
        int index = 1;
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            InvoiceDetailGeneratePojo invoiceDetailGeneratePojo = mapToInvoiceDetailGeneratePojo(invoiceDetail, index);
            detailGeneratePojos.add(invoiceDetailGeneratePojo);
            index++;
        }
        return detailGeneratePojos;
    }

    private InvoiceDetailGeneratePojo mapToInvoiceDetailGeneratePojo(InvoiceDetail invoiceDetail, int index) {
        InvoiceDetailGeneratePojo invoiceDetailGeneratePojo = new InvoiceDetailGeneratePojo();
        Product product = invoiceDetail.getProduct();
        invoiceDetailGeneratePojo.setIndex(index);
        invoiceDetailGeneratePojo.setProductQuantity(invoiceDetail.getTotalQuantity());
        invoiceDetailGeneratePojo.setProductPrice(String.format("%.0f", product.getPrice()));
        invoiceDetailGeneratePojo.setProductName(product.getName());
        invoiceDetailGeneratePojo.setProductType(convertTypeToString(product.getCategory()));
        invoiceDetailGeneratePojo.setTotalPrice(String.format("%.0f", invoiceDetail.getTotalPrice()));
        return invoiceDetailGeneratePojo;
    }

    private String convertTypeToString(Category category) {
        if (category == Category.PRODUCT) {
            return "Sản phẩm";
        } else if (category == Category.SERVICE) {
            return "Dịch vụ";
        } else if (category == Category.TREATMENT) {
            return "Liệu trình";
        }
        return null;
    }

    private InvoiceGeneratePojo getInvoicePojo(Invoice invoice) {
        Customer customer = invoice.getCustomer();
        String employeeName = invoice.getEmployee().getFirstName() + " " + invoice.getEmployee().getLastName();
        String customerName = customer.getFirstName() + " " + customer.getLastName();
        String customerPhone = customer.getPhoneNumber();
        String customerEmail = customer.getEmail();
        String paymentMethod = invoice.getPaymentMethod().toString();
        Double totalAmount = invoice.getTotalAmount();
        Date createdDate = invoice.getCreatedDate();
        return InvoiceGeneratePojo.builder()
                .idInvoice(invoice.getId())
                .employeeName(employeeName)
                .customerName(customerName)
                .customerPhone(customerPhone)
                .customerEmail(customerEmail)
                .paymentMethod(paymentMethod)
                .totalAmount(String.format("%.0f", totalAmount))
                .createdDate(createdDate)
                .build();
    }

}
