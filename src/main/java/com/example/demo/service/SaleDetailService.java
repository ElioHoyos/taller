package com.example.demo.service;

import com.example.demo.dto.SaleDetailDto;
import com.example.demo.dto.request.SaleDetailRequestDto;
import com.example.demo.entity.SaleDetail;

import java.util.List;
import java.util.Optional;

public interface SaleDetailService {
    List<SaleDetailDto> getDetails();
    List<SaleDetailRequestDto> getSaleDetail(String Id);
    void saveDetailToSale(SaleDetailRequestDto saleDetailRequestDto);
}
