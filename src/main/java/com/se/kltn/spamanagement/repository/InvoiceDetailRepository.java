package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.InvoiceDetail;
import com.se.kltn.spamanagement.model.InvoiceDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, InvoiceDetailId> {
    List<InvoiceDetail> findInvoiceDetailsByInvoice_Id(Long invoiceId);
}
