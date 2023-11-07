package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employees e WHERE " +
            "LOWER(REPLACE(CONCAT(UNACCENT(e.first_name), ' ', UNACCENT(e.last_name)), ' ', '')) LIKE " +
            "LOWER(REPLACE(CONCAT('%', UNACCENT(:text), '%'), ' ', ''))", nativeQuery = true)
    List<Employee> getEmployeesByText(@Param("text") String text);
}
