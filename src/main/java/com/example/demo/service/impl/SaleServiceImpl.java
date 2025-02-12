package com.example.demo.service.impl;

import com.example.demo.dto.SaleDetailDto;
import com.example.demo.dto.SaleDto;
import com.example.demo.dto.request.SaleDetailRequestDto;
import com.example.demo.dto.request.SaleRequestDto;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Sale;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SaleDetailService;
import com.example.demo.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService, SaleDetailService {


    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final List<SaleDetailRequestDto> listSaleDetail;


    @Override
    public List<SaleDto> getSales() {
        return List.of();
    }

    @Override
    public Optional<Sale> getSale(Long Id) {
        return Optional.empty();
    }

    @Override
    public void saveSaleToPeopleToUser(SaleRequestDto saleRequestDto) {
        //validar si existe el id
        Customer customer = customerRepository.findById(saleRequestDto.getPeople_id())
                .orElseThrow(() -> new NotFoundException(String.format("El ID del Cliente no existe", saleRequestDto.getPeople_id())));
        User user = userRepository.findById(saleRequestDto.getUser_id())
                .orElseThrow(() -> new NotFoundException(String.format("El ID del Usuario no existe", saleRequestDto.getUser_id())));

        Sale salev = Sale.builder()
                .customer(customer)
                .user(user)
                .voucher_type(saleRequestDto.getVoucher_type())
                .voucher_series(saleRequestDto.getVoucher_series())
                .voucher_number(saleRequestDto.getVoucher_number())
                .sale_date(LocalDate.now())
                .igv(saleRequestDto.getIgv())
                .total(saleRequestDto.getTotal())
                .state(Boolean.TRUE)
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .build();
        Sale sale = saleRepository.save(salev);
        sale.getId();
    }

    @Override
    public List<SaleDetailDto> getDetails() {
        return List.of();
    }

    @Override
    public List<SaleDetailRequestDto> getSaleDetail(String Id) {
        return listSaleDetail;
    }

    @Override
    public void saveDetailToSale(SaleDetailRequestDto saleDetailRequestDto) {
        listSaleDetail.add(saleDetailRequestDto);
    }
}
