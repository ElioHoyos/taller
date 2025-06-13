package com.example.demo.repository;

import com.example.demo.entity.Article;
import org.springframework.data.domain.Page; // Importar Page
import org.springframework.data.domain.Pageable; // Importar Pageable
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Métodos de búsqueda para paginación
    Page<Article> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Article> findByCodeContainingIgnoreCase(String code, Pageable pageable);
    Page<Article> findByCategory_NameContainingIgnoreCase(String categoryName, Pageable pageable);
    Page<Article> findByState(Boolean state, Pageable pageable); // Para buscar por estado

    // Métodos para validaciones de duplicidad (si los necesitas para Article)
    Optional<Article> findByCode(String code);
    Optional<Article> findByCodeAndIdNot(String code, Long id);
    Optional<Article> findByName(String name);
    Optional<Article> findByNameAndIdNot(String name, Long id);
}