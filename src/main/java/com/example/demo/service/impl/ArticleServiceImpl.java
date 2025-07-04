package com.example.demo.service.impl;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.request.ArticleRequestDto;
import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación del servicio para gestión de artículos.
 *
 * Buenas prácticas implementadas:
 * 1. Separación clara de capas (servicio vs. controlador)
 * 2. Validación centralizada de reglas de negocio
 * 3. Patrón DTO para transferencia de datos
 * 4. Manejo transaccional (@Transactional)
 * 5. Inyección de dependencias por constructor (Lombok @RequiredArgsConstructor)
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Obtiene artículos paginados con opción de búsqueda.
     *
     * Buenas prácticas:
     * - Paginación para mejor rendimiento
     * - Mapeo entidad-DTO separado
     */
    @Override
    public Page<ArticleDto> getArticles(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles;

        if (search != null && !search.isEmpty()) {
            // Búsqueda flexible por nombre o código
            articles = articleRepository.findByNameContainingOrCodeContaining(search, search, pageable);
        } else {
            // Todos los artículos paginados
            articles = articleRepository.findAll(pageable);
        }

        return articles.map(this::convertToDto);
    }

    /**
     * Obtiene un artículo por ID.
     *
     * Buenas prácticas:
     * - Uso de Optional para manejo seguro de valores nulos
     */
    @Override
    public Optional<ArticleDto> getArticle(Long id) {
        return articleRepository.findById(id)
                .map(this::convertToDto);
    }

    /**
     * Crea un nuevo artículo.
     *
     * Buenas prácticas:
     * - Validación completa antes de operaciones
     * - Manejo transaccional
     * - Construcción separada de entidades
     */
    @Override
    @Transactional
    public void saveArticleToCategory(ArticleRequestDto requestDto) {
        Map<String, String> errors = new HashMap<>();
        validateFields(requestDto, errors);
        validateBusinessRules(requestDto, errors, null);

        if (!errors.isEmpty()) {
            // Lanzar excepción con todos los errores
            throw new ValidationException(errors);
        }

        Category category = categoryRepository.findById(requestDto.getCategory_id())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        Article article = buildArticle(requestDto, category);
        articleRepository.save(article);
    }

    /**
     * Actualiza un artículo existente.
     *
     * Buenas prácticas:
     * - Validación de existencia
     * - Actualización selectiva de campos
     */
    @Override
    @Transactional
    public Optional<ArticleDto> updateArticle(Long id, ArticleRequestDto requestDto) {
        Map<String, String> errors = new HashMap<>();
        validateFields(requestDto, errors);
        validateBusinessRules(requestDto, errors, id);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        return articleRepository.findById(id)
                .map(article -> {
                    updateArticleFields(article, requestDto);
                    return convertToDto(articleRepository.save(article));
                });
    }

    /**
     * Eliminación lógica de artículo (soft delete).
     *
     * Buenas prácticas:
     * - No eliminación física para mantener integridad histórica
     * - Cambio de estado en lugar de borrado
     */
    @Override
    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Artículo no encontrado"));
        article.setState(false);
        article.setDate_modified(LocalDate.now());
        articleRepository.save(article);
    }

    /**
     * Convierte entidad Article a DTO.
     *
     * Buenas prácticas:
     * - Separación entre entidad de persistencia y DTO
     * - Mapeo explícito de campos
     */
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
                .date_created(article.getDate_created())
                .date_modified(article.getDate_modified())
                .state(article.getState())
                .build();
    }

    /**
     * Valida campos básicos del DTO.
     *
     * Buenas prácticas:
     * - Validación centralizada
     * - Acumulación de múltiples errores
     */
    private void validateFields(ArticleRequestDto dto, Map<String, String> errors) {
        // Validación de código
        if (dto.getCode() == null || dto.getCode().trim().isEmpty()) {
            errors.put("codigo producto", "El código es obligatorio");
        }

        // Validación de cantidad
        if (dto.getAmount() == null) {
            errors.put("cantidad producto", "La cantidad es obligatoria");
        } else if (dto.getAmount() < 0) {
            errors.put("cantidad producto", "No se aceptan negativos; la cantidad debe ser mayor a 0");
        }

        // Validación de precio de compra
        if (dto.getPurchase_price() == null) {
            errors.put("Precio de compra", "El precio de compra es obligatorio");
        } else if (dto.getPurchase_price().compareTo(BigDecimal.ZERO) <= 0) {
            errors.put("Precio de compra", "El precio de compra debe ser mayor a 0");
        }

        // Validación de precio de venta
        if (dto.getSale_price() == null) {
            errors.put("Precio de venta", "El precio de venta es obligatorio");
        } else if (dto.getSale_price().compareTo(BigDecimal.ZERO) <= 0) {
            errors.put("Precio de venta", "El precio de venta debe ser mayor a 0");
        }

        // Validación de relación entre precios
        if (dto.getPurchase_price() != null && dto.getSale_price() != null &&
                dto.getSale_price().compareTo(dto.getPurchase_price()) <= 0) {
            errors.put("Precio de venta", "Debe ser mayor que el precio de compra");
        }
    }

    /**
     * Valida reglas de negocio.
     *
     * Buenas prácticas:
     * - Validación de unicidad
     * - Verificación de existencia de relaciones
     */
    private void validateBusinessRules(ArticleRequestDto dto, Map<String, String> errors, Long id) {
        // Validar unicidad de código
        if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
            boolean codeExists = id == null ?
                    articleRepository.existsByCode(dto.getCode()) :
                    articleRepository.existsByCodeAndIdNot(dto.getCode(), id);

            if (codeExists) {
                errors.put("codigo producto", "El código del artículo ya existe");
            }
        }

        // Validar existencia de categoría
        if (dto.getCategory_id() != null && !categoryRepository.existsById(dto.getCategory_id())) {
            errors.put("categoría", "La categoría especificada no existe");
        }
    }

    /**
     * Construye entidad Article desde DTO.
     *
     * Buenas prácticas:
     * - Separación de construcción
     * - Inicialización de campos automáticos (fechas, estado)
     */
    private Article buildArticle(ArticleRequestDto dto, Category category) {
        return Article.builder()
                .category(category)
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .sale_price(dto.getSale_price())
                .purchase_price(dto.getPurchase_price())
                .expiration_date(dto.getExpiration_date())
                .date_created(LocalDate.now())
                .date_modified(LocalDate.now())
                .state(true)
                .build();
    }

    /**
     * Actualiza campos de entidad desde DTO.
     *
     * Buenas prácticas:
     * - Actualización selectiva
     * - Manejo de relaciones
     */
    private void updateArticleFields(Article article, ArticleRequestDto dto) {
        article.setCode(dto.getCode());
        article.setName(dto.getName());
        article.setDescription(dto.getDescription());
        article.setAmount(dto.getAmount());
        article.setSale_price(dto.getSale_price());
        article.setPurchase_price(dto.getPurchase_price());
        article.setExpiration_date(dto.getExpiration_date());
        article.setDate_modified(LocalDate.now());

        // Actualizar categoría solo si es necesario
        if (dto.getCategory_id() != null) {
            Category category = categoryRepository.findById(dto.getCategory_id())
                    .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
            article.setCategory(category);
        }
    }
}