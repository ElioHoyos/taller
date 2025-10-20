package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.request.CustomerRequestDto;
import com.example.demo.entity.Customer;
import com.example.demo.exception.DocumentValidationException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.validate.DocumentValidator;
import com.example.demo.validate.EmailValidator;
import com.example.demo.validate.PhoneValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final DocumentValidator documentValidator;
    private final PhoneValidator phoneValidator;
    private final EmailValidator emailValidator; // si no quieres validar email, elimínalo

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               DocumentValidator documentValidator,
                               PhoneValidator phoneValidator,
                               EmailValidator emailValidator) {
        this.customerRepository = customerRepository;
        this.documentValidator = documentValidator;
        this.phoneValidator = phoneValidator;
        this.emailValidator = emailValidator;
    }

    @Override
    public List<CustomerDto> getPersons() {
        return customerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> getPerson(Long id) {
        return customerRepository.findById(id).map(this::convertToDto);
    }

    @Override
    @Transactional
    public CustomerDto savePerson(CustomerRequestDto r) {
        List<String> errors = new ArrayList<>();
        errors.addAll(documentValidator.validate(r.getDocument_type(), r.getDocument_number()));
        errors.addAll(phoneValidator.validate(r.getCellphone()));
        errors.addAll(emailValidator.validate(r.getEmail()));
        if (!errors.isEmpty()) throw new DocumentValidationException(errors);

        Customer c = Customer.builder()
                .type_person(r.getType_person())
                .name(r.getName())
                .document_type(r.getDocument_type())
                .document_number(r.getDocument_number())
                .cellphone(r.getCellphone())
                .email(r.getEmail())
                .address(r.getAddress())
                .state(Boolean.TRUE)
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .build();

        Customer saved = customerRepository.save(c);
        return convertToDto(saved);  // ⬅️ devolvemos el creado
    }

    private CustomerDto convertToDto(Customer c) {
        return CustomerDto.builder()
                .id(c.getId())
                .type_person(c.getType_person())
                .name(c.getName())
                .document_type(c.getDocument_type())
                .document_number(c.getDocument_number())
                .cellphone(c.getCellphone())
                .email(c.getEmail())
                .address(c.getAddress())
                .state(c.getState())
                .date_modified(c.getDate_modified())
                .date_created(c.getDate_created())
                .build();
    }

}
