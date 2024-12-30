package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String amount;
    private String purchase_price;
    private LocalDate expiration_date;
    private Boolean state;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
