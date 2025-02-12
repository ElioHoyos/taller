package com.example.demo.entity;

import com.example.demo.entity.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private DocumentType document_type;
    private String document_number;
    private String address;
    private String cellphone;
    private String email;
    private Boolean state;
    private LocalDate date_modified;
    private LocalDate date_created;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

}
