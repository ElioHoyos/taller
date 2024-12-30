package com.example.demo.controller;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.CategoryRequestDao;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<CategoryDao> getAllCategory(){
        return categoryService.getCategories();
    }

    @GetMapping("/{Id}")
    public Optional<Category> getById(@PathVariable("Id") Long Id){
        return categoryService.getCategory(Id);
    }

    @PostMapping
    public void saveCategory(@RequestBody CategoryRequestDao categoryRequestDao){
        categoryService.saveCategory(categoryRequestDao);
    }

    @DeleteMapping("/{Id}")
    public void deleteCategory(@PathVariable("Id") Long Id){
        categoryService.delete(Id);
    }

}
