package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Product;
import com.se.kltn.spamanagement.constants.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Page<Product> getProductsByCategory(Category category, Pageable pageable);

    @Query(value = "SELECT * from products where price between :from and :to", nativeQuery = true)
    Optional<List<Product>> filterProductsByPriceBetween(@Param("from") Double from, @Param("to") Double to);

    @Query(value = "SELECT * FROM products p WHERE " +
            "LOWER(REPLACE(CONCAT(UNACCENT(p.name), ' ', UNACCENT(p.description)), ' ', '')) LIKE " +
            "LOWER(REPLACE(CONCAT('%', UNACCENT(:text), '%'), ' ', ''))", nativeQuery = true)
    List<Product> getProductsByText(@Param("text") String text);
}
