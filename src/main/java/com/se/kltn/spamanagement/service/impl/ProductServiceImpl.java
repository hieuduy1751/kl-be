package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.model.Product;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return this.productRepository.findById(id).orElseThrow(
                () -> new Resoure
        );
    }

    @Override
    public List<ProductResponse> getProducts() {
        return null;
    }

    @Override
    public ProductResponse deleteProduct(Long id) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Long id, Product product) {
        return null;
    }
}
