package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
    List<Invoice> findInvoicesByCustomer_Id(Long idCustomer);

    List<Invoice> findInvoicesByUpdatedDateBetweenAndStatus(Date startDate, Date endDate, Status status);

}
