package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long amount;
    private BigDecimal purchase_price;
    private LocalDate expiration_date;
    private LocalDate date_modified;
    private LocalDate date_created;
    private Boolean state;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
