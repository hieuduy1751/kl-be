package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query(value = "SELECT * FROM customers c WHERE " +
            "LOWER(REPLACE(CONCAT(UNACCENT(c.first_name), ' ', UNACCENT(c.last_name), ' ', UNACCENT(c.email), ' ', c.phone_number), ' ', ''))" +
            "LIKE LOWER(REPLACE(CONCAT('%', UNACCENT(:name), '%'), ' ', ''))", nativeQuery = true)
    List<Customer> getCustomersByText(@Param("text") String text);
}
