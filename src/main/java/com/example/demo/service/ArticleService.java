package com.example.demo.service;

import com.example.demo.dao.request.ArticleRequestDao;
import com.example.demo.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<ArticleRequestDao> getArticles();
    Optional<Article> getArticle(Long Id);
    void saveArticleToCategory(ArticleRequestDao requestDao);

}
