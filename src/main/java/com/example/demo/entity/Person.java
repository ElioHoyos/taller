package com.example.demo.entity;
import com.example.demo.entity.enums.DocumentType;
import com.example.demo.entity.enums.PersonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "people")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PersonType type_person;
    private String name;
    @Enumerated(EnumType.STRING)
    private DocumentType document_type;
    private String document_number;
    private String cellphone;
    private String email;
    private String address;
    private Boolean state;
    private LocalDate date_modified;
    private LocalDate date_created;
}
