package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.ProductRequest;
import com.se.kltn.spamanagement.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product listed"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @Operation(summary = "get list product")
    private ResponseEntity<Object> getProducts(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                               @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.productService.getProducts(page, size));
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product founded"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @Operation(summary = "find product by id")
    private ResponseEntity<Object> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product updated"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @Operation(summary = "update product")
    private ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok().body(this.productService.updateProduct(id, productRequest));
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product deleted"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @Operation(summary = "delete product by id")
    private ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok().body("Product deleted");
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @Operation(summary = "create product")
    @PostMapping
    private ResponseEntity<Object> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok().body(this.productService.createProduct(productRequest));
    }

    @GetMapping("/type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "products founded"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @Operation(summary = "find products by type")
    private ResponseEntity<Object> getProductsByType(@RequestParam("categoryName") String type,
                                                     @RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                                     @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.productService.getProductsByType(type, page, size));
    }

    @GetMapping("/category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "products founded"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @Operation(summary = "find products by category")
    private ResponseEntity<Object> getProductsByCategory(@RequestParam("categoryName") String categoryName,
                                                         @RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                                         @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.productService.getProductsByCategory(categoryName, page, size));
    }

    @GetMapping("/filter-price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "products founded"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @Operation(summary = "filter products by price")
    private ResponseEntity<Object> filterProductsByPriceBetween(@RequestParam("from") Double from, @RequestParam("to") Double to) {
        return ResponseEntity.ok().body(this.productService.filterProductsByPriceBetween(from, to));
    }

    @GetMapping("/search/text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "products founded"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @Operation(summary = "search product by text")
    public ResponseEntity<Object> findProductByText(@RequestParam(value = "text", required = false) String text) {
        return ResponseEntity.ok().body(this.productService.searchByText(text));
    }

    @GetMapping("/search/supplies/text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "devices founded"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @Operation(summary = "search supplies by text")
    public ResponseEntity<Object> findSuppliersByText(@RequestParam(value = "text", required = false) String text) {
        return ResponseEntity.ok().body(this.productService.searchByTextForSupplies(text));
    }
}
