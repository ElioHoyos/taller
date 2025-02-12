package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.request.ArticleRequestDto;
import com.example.demo.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<ArticleDto> getArticles();
    Optional<Article> getArticle(Long Id);
    void saveArticleToCategory(ArticleRequestDto requestDao);
    Optional<ArticleDto> updateArticle(Long id, ArticleDto articleDto);
}
