package com.example.demo.dao.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class ArticleRequestDao {
    private String code;
    private Long category_id;
    private String name;
    private String description;
    private Long amount;
    private BigDecimal purchase_price;
    private LocalDate expiration_date;
    private LocalDate date_modified;
    private LocalDate date_created;
    private Boolean state;
}
