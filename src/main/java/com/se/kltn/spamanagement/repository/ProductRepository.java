package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.dto.projection.TopMostPopularProductInterface;
import com.se.kltn.spamanagement.model.Product;
import com.se.kltn.spamanagement.constants.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Page<Product> getProductsByCategory(ProductType category, Pageable pageable);

    @Query(value = "SELECT * from products where price between :from and :to", nativeQuery = true)
    Optional<List<Product>> filterProductsByPriceBetween(@Param("from") Double from, @Param("to") Double to);

    @Query(value = "SELECT * FROM products p WHERE " +
            "LOWER(REPLACE(CONCAT(UNACCENT(p.name), ' ', UNACCENT(p.description)), ' ', '')) LIKE " +
            "LOWER(REPLACE(CONCAT('%', UNACCENT(:text), '%'), ' ', ''))", nativeQuery = true)
    List<Product> getProductsByText(@Param("text") String text);

    @Query(value = "SELECT \n" +
            "    p.id,p.\"name\" ,p.description ,p.category ,p.price ,\n" +
            "    count(a.id) as numOfAppointment \n" +
            "FROM \n" +
            "   products p \n" +
            "join \n" +
            "\tappointments a on p.id =a.product_id \n" +
            "where a.status = 'FINISHED'\n" +
            "group by p.id, p.\"name\"\n" +
            "order by numOfAppointment desc\n" +
            "fetch first :numOfRow row only",nativeQuery = true)
    List<TopMostPopularProductInterface> getTopPopularProductByAppointment(@Param("numOfRow") int numOfRow);

    @Query(value = "SELECT * FROM products p WHERE " +
            "LOWER(REPLACE(CONCAT(UNACCENT(p.name), ' ', UNACCENT(p.description)), ' ', '')) LIKE " +
            "LOWER(REPLACE(CONCAT('%', UNACCENT(:text), '%'), ' ', '')" +
            "AND p.category = 'DEVICE')", nativeQuery = true)
    List<Product> getProductsByTextAndCategoryIsDevice(@Param("text") String text);
}
