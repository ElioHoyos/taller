package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.request.CustomerRequestDto;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // LISTAR (soluciona tu 405 al hacer GET /api/v1/person)
    @GetMapping
    public List<CustomerDto> getAll() {
        return customerService.getPersons();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Long id) {
        return customerService.getPerson(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR (se mantiene void)
    @PostMapping
    public ResponseEntity<CustomerDto> savePerson(@RequestBody CustomerRequestDto person){
        CustomerDto created = customerService.savePerson(person);
        return ResponseEntity
                .created(URI.create("/api/v1/person/" + created.getId()))
                .body(created);
    }
}
