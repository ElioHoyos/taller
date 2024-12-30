package com.example.demo.service.impl;

import com.example.demo.dao.request.ArticleRequestDao;
import com.example.demo.entity.Category;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Service
@RestControllerAdvice
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void saveArticleToCategory(ArticleRequestDao requestDao) {

    }
}
