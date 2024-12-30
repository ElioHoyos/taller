package com.example.demo.dao;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Builder
public class CategoryDao implements Serializable {

    private Long id;
    private String name;
    private String state;
    private LocalDate date_modified;
    private LocalDate date_created;
}
