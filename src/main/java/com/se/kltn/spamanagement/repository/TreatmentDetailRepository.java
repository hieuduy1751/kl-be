package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.TreatmentDetail;
import com.se.kltn.spamanagement.model.TreatmentDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreatmentDetailRepository extends JpaRepository<TreatmentDetail, TreatmentDetailId> {
    List<TreatmentDetail> getTreatmentDetailsByCustomer_Id(Long customerId);
}
