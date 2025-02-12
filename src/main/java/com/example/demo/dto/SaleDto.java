package com.example.demo.dto;

import com.example.demo.entity.enums.VoucherType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleDto {
    private Long id;
    private Long people_id;
    private String people_name;
    private Long user_id;
    private String user_name;
    @Enumerated(EnumType.STRING)
    private VoucherType voucher_type;
    private String voucher_series;
    private String voucher_number;
    private Locale sale_date;
    private BigDecimal igv;
    private BigDecimal total;
    private Boolean state;
    private LocalDate date_modified;
    private LocalDate date_created;
}
