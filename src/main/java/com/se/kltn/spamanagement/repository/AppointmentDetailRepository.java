package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.AppointmentDetail;
import com.se.kltn.spamanagement.model.AppointmentDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentDetailRepository extends JpaRepository<AppointmentDetail, AppointmentDetailId> {
}
