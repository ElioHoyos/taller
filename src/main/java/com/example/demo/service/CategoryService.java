package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.request.CategoryRequestDao;
import com.example.demo.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> getCategories();
    Optional<Category> getCategory(Long id);
    void saveCategory(CategoryRequestDao categoryRequestDao);
    void updateCategory(CategoryRequestDao categoryRequestDao);
    void delete(Long id);
    void toggleState(Long id);

}
