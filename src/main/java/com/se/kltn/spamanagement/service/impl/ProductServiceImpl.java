package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.ProductRequest;
import com.se.kltn.spamanagement.dto.response.ProductResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Product;
import com.se.kltn.spamanagement.constants.enums.ProductType;
import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.service.ProductService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.PRODUCT_NOT_FOUND;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        log.info("get product by id: " + id);
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
        return MappingData.mapObject(product, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getProducts(int page, int size) {
        log.info("get list product");
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = this.productRepository.findAll(pageable).getContent();
        return MappingData.mapListObject(products, ProductResponse.class);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("delete product by id: " + id);
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
        this.productRepository.delete(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        log.info("update product by id: " + id);
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
        NullUtils.updateIfPresent(product::setName, productRequest.getName());
        NullUtils.updateIfPresent(product::setPrice, productRequest.getPrice());
        NullUtils.updateIfPresent(product::setQuantity, productRequest.getQuantity());
        NullUtils.updateIfPresent(product::setImageUrl, productRequest.getImageUrl());
        NullUtils.updateIfPresent(product::setType, productRequest.getProductType());
        NullUtils.updateIfPresent(product::setCategory, productRequest.getCategory());
        NullUtils.updateIfPresent(product::setUnit, productRequest.getUnit());
        NullUtils.updateIfPresent(product::setDescription, productRequest.getDescription());
        NullUtils.updateIfPresent(product::setStatus, productRequest.getStatus());
        NullUtils.updateIfPresent(product::setSupplier, product.getSupplier());
        product.setUpdatedDate(new Date());
        checkStatus(product);
        Product productUpdated = this.productRepository.save(product);
        return MappingData.mapObject(productUpdated, ProductResponse.class);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("create product");
        Product product = MappingData.mapObject(productRequest, Product.class);
        product.setType(productRequest.getProductType());
        product.setCreatedDate(new Date());
        checkStatus(product);
        return MappingData.mapObject(this.productRepository.save(product), ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(String category, int page, int size) {
        log.info("get list product by category");
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = this.productRepository.getProductsByCategory(category, pageable).getContent();
        return MappingData.mapListObject(products, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getProductsByType(String type, int page, int size) {
        log.info("get list product by type");
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = this.productRepository.getProductsByType(ProductType.valueOf(type.toUpperCase()), pageable).getContent();
        return MappingData.mapListObject(products, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> filterProductsByPriceBetween(Double from, Double to) {
        log.info("filter product by price between " + from + " and " + to);
        List<Product> products = this.productRepository.filterProductsByPriceBetween(from, to).orElse(null);
        return MappingData.mapListObject(products, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> searchByText(String text) {
        log.info("search product by text: " + text);
        if (text == null) {
            return getProducts(0, 10);
        }
        return MappingData.mapListObject(this.productRepository.getProductsByText(text), ProductResponse.class);
    }

    @Override
    public List<ProductResponse> searchByTextForSupplies(String text) {
        log.info("search supplies by text: " + text);
        if (text == null) {
            this.getProductsByCategory(String.valueOf(ProductType.SUPPLIES), 0, 10);
        }
        return MappingData.mapListObject(this.productRepository.getProductsByTextAndTypeIsSupplies(text), ProductResponse.class);
    }

    @Override
    public List<ProductResponse> searchByTextForService(String text) {
        log.info("search service by text: " + text);
        if (text == null) {
            this.getProductsByCategory(String.valueOf(ProductType.SUPPLIES), 0, 10);
        }
        return MappingData.mapListObject(this.productRepository.getProductsByTextAndTypeIsService(text), ProductResponse.class);
    }

    private void checkStatus(Product product) {
        if (product.getQuantity() > 0) {
            product.setStatus(Status.ACTIVE);
        } else {
            product.setStatus(Status.INACTIVE);
        }
    }
}
