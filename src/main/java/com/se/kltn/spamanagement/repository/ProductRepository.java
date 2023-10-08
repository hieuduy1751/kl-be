package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * from products p join categories c on p.category_id = c.id where c.name  ilike %:name%", nativeQuery = true)
    Optional<List<Product>> findProductsByCategory_NameContainingIgnoreCase(@Param("name") String name);
}
