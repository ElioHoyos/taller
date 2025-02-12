package com.example.demo.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class RolRequestDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate date_modified;
    private LocalDate date_created;
}
