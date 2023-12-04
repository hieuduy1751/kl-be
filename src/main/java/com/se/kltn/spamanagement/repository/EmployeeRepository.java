package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employees e WHERE " +
            "LOWER(REPLACE(CONCAT(UNACCENT(e.first_name), ' ', UNACCENT(e.last_name)), ' ', '')) LIKE " +
            "LOWER(REPLACE(CONCAT('%', UNACCENT(:text), '%'), ' ', ''))", nativeQuery = true)
    List<Employee> getEmployeesByText(@Param("text") String text);

    @Query(value = "SELECT * FROM employees e WHERE " +
            "LOWER(REPLACE(CONCAT(UNACCENT(e.first_name), ' ', UNACCENT(e.last_name)), ' ', '')) LIKE " +
            "LOWER(REPLACE(CONCAT('%', UNACCENT(:text), '%'), ' ', '')) AND e.position ='THERAPIST'", nativeQuery = true)
    List<Employee> getEmployeesIsTherapistByText(@Param("text") String text);

    @Query(value = "select * from employees e where e.id not in " +
            "(select a.employee_id from appointments a where a.time between :startDate and :endDate) and e.position = 'THERAPIST'", nativeQuery = true)
    List<Employee> findEmployeesNotInAppointmentTime(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
