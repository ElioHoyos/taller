package com.example.demo.controller;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.request.CategoryRequestDao;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/category")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Endpoint paginado con Pageable (manteniendo por si se usa sin searchTerm)
    @GetMapping("/paged")
    public ResponseEntity<Page<CategoryDto>> getCategoriesPaged(
            @PageableDefault(size = 10, sort = "dateCreated", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CategoryDto> categories = categoryService.getCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    // Endpoint sin paginación (manteniendo por si se usa sin paginación)
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // Endpoint paginado con parámetros individuales y nuevo parámetro searchTerm
    @GetMapping("/categoryView")
    public ResponseEntity<Page<CategoryDto>> getCategoriesPagedWithParams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCreated") String sort,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(required = false) String searchTerm) { // Nuevo parámetro de búsqueda

        // Validar campos de ordenamiento permitidos
        if (!isValidSortField(sort)) {
            throw new IllegalArgumentException("Campo de ordenamiento inválido: " + sort);
        }

        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
        Page<CategoryDto> categories = categoryService.getCategories(searchTerm, pageable); // Pasar searchTerm al servicio
        return ResponseEntity.ok(categories);
    }

    // Método para validar campos de ordenamiento (sin cambios)
    private boolean isValidSortField(String sort) {
        return List.of("id", "name", "state", "dateCreated", "dateModified")
                .contains(sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") Long id) {
        Optional<Category> category = categoryService.getCategory(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public void updateCategory(@RequestBody CategoryRequestDao categoryRequestDao) {
        categoryService.updateCategory(categoryRequestDao);
    }

    @PostMapping
    public void saveCategory(@RequestBody CategoryRequestDao categoryRequestDao) {
        categoryService.saveCategory(categoryRequestDao);
    }

    @PatchMapping("/{id}/toggle-state")
    public void toggleCategoryState(@PathVariable("id") Long id) {
        categoryService.toggleState(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}