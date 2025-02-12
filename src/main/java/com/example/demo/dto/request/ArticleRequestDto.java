package com.example.demo.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class ArticleRequestDto {
    private Long id;
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
    private Boolean state;
}
