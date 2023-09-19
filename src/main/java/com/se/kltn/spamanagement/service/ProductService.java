package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Long id);

    List<ProductResponse> getProducts();

    ProductResponse deleteProduct(Long id);

    ProductResponse updateProduct(Long id, Product product);
}
