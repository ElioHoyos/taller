package com.example.demo.service.impl;

import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.CategoryRequestDao;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.validate.ValidateCategory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryDao> getCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryDao.builder()
                        .date_modified(category.getDate_modified())
                        .date_created(category.getDate_created())
                        .name(category.getName())
                        .state(category.getState())
                        .id(category.getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> getCategory(Long id) {
        return Optional.empty();
    }

    @Override
    public void saveCategory(CategoryRequestDao categoryRequestDao) {
        ValidateCategory.validateCategory(categoryRequestDao);
        categoryRepository.save(Category.builder()
                .name(categoryRequestDao.getName())
                .state(categoryRequestDao.getState())
                .date_modified(categoryRequestDao.getDate_modified())
                .date_created(categoryRequestDao.getDate_created())
                .build());
    }

    @Override
    public void delete(Long id) {

    }
}
