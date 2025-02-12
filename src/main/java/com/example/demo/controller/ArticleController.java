package com.example.demo.controller;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.request.ArticleRequestDto;
import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/listArticle")
    public List<ArticleDto> getAllArticle(){
        return articleService.getArticles();
    }

    @GetMapping("/{Id}")
    public Optional<Article> getById(@PathVariable("Id") Long Id){
        return articleService.getArticle(Id);
    }

    @PostMapping
    public void saveArticleToCategory(@RequestBody ArticleRequestDto requestDao){
        articleService.saveArticleToCategory(requestDao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") Long id, @RequestBody ArticleDto articleDto) {
        Optional<ArticleDto> updatedArticle = articleService.updateArticle(id, articleDto);

        return updatedArticle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
