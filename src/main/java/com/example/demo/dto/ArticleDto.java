package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto implements Serializable {
    private Long id;
    private String code;
    private Long category_id;
    private String category_name;
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
