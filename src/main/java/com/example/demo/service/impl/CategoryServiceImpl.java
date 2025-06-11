package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.request.CategoryRequestDao;
import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryNameEmptyException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryDto> getCategories(Pageable pageable) {
        // Usa el método que tiene término de búsqueda, pasando null para obtener todas
        return getCategories(null, pageable);
    }

    @Override
    public Page<CategoryDto> getCategories(String searchTerm, Pageable pageable) {
        Page<Category> categoriesPage;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            categoriesPage = categoryRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
        } else {
            categoriesPage = categoryRepository.findAll(pageable); // findAll de JpaRepository para paginación sin filtro
        }
        return convertToDtoPage(categoriesPage, pageable);
    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Page<CategoryDto> convertToDtoPage(Page<Category> categoriesPage, Pageable pageable) {
        List<CategoryDto> categoryDtos = categoriesPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(categoryDtos, pageable, categoriesPage.getTotalElements());
    }

    private CategoryDto convertToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .state(category.getState())
                .dateCreated(category.getDateCreated())
                .dateModified(category.getDateModified())
                .build();
    }

    @Override
    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void saveCategory(CategoryRequestDao categoryRequestDao) {
        if (categoryRequestDao.getName() == null || categoryRequestDao.getName().trim().isEmpty()) {
            throw new CategoryNameEmptyException("El nombre de la categoría no debe estar vacío");
        }

        if (categoryRepository.findByName(categoryRequestDao.getName()).isPresent()) {
            throw new CategoryNameEmptyException.CategoryNameDuplicateException("El nombre de la categoría ya existe");
        }

        categoryRepository.save(Category.builder()
                .name(categoryRequestDao.getName())
                .state(Boolean.TRUE)
                .dateModified(LocalDate.now())
                .dateCreated(LocalDate.now())
                .build());
    }

    @Override
    public void updateCategory(CategoryRequestDao categoryRequestDao) {
        if (categoryRequestDao.getId() == null) {
            throw new IllegalArgumentException("ID de categoría no proporcionado");
        }

        Category existingCategory = categoryRepository.findById(categoryRequestDao.getId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        if (categoryRequestDao.getName() == null || categoryRequestDao.getName().trim().isEmpty()) {
            throw new CategoryNameEmptyException("El nombre de la categoría no debe estar vacío");
        }

        Optional<Category> duplicateCategory = categoryRepository.findByNameAndIdNot(
                categoryRequestDao.getName(),
                categoryRequestDao.getId()
        );

        if (duplicateCategory.isPresent()) {
            throw new CategoryNameEmptyException.CategoryNameDuplicateException(
                    "El nombre de la categoría ya existe en otra categoría"
            );
        }

        existingCategory.setName(categoryRequestDao.getName());
        existingCategory.setState(categoryRequestDao.getState());
        existingCategory.setDateModified(LocalDate.now());

        categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        categoryRepository.delete(category);
    }

    @Override
    public void toggleState(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        category.setState(!category.getState());
        category.setDateModified(LocalDate.now());

        categoryRepository.save(category);
    }
}