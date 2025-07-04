package com.example.demo.repository;

import com.example.demo.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para entidades Article.
 *
 * Buenas prácticas implementadas:
 * 1. Herencia de JpaRepository para operaciones CRUD estándar
 * 2. Métodos de consulta derivados del nombre (query methods)
 * 3. Paginación nativa de Spring Data
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * Busca artículos por nombre o código (búsqueda insensible a mayúsculas/minúsculas).
     *
     * Buenas prácticas:
     * - Consulta paginada para mejor rendimiento con grandes volúmenes de datos
     * - Uso de operadores Containing para búsquedas parciales
     */
    Page<Article> findByNameContainingOrCodeContaining(String name, String code, Pageable pageable);

    /**
     * Verifica si existe un artículo con el código dado.
     *
     * Buenas prácticas:
     * - Validación eficiente de unicidad
     */
    boolean existsByCode(String code);

    /**
     * Verifica si existe otro artículo con el mismo código (excluyendo el actual).
     *
     * Buenas prácticas:
     * - Evita duplicados en actualizaciones
     * - Consulta optimizada para operaciones de actualización
     */
    boolean existsByCodeAndIdNot(String code, Long id);
}