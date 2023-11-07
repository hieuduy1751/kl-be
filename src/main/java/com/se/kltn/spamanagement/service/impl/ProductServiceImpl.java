package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.ProductRequest;
import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Product;
import com.se.kltn.spamanagement.constants.enums.Category;
import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.service.ProductService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.PRODUCT_NOT_FOUND;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
        return MappingData.mapObject(product, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = this.productRepository.findAll(pageable).getContent();
        return MappingData.mapListObject(products, ProductResponse.class);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
        this.productRepository.delete(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
        NullUtils.updateIfPresent(product::setName, productRequest.getName());
        NullUtils.updateIfPresent(product::setPrice, productRequest.getPrice());
        NullUtils.updateIfPresent(product::setQuantity, productRequest.getQuantity());
        NullUtils.updateIfPresent(product::setImageUrl, productRequest.getImageUrl());
        NullUtils.updateIfPresent(product::setCategory, Category.valueOf(productRequest.getCategory().toUpperCase()));
        NullUtils.updateIfPresent(product::setDescription, productRequest.getDescription());
        product.setUpdatedDate(new Date());
        checkStatus(product);
        Product productUpdated = this.productRepository.save(product);
        return MappingData.mapObject(productUpdated, ProductResponse.class);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = MappingData.mapObject(productRequest, Product.class);
        product.setCategory(Category.valueOf(productRequest.getCategory().toUpperCase()));
        product.setCreatedDate(new Date());
        checkStatus(product);
        return MappingData.mapObject(this.productRepository.save(product), ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(String categoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = this.productRepository.getProductsByCategory(Category.valueOf(categoryName.toUpperCase()), pageable).getContent();
        return MappingData.mapListObject(products, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> filterProductsByPriceBetween(Double from, Double to) {
        List<Product> products = this.productRepository.filterProductsByPriceBetween(from, to).orElse(null);
        return MappingData.mapListObject(products, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> searchByText(String text) {
        if (text == null) {
            return getProducts(0, 10);
        }
        return MappingData.mapListObject(this.productRepository.getProductsByText(text), ProductResponse.class);
    }

    private void checkStatus(Product product) {
        if (product.getQuantity() > 0) {
            product.setStatus(Status.ACTIVE);
        } else {
            product.setStatus(Status.INACTIVE);
        }
    }
}
