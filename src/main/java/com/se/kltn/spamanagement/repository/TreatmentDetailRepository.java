package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.TreatmentDetail;
import com.se.kltn.spamanagement.model.TreatmentDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentDetailRepository extends JpaRepository<TreatmentDetail, TreatmentDetailId> {
}
