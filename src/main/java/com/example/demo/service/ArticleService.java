package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.request.ArticleRequestDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ArticleService {
    Page<ArticleDto> getArticles(int page, int size, String search);
    Optional<ArticleDto> getArticle(Long id);
    Optional<ArticleDto> getByCode(String code);
    void saveArticleToCategory(ArticleRequestDto requestDao);
    Optional<ArticleDto> updateArticle(Long id, ArticleRequestDto articleDto);
    void deleteArticle(Long id);
}