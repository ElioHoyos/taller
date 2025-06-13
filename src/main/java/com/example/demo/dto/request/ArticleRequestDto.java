package com.example.demo.dto.request;

import lombok.AllArgsConstructor; // Añadido
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor; // Añadido
import lombok.Setter; // Añadido para mutabilidad

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter // Permite establecer valores para la actualización
@AllArgsConstructor // Permite constructor con todos los argumentos para @Builder
@NoArgsConstructor // Necesario para deserialización de JSON
public class ArticleRequestDto {
    private Long id; // Necesario para operaciones de actualización
    private String code;
    private Long category_id;
    private String name;
    private String description;
    private Long amount;
    private BigDecimal sale_price;
    private BigDecimal purchase_price;
    private LocalDate expiration_date;
    private LocalDate date_modified;
    private LocalDate date_created;
    private Boolean state; // Necesario para el estado inicial o actualización de estado junto con otros campos
}