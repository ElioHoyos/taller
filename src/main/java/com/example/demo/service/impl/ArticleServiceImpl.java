package com.example.demo.service.impl;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.request.ArticleRequestDto;
import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Importar Page
import org.springframework.data.domain.Pageable; // Importar Pageable
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Método de conversión a DTO
    private ArticleDto convertToDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .category_id(article.getCategory().getId())
                .category_name(article.getCategory().getName())
                .code(article.getCode())
                .name(article.getName())
                .description(article.getDescription())
                .amount(article.getAmount())
                .sale_price(article.getSale_price())
                .purchase_price(article.getPurchase_price())
                .expiration_date(article.getExpiration_date())
                .date_modified(article.getDate_modified())
                .date_created(article.getDate_created())
                .state(article.getState())
                .build();
    }

    // NUEVO MÉTODO: Paginación y búsqueda para artículos
    @Override
    public Page<ArticleDto> getArticlesPaged(Pageable pageable, String searchTerm, String searchBy) {
        Page<Article> articlePage;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            switch (searchBy) {
                case "name":
                    articlePage = articleRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
                    break;
                case "code":
                    articlePage = articleRepository.findByCodeContainingIgnoreCase(searchTerm, pageable);
                    break;
                case "category_name":
                    // Asumiendo que Article tiene una relación con Category y Category tiene un campo 'name'
                    articlePage = articleRepository.findByCategory_NameContainingIgnoreCase(searchTerm, pageable);
                    break;
                case "state":
                    try {
                        Boolean stateValue = Boolean.parseBoolean(searchTerm);
                        articlePage = articleRepository.findByState(stateValue, pageable);
                    } catch (IllegalArgumentException e) {
                        articlePage = Page.empty(pageable); // Manejar búsqueda inválida por estado
                    }
                    break;
                default: // Por defecto, buscar por nombre si no se especifica o es inválido
                    articlePage = articleRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
                    break;
            }
        } else {
            articlePage = articleRepository.findAll(pageable);
        }
        return articlePage.map(this::convertToDto);
    }

    @Override
    public List<ArticleDto> getArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDto) // Usar el método de conversión
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> getArticle(Long Id) {
        return articleRepository.findById(Id);
    }

    @Override
    public void saveArticleToCategory(ArticleRequestDto requestDao) {
        Category category = categoryRepository.findById(requestDao.getCategory_id())
                .orElseThrow(() -> new NotFoundException(String.format("La categoria de producto con el id %s no existe", requestDao.getCategory_id())));

        Article article = Article.builder()
                .category(category)
                .code(requestDao.getCode())
                .name(requestDao.getName())
                .description(requestDao.getDescription())
                .amount(requestDao.getAmount())
                .sale_price(requestDao.getSale_price())
                .purchase_price(requestDao.getPurchase_price())
                .expiration_date(requestDao.getExpiration_date())
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .state(Boolean.TRUE)
                .build();
        articleRepository.save(article);
    }

    @Override
    public Optional<ArticleDto> updateArticle(Long id, ArticleDto articleDto) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Artículo no encontrado con ID: " + id));

        Category category = categoryRepository.findById(articleDto.getCategory_id())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + articleDto.getCategory_id()));

        // Actualizar campos
        existingArticle.setCategory(category);
        existingArticle.setCode(articleDto.getCode());
        existingArticle.setName(articleDto.getName());
        existingArticle.setDescription(articleDto.getDescription());
        existingArticle.setAmount(articleDto.getAmount());
        existingArticle.setSale_price(articleDto.getSale_price());
        existingArticle.setPurchase_price(articleDto.getPurchase_price());
        existingArticle.setExpiration_date(articleDto.getExpiration_date());
        existingArticle.setState(articleDto.getState());
        existingArticle.setDate_modified(LocalDate.now());

        Article updatedArticle = articleRepository.save(existingArticle);
        return Optional.of(convertToDto(updatedArticle));
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Artículo no encontrado con ID: " + id));
        articleRepository.delete(article);
    }

    @Override
    public void toggleArticleState(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Artículo no encontrado con ID: " + id));
        article.setState(!article.getState());
        article.setDate_modified(LocalDate.now());
        articleRepository.save(article);
    }
}