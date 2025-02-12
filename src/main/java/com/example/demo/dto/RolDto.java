package com.example.demo.dto;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Builder
public class RolDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private LocalDate date_modified;
    private LocalDate date_created;
}
