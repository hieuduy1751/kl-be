package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment,Long> {
}
