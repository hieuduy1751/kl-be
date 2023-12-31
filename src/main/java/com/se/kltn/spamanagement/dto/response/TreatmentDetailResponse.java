package com.se.kltn.spamanagement.dto.response;

import com.se.kltn.spamanagement.constants.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreatmentDetailResponse {

    private ProductResponse productResponse;

    private CustomerResponse customerResponse;

    private EmployeeResponse employeeResponse;

    private InvoiceResponse invoiceResponse;

    private Status status;

    private String note;

    private String imageBefore;

    private String imageCurrent;

    private String imageResult;

    private String createdDate;

    private String updatedDate;


}
