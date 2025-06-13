package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.request.CategoryRequestDao;
import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    // Método con paginación
    Page<CategoryDto> getCategories(Pageable pageable);

    List<CategoryDto> getCategories();
    Optional<Category> getCategory(Long id);
    void saveCategory(CategoryRequestDao categoryRequestDao);
    void updateCategory(CategoryRequestDao categoryRequestDao);
    void delete(Long id);
    void toggleState(Long id);

    // NUEVO: Método para buscar categorías por nombre (para el buscador en tiempo real)
    Page<CategoryDto> searchCategoriesByName(String name, Pageable pageable);
}