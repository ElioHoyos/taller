package com.example.demo.controller;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.request.ArticleRequestDto;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<Page<ArticleDto>> getAllArticle(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "date_created") String sort,   // si quisieras
            @RequestParam(defaultValue = "DESC") String direction) {     // si quisieras
        return ResponseEntity.ok(articleService.getArticles(page, size, search /*, sort, direction*/));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getById(@PathVariable Long id) {
        return articleService.getArticle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ArticleDto> getByCode(@PathVariable String code) {
        return articleService.getByCode(code.trim())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@RequestBody ArticleRequestDto requestDao) {
        articleService.saveArticleToCategory(requestDao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(
            @PathVariable Long id,
            @RequestBody ArticleRequestDto articleDto) {

        return articleService.updateArticle(id, articleDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //reactivar/desactivar.
    @PatchMapping("/{id}/toggle-state")
    public ResponseEntity<ArticleDto> toggleState(@PathVariable Long id) {
        return articleService.toggleState(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/generate-code")
    public ResponseEntity<Map<String, String>> generateCode() {
        String code = articleService.generateNewCode();
        return ResponseEntity.ok(Map.of("code", code));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}