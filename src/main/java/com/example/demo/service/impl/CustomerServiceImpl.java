package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.request.CustomerRequestDto;
import com.example.demo.entity.Customer;
import com.example.demo.exception.DocumentValidationException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.validate.DocumentValidator;
import com.example.demo.validate.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private final DocumentValidator documentValidator;

    // Constructor para inyectar DocumentValidator
    public CustomerServiceImpl() {
        this.documentValidator = new DocumentValidator();
    }

    @Override
    public List<CustomerDto> getPersons() {
        return customerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CustomerDto convertToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .type_person(customer.getType_person())
                .name(customer.getName())
                .document_type(customer.getDocument_type())
                .document_number(customer.getDocument_number())
                .cellphone(customer.getCellphone())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .state(Boolean.TRUE)
                .date_modified(customer.getDate_modified())
                .date_created(customer.getDate_created())
                .build();
    }

    @Override
    public Optional<CustomerDto> getPerson(Long id) {
        return customerRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public void savePerson(CustomerRequestDto requestDao) {
        List<String> errorMessages = validateRequest(requestDao);

        // Si hay errores, lanzar una excepción con todos los mensajes
        if (!errorMessages.isEmpty()) {
            throw new DocumentValidationException(errorMessages);
        }

        // Crear y guardar la persona
        Customer customer = createCustomerFromRequest(requestDao);
        customerRepository.save(customer);
    }

    private List<String> validateRequest(CustomerRequestDto requestDao) {
        List<String> errorMessages = new ArrayList<>();

        // Validar el documento
        errorMessages.addAll(documentValidator.validate(requestDao.getDocument_type(), requestDao.getDocument_number()));

        // Validar el correo electrónico
        EmailValidator emailValidator = new EmailValidator();
        errorMessages.addAll(emailValidator.validate(requestDao.getEmail()));

        return errorMessages;
    }

    private Customer createCustomerFromRequest(CustomerRequestDto requestDao) {
        return Customer.builder()
                .type_person(requestDao.getType_person())
                .name(requestDao.getName())
                .document_type(requestDao.getDocument_type())
                .document_number(requestDao.getDocument_number())
                .cellphone(requestDao.getCellphone())
                .email(requestDao.getEmail())
                .address(requestDao.getAddress())
                .state(Boolean.TRUE)
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .build();
    }
}
