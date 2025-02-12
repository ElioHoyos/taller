package com.example.demo.dto;


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
public class SaleDetailDto {
    private Long id;
    private Long sale_id;
    private String voucher_type;
    private String voucher_series;
    private String voucher_number;
    private LocalDate sale_date;
    private BigDecimal igv;
    private BigDecimal total;
    private Boolean state;
    private Long article_id;
    private String name;
    private BigDecimal purchase_price;
    private BigDecimal sale_price;
    private Long amount;
    private BigDecimal discount;

}
