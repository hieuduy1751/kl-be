package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.ErrorMessage;
import com.se.kltn.spamanagement.dto.request.CategoryRequest;
import com.se.kltn.spamanagement.dto.response.CategoryResponse;
import com.se.kltn.spamanagement.exception.BadRequestException;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Category;
import com.se.kltn.spamanagement.repository.CategoryRepository;
import com.se.kltn.spamanagement.service.CategoryService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.CATEGORY_NOT_FOUND;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        return MappingData.mapObject(this.categoryRepository.findCategoryByName(name), CategoryResponse.class);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = MappingData.mapObject(categoryRequest, Category.class);
        if (this.categoryRepository.findCategoryByName(category.getName()) != null) {
            throw new BadRequestException(ErrorMessage.CATEGORY_EXISTED);
        }
        return MappingData.mapObject(this.categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));
        NullUtils.updateIfPresent(category::setName, categoryRequest.getName());
        NullUtils.updateIfPresent(category::setDescription, categoryRequest.getDescription());
        return MappingData.mapObject(this.categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));
        this.categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> getAllCategory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Category> categories = this.categoryRepository.findAll(pageable).getContent();
        return MappingData.mapListObject(categories, CategoryResponse.class);
    }
}
