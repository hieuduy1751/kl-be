package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.CategoryRequest;
import com.se.kltn.spamanagement.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getCategoryByName(String name);

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);

    List<CategoryResponse> getAllCategory(int page, int size);
}
