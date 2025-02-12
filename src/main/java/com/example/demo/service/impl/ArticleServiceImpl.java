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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @Override
    public List<ArticleDto> getArticles() {
        return articleRepository.findAll().stream()
                .map(article -> ArticleDto.builder()
                        .id(article.getId())
                        .category_id(article.getId())
                        .code(article.getCode())
                        .name(article.getName())
                        .description(article.getDescription())
                        .amount(article.getAmount())
                        .sale_price(article.getSale_price())
                        .purchase_price(article.getPurchase_price())
                        .expiration_date(article.getExpiration_date())
                        .state(article.getState()) // Este valor se usará para determinar el valor de getStateAsInt
                        .category_name(article.getCategory().getName()) // Obtener el nombre de la categoría
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> getArticle(Long Id) {
        return Optional.empty();
    }

    @Override
    public void saveArticleToCategory(ArticleRequestDto requestDao) {
//        List<NotFoundException> lisErrors = new ArrayList<>();
        Category category = categoryRepository.findById(requestDao.getCategory_id())
                .orElseThrow(() -> new NotFoundException(String.format("La categoria de producto con el id %s no existe", requestDao.getCategory_id())));
//                .orElseGet(() -> {
//                    lisErrors.add(new NotFoundException("El id Categoria no se encuentra :: "  + requestDao.getCategory_id()));
//                    return null;
//                });
//        Boolean defaultState = true; // O false, según lo que necesites
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
        return Optional.empty();
    }
}
