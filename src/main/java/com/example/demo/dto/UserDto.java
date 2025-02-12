package com.example.demo.dto;

import com.example.demo.entity.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private Long rol_id;
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
}
