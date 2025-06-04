package com.example.demo.controller;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.request.CategoryRequestDao;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryView")
    public List<CategoryDto> getAllCategory(){
        return categoryService.getCategories();
    }

    @GetMapping("/{Id}")
    public Optional<Category> getById(@PathVariable("Id") Long Id){
        return categoryService.getCategory(Id);
    }

    @PutMapping
    public void updateCategory(@RequestBody CategoryRequestDao categoryRequestDao) {
        categoryService.updateCategory(categoryRequestDao);
    }

    @PostMapping
    public void saveCategory(@RequestBody CategoryRequestDao categoryRequestDao){
        categoryService.saveCategory(categoryRequestDao);
    }

    @PatchMapping("/{id}/toggle-state")
    public void toggleCategoryState(@PathVariable("id") Long id) {
        categoryService.toggleState(id);
    }

    @DeleteMapping("/{Id}")
    public void deleteCategory(@PathVariable("Id") Long Id){
        categoryService.delete(Id);
    }

}
