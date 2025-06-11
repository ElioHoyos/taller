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
    // Page<Category> findAll(Pageable pageable); // No es necesario declararlo explícitamente, ya está en JpaRepository

    // Versión con filtro por estado (si se necesita en el futuro)
    Page<Category> findByState(Boolean state, Pageable pageable);

    // Nuevo método para buscar por nombre (parcial e insensible a mayúsculas/minúsculas)
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
}