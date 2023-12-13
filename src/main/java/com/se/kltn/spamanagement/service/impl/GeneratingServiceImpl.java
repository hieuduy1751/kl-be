package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.enums.ProductType;
import com.se.kltn.spamanagement.dto.pojo.InvoiceDetailGeneratePojo;
import com.se.kltn.spamanagement.dto.pojo.InvoiceGeneratePojo;
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

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public GeneratingServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    private JasperPrint getJasperPrint(List<?> collection, Map<String, Object> parameters, String resourceLocation) throws FileNotFoundException, JRException {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(ResourceUtils.getFile(resourceLocation).getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collection);
            parameters.put("detailDataset", dataSource);
            return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        } catch (Exception e) {
            e.printStackTrace();
            throw new JRException("Error generating JasperPrint.");
        }
    }

    private Path getUploadPath(String fileFormat, JasperPrint jasperPrint, String fileName, String uploadDir) throws IOException, JRException {
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

    @Override
    @Transactional
    public String generateInvoice(String fileFormat, Long invoiceId, String uploadDir) throws JRException, IOException {
        log.info("Generating invoice to pdf with id: " + invoiceId);
        try {
            Invoice invoice = this.invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new ResourceNotFoundException(INVOICE_NOT_FOUND));

            InvoiceGeneratePojo invoiceGeneratePojo = getInvoicePojo(invoice);
            List<InvoiceDetailGeneratePojo> listInvoiceDetailPojo = getListInvoiceDetailPojo(invoice);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("invoice", invoiceGeneratePojo);
            String fileName = "invoice_" + invoiceGeneratePojo.getIdInvoice() + "_" + invoiceGeneratePojo.getCustomerName().replaceAll("\\s+","_") + ".pdf";

            JasperPrint jasperPrint = getJasperPrint(listInvoiceDetailPojo, parameters, RESOURCE_INVOICE);
            Path uploadPath = getUploadPath(fileFormat, jasperPrint, fileName, uploadDir);

            String pdfFileLink = StringUtils.cleanPath(uploadPath + "\\" + fileName);
            // Log thông tin
            log.info("file invoice pdf generated to pdf: " + pdfFileLink);

            return pdfFileLink;
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
        invoiceDetailGeneratePojo.setProductType(convertTypeToString(product.getType()));
        invoiceDetailGeneratePojo.setTotalPrice(String.format("%.0f", invoiceDetail.getTotalPrice()));
        return invoiceDetailGeneratePojo;
    }

    private String convertTypeToString(ProductType productType) {
        if (productType == ProductType.PRODUCT) {
            return "Sản phẩm";
        } else if (productType == ProductType.SERVICE) {
            return "Dịch vụ";
        } else if (productType == ProductType.TREATMENT) {
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
