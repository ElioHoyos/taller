package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByDocumentTypeAndDocumentNumber(DocumentType documentType, String documentNumber);
    boolean existsByDocumentTypeAndDocumentNumberAndIdNot(DocumentType documentType, String documentNumber, Long id);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByLogin(String login);
    boolean existsByLoginAndIdNot(String login, Long id);

    Optional<User> findByLogin(String login);
}
