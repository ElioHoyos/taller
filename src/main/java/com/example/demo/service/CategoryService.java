package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.request.CategoryRequestDao;
import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    // Método para paginación sin término de búsqueda (original)
    Page<CategoryDto> getCategories(Pageable pageable);

    // Nuevo método para paginación con término de búsqueda
    Page<CategoryDto> getCategories(String searchTerm, Pageable pageable);

    List<CategoryDto> getCategories(); // Método para obtener todas sin paginación
    Optional<Category> getCategory(Long id);
    void saveCategory(CategoryRequestDao categoryRequestDao);
    void updateCategory(CategoryRequestDao categoryRequestDao);
    void delete(Long id);
    void toggleState(Long id);

}