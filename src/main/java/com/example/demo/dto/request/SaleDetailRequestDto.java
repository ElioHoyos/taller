package com.example.demo.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleDetailRequestDto {
    private Long id;
    private Long sale_id;
    private Long article_id;
    private Long user_id;
    private Long customer_id;
    private String sale_code;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal discount;
}
