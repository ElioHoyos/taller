package com.example.demo.controller;

import com.example.demo.dao.request.ArticleRequestDao;
import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/viewArticle")
    public List<ArticleRequestDao> getAllByArticle(){
        return articleService.getArticles();
    }

    @GetMapping("/{Id}")
    public Optional<Article> getById(@PathVariable("Id") Long Id){
        return articleService.getArticle(Id);
    }

    @PostMapping("/save")
    public void saveArticleToCategory(@RequestBody ArticleRequestDao requestDao){
        articleService.saveArticleToCategory(requestDao);
    }

}
