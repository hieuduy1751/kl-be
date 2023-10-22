package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findInvoicesByCustomer_Id(Long idCustomer);

    @Query(value = "select * from invoices where updated_date between :startDate and :endDate and status= :status", nativeQuery = true)
    List<Invoice> findInvoicesByDateBetweenAndStatus(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, String status);

}
