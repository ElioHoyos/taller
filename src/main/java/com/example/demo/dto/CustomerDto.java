package com.example.demo.dto;

import com.example.demo.entity.enums.DocumentType;
import com.example.demo.entity.enums.PersonType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CustomerDto {
    private Long id;
    @Enumerated(EnumType.STRING)
    private PersonType type_person;
    private String name;
    @Enumerated(EnumType.STRING)
    private DocumentType document_type;
    private Long document_number;
    private String cellphone;
    private String email;
    private String address;
    private Boolean state;
    private LocalDate date_modified;
    private LocalDate date_created;
}
