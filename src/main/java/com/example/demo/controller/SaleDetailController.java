package com.example.demo.controller;


import com.example.demo.dto.SaleDetailDto;
import com.example.demo.dto.request.SaleDetailRequestDto;
import com.example.demo.entity.SaleDetail;
import com.example.demo.service.SaleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/sale_detail")
public class SaleDetailController {
    @Autowired
    private SaleDetailService saleDetailService;

    @GetMapping("/listSaleDetails")
    public List<SaleDetailDto> getAllDetails(){
        return saleDetailService.getDetails();
    }

    @GetMapping("/{sale_id}")
    public ResponseEntity<List<SaleDetailRequestDto>> getSaleDetail(@PathVariable("sale_id") String saleId){
        return ResponseEntity.ok(saleDetailService.getSaleDetail(saleId));
    }

    @PostMapping
    public void saveDetail(@RequestBody SaleDetailRequestDto saleDetailRequestDto){
        saleDetailService.saveDetailToSale(saleDetailRequestDto);
    }
}
