package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAppointmentsByCustomer_Id(Long idCustomer, Pageable pageable);

}
