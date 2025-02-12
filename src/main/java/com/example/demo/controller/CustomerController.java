package com.example.demo.controller;


import com.example.demo.dto.request.CustomerRequestDto;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/person")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public void savePerson(@RequestBody CustomerRequestDto person){
        customerService.savePerson(person);
    }

}
