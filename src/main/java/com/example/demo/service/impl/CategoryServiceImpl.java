package com.example.demo.service.impl;

import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.CategoryRequestDao;
import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryNameEmptyException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.validate.ValidateCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
                        .state(category.getState() != null && category.getState() ? "Categoria Activo" : "Categoria Inactivo") // Conversión a texto
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
        //ValidateCategory.validateCategory(categoryRequestDao);
        // Verificar si el nombre de la categoría ya existe
        if (categoryRepository.findByName(categoryRequestDao.getName()).isPresent()) {
            throw new CategoryNameEmptyException("El nombre de la categoría ya existe");
        }
        // Validar que el nombre no esté vacío
        if (categoryRequestDao.getName() == null || categoryRequestDao.getName().trim().isEmpty()) {
            throw new CategoryNameEmptyException("El nombre de la categoría no debe estar vacío");
        }
        Boolean defaultState = true; // O false, según lo que necesites
        categoryRepository.save(Category.builder()
                .name(categoryRequestDao.getName())
                .state(defaultState)
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .build());
    }

    @Override
    public void delete(Long id) {

    }
}
