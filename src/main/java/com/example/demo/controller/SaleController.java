package com.example.demo.controller;


import com.example.demo.dto.SaleDto;
import com.example.demo.dto.request.SaleRequestDto;
import com.example.demo.entity.Sale;
import com.example.demo.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping("/listSales")
    public List<SaleDto> getAllSale(){
        return saleService.getSales();
    }

    @GetMapping("/{Id}")
    public Optional<Sale> getById(@PathVariable("Id") Long Id){
        return saleService.getSale(Id);
    }

    @PostMapping
    public void saveSaleToPeopleToUser(@RequestBody SaleRequestDto saleRequestDto){
        saleService.saveSaleToPeopleToUser(saleRequestDto);
    }
}
