package com.example.demo.service;

import com.example.demo.dto.SaleDto;
import com.example.demo.dto.request.SaleRequestDto;
import com.example.demo.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    List<SaleDto> getSales();
    Optional<Sale> getSale(Long Id);
    void saveSaleToPeopleToUser(SaleRequestDto saleRequestDto);
}
