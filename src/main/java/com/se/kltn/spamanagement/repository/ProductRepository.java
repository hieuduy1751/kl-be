package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Product;
import com.se.kltn.spamanagement.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> getProductsByCategory(Category category);

    @Query(value = "SELECT * from products where price between :from and :to", nativeQuery = true)
    Optional<List<Product>> filterProductsByPriceBetween(@Param("from") Double from, @Param("to") Double to);
}
