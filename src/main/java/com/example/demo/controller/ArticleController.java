package com.example.demo.controller;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.request.ArticleRequestDto;
import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Importar Page
import org.springframework.data.domain.Pageable; // Importar Pageable
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault; // Importar PageableDefault
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/article")
@Validated // Asegúrate de tener esto para validaciones si usas @Valid o @Validated en DTOs
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // NUEVO ENDPOINT: Paginado con búsqueda
    @GetMapping("/paged")
    public ResponseEntity<Page<ArticleDto>> getArticlesPaged(
            @PageableDefault(size = 10, sort = "dateCreated", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, defaultValue = "name") String searchBy) {

        // Validar campos de ordenamiento permitidos (opcional pero recomendado)
        if (!isValidArticleSortField(pageable.getSort().iterator().next().getProperty())) {
            return ResponseEntity.badRequest().body(null);
        }

        Page<ArticleDto> articles = articleService.getArticlesPaged(pageable, searchTerm, searchBy);
        return ResponseEntity.ok(articles);
    }


    // El antiguo endpoint /listArticle. Puedes mantenerlo si aún lo necesitas,
    // o eliminarlo y usar solo /paged para todas las consultas de lista.
    @GetMapping("/listArticle")
    public List<ArticleDto> getAllArticle(){
        return articleService.getArticles();
    }

    @GetMapping("/{Id}")
    public Optional<Article> getById(@PathVariable("Id") Long Id){
        return articleService.getArticle(Id);
    }

    @PostMapping
    public ResponseEntity<Void> saveArticleToCategory(@RequestBody ArticleRequestDto requestDao){
        articleService.saveArticleToCategory(requestDao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") Long id, @RequestBody ArticleDto articleDto) {
        Optional<ArticleDto> updatedArticle = articleService.updateArticle(id, articleDto);
        return updatedArticle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build(); // 204 No Content para eliminación exitosa
    }

    @PatchMapping("/{id}/toggle-state")
    public ResponseEntity<Void> toggleArticleState(@PathVariable("id") Long id) {
        articleService.toggleArticleState(id);
        return ResponseEntity.ok().build();
    }

    // Método para validar campos de ordenamiento permitidos para artículos
    private boolean isValidArticleSortField(String sortField) {
        return List.of("id", "code", "name", "amount", "sale_price", "purchase_price",
                        "expiration_date", "date_modified", "date_created", "state")
                .contains(sortField);
    }
}