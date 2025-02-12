package com.example.demo.service;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.request.CustomerRequestDto;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDto> getPersons();
    Optional<CustomerDto> getPerson(Long Id);
    void savePerson(CustomerRequestDto requestDao);

}
