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
import org.springframework.data.web.PageableDefault; // Import added
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Import added
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

    // Endpoint paginado con Pageable
    @GetMapping("/paged")
    public ResponseEntity<Page<CategoryDto>> getCategoriesPaged(
            @PageableDefault(size = 10, sort = "dateCreated", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CategoryDto> categories = categoryService.getCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    // Endpoint sin paginación
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // Endpoint paginado con parámetros individuales
    @GetMapping("/categoryView")
    public ResponseEntity<Page<CategoryDto>> getCategoriesPagedWithParams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCreated") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        // Validar campos de ordenamiento permitidos
        if (!isValidSortField(sort)) {
            // Returning BAD_REQUEST for invalid sort field
            return ResponseEntity.badRequest().build();
        }

        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
        Page<CategoryDto> categories = categoryService.getCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    // Método para validar campos de ordenamiento
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
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryRequestDao categoryRequestDao) {
        categoryService.updateCategory(categoryRequestDao);
        return ResponseEntity.ok().build(); // Explicitly return 200 OK
    }

    @PostMapping
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryRequestDao categoryRequestDao) {
        categoryService.saveCategory(categoryRequestDao);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Explicitly return 201 Created
    }

    @PatchMapping("/{id}/toggle-state")
    public ResponseEntity<Void> toggleCategoryState(@PathVariable("id") Long id) {
        categoryService.toggleState(id);
        return ResponseEntity.ok().build(); // Explicitly return 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build(); // Explicitly return 204 No Content
    }
}