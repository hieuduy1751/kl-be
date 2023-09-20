package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.ProductRequest;
import com.se.kltn.spamanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    private ResponseEntity<Object> getProducts(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.ok().body(this.productService.getProducts(pageable));
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.productService.getProductById(id));
    }

    @PostMapping("/{id}")
    private ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok().body(this.productService.updateProduct(id, productRequest));
    }

    @DeleteMapping("{/id}")
    private ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok().body("Product deleted");
    }
}
