package com.example.demo.service;

import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.CategoryRequestDao;
import com.example.demo.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDao> getCategories();
    Optional<Category> getCategory(Long id);
    void saveCategory(CategoryRequestDao categoryRequestDao);
    void delete(Long id);

}
