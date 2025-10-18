package com.example.demo.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class ArticleRequestDto {
    //@NotBlank(message = "El código es obligatorio")
    private String code;

    @NotNull(message = "La categoría es obligatoria")
    private Long category_id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "La cantidad es obligatoria")
    @PositiveOrZero(message = "La cantidad debe ser mayor o igual a cero")
    @Digits(integer = 10, fraction = 0, message = "La cantidad debe ser un número entero")
    private Long amount;

    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio de venta debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2, message = "El precio de venta debe tener máximo dos decimales")
    private BigDecimal sale_price;

    @NotNull(message = "El precio de compra es obligatorio")
    @Positive(message = "El precio de compra debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2, message = "El precio de compra debe tener máximo dos decimales")
    private BigDecimal purchase_price;

    @FutureOrPresent(message = "La fecha de expiración debe ser en el presente o futuro")
    private LocalDate expiration_date;
}