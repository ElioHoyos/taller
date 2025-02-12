package com.example.demo.dto.request;

import com.example.demo.entity.enums.DocumentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class UserRequestDto {
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
