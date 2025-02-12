package com.example.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryRequestDao {

    private Long id;
    @NotEmpty(message = "El nombre de la categoria no puede estar vacía")
    private String name;
    private Boolean state;
}
