package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Builder
public class CategoryDto implements Serializable {

    private Long id;
    private String name;
    private Boolean state;
    private LocalDate date_modified;
    private LocalDate date_created;
}
