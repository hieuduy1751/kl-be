package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.ProductRequest;
import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.model.Product;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Long id);

    List<ProductResponse> getProducts(int page, int size);

    void deleteProduct(Long id);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    ProductResponse createProduct(ProductRequest product);
}
