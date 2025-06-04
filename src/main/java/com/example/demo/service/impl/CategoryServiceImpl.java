package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.request.CategoryRequestDao;
import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryNameEmptyException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
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
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryDto.builder()
                        .date_modified(category.getDate_modified())
                        .date_created(category.getDate_created())
                        .name(category.getName())
                        .state(category.getState()) // Conversión a texto
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
        //Boolean defaultState = true; // O false, según lo que necesites
        categoryRepository.save(Category.builder()
                .name(categoryRequestDao.getName())
                .state(Boolean.TRUE)
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .build());
    }

    @Override
    public void updateCategory(CategoryRequestDao categoryRequestDao) {
        // Validar ID
        if (categoryRequestDao.getId() == null) {
            throw new IllegalArgumentException("ID de categoría no proporcionado");
        }

        // Buscar categoría existente
        Category existingCategory = categoryRepository.findById(categoryRequestDao.getId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        // Validar nombre
        if (categoryRequestDao.getName() == null || categoryRequestDao.getName().trim().isEmpty()) {
            throw new CategoryNameEmptyException("El nombre de la categoría no debe estar vacío");
        }

        // Verificar si el nuevo nombre ya existe en otra categoría
        Optional<Category> duplicateCategory = categoryRepository.findByNameAndIdNot(
                categoryRequestDao.getName(),
                categoryRequestDao.getId()
        );

        if (duplicateCategory.isPresent()) {
            throw new CategoryNameEmptyException.CategoryNameDuplicateException(
                    "El nombre de la categoría ya existe en otra categoría"
            );
        }

        // Actualizar campos
        existingCategory.setName(categoryRequestDao.getName());
        existingCategory.setState(categoryRequestDao.getState());
        existingCategory.setDate_modified(LocalDate.now());

        categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Long id) {
        // Verificar si la categoría existe
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        categoryRepository.delete(category);
    }

    @Override
    public void toggleState(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        // Cambiar estado (activo/inactivo)
        category.setState(!category.getState());
        category.setDate_modified(LocalDate.now());

        categoryRepository.save(category);
    }
    }

