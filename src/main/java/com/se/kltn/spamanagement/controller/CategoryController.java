package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.CategoryRequest;
import com.se.kltn.spamanagement.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{name}")
    @Operation(summary = "get category by name")
    public ResponseEntity<Object> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok().body(this.categoryService.getCategoryByName(name));
    }

    @GetMapping
    @Operation(summary = "get list category")
    public ResponseEntity<Object> getCategories(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                                @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.categoryService.getAllCategory(page, size));
    }

    @PostMapping
    @Operation(summary = "create category")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok().body(this.categoryService.createCategory(categoryRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update category")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok().body(this.categoryService.updateCategory(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete category")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.ok().body("Category deleted");
    }
}
