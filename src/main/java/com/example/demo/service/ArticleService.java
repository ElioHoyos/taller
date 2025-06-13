package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.request.ArticleRequestDto;
import com.example.demo.entity.Article;
import org.springframework.data.domain.Page; // Importar Page
import org.springframework.data.domain.Pageable; // Importar Pageable

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    // Cambiado: Ahora acepta Pageable, searchTerm y searchBy, y devuelve Page<ArticleDto>
    Page<ArticleDto> getArticlesPaged(Pageable pageable, String searchTerm, String searchBy);

    // Si aún necesitas una lista sin paginar, puedes mantener este, o eliminarlo si no se usa
    List<ArticleDto> getArticles();

    Optional<Article> getArticle(Long Id);
    void saveArticleToCategory(ArticleRequestDto requestDao);
    Optional<ArticleDto> updateArticle(Long id, ArticleDto articleDto);
    void deleteArticle(Long id); // Añadir método para eliminar
    void toggleArticleState(Long id); // Añadir método para cambiar estado
}