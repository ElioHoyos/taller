package com.example.demo.dto;

import com.example.demo.entity.enums.DocumentType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder @Getter @Setter
public class UserDto {
    private Long id;
    private String name;
    private DocumentType documentType;
    private String documentNumber;
    private String address;
    private String cellphone;
    private String email;
    private String login;
    private String image;
    private Boolean state;
    private LocalDate dateModified;
    private LocalDate dateCreated;

    // opcional: nombres de roles
    private List<String> roles;
}
