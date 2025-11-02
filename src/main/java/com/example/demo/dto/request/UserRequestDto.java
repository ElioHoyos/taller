package com.example.demo.dto.request;

import com.example.demo.entity.enums.DocumentType;
import lombok.*;

import java.time.LocalDate;

@Builder @Getter @Setter
public class UserRequestDto {
    private String name;
    private DocumentType documentType;
    private String documentNumber;
    private String address;
    private String cellphone;
    private String email;
    private String login;      // requerido al crear
    private String password;   // claro -> se hace hash
    private String weight;
    private LocalDate birthdayDate;
}
