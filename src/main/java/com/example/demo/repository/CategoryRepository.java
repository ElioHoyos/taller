package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    Optional<Category> findByNameAndIdNot(String name, Long id);

    // Método de paginación automático (ya incluido en JpaRepository)
    // Page<Category> findAll(Pageable pageable); // Este método ya no será la principal forma de obtener paginado si hay búsqueda

    // Versión con filtro por estado
    Page<Category> findByState(Boolean state, Pageable pageable);

    // AÑADIDO: Método para buscar categorías por nombre (ignorando mayúsculas/minúsculas) y paginar
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Opcional: Si quieres una búsqueda más compleja que incluya el estado, podrías hacer algo como:
    // Page<Category> findByNameContainingIgnoreCaseAndState(String name, Boolean state, Pageable pageable);
}