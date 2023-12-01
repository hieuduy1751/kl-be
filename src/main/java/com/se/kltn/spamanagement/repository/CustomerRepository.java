package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.dto.projection.TopCustomerStatisticInterface;
import com.se.kltn.spamanagement.dto.response.TopCustomerStatisticResponse;
import com.se.kltn.spamanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query(value = "SELECT * FROM customers c WHERE " +
            "LOWER(REPLACE(CONCAT(UNACCENT(c.first_name), ' ', UNACCENT(c.last_name), ' ', UNACCENT(c.email), ' ', c.phone_number), ' ', ''))" +
            "LIKE LOWER(REPLACE(CONCAT('%', UNACCENT(:text), '%'), ' ', ''))", nativeQuery = true)
    List<Customer> getCustomersByText(@Param("text") String text);

    @Query(value = "SELECT \n" +
            "    c.id as idCustomer , c.first_name as firstName, c.last_name as lastName, c.phone_number as phoneNumber, c.\"class\" as customerClass , sum(i.total_amount) as totalSpending \n" +
            "FROM \n" +
            "   customers c\n" +
            "JOIN \n" +
            "    invoices i ON c.id =i.customer_id \n" +
            "where i.status = 'PAID'\n" +
            "group by c.id\n" +
            "order by totalSpending desc\n" +
            "fetch first :numOfRow row only", nativeQuery = true)
    List<TopCustomerStatisticInterface> getCustomersByInvoiceIsPaid(@Param("numOfRow") int numOfRow);

    Optional<Customer> findCustomerByAccount_Username(String username);
}
