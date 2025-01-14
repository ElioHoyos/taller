package com.example.demo.service.impl;

import com.example.demo.dao.PersonDao;
import com.example.demo.dao.request.PersonRequestDao;
import com.example.demo.entity.Person;
import com.example.demo.entity.enums.DocumentType;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RestControllerAdvice
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<PersonDao> getPersons() {
        return List.of();
    }

    @Override
    public Optional<PersonDao> getPerson(Long Id) {
        return Optional.empty();
    }

    @Override
    public void savePerson(PersonRequestDao requestDao) {
//        if (requestDao == null) {
//            throw new IllegalArgumentException("El objeto requestDao no puede ser nulo.");
//        }

        DocumentType documentType = requestDao.getDocument_type();
        String documentNumber = requestDao.getDocument_number();

        // Validación del tipo de documento
        switch (documentType) {
            case DNI:
                if (String.valueOf(documentNumber).length() != 8) {
                    throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos.");
                }
                break;
            case RUC:
                if (String.valueOf(documentNumber).length() != 11) {
                    throw new IllegalArgumentException("El RUC debe tener exactamente 11 dígitos.");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de documento no válido. Debe ser 'DNI' o 'RUC'.");
        }

        Boolean defaultState = true; // O false, según lo que necesites
        Person person = Person.builder()
                .type_person(requestDao.getType_person())
                .name(requestDao.getName())
                .document_type(documentType) // Convertir a String si es necesario
                .document_number(documentNumber) // Convertir a String si es necesario
                .cellphone(requestDao.getCellphone())
                .email(requestDao.getEmail())
                .address(requestDao.getAddress())
                .state(defaultState)
                .date_modified(LocalDate.now())
                .date_created(LocalDate.now())
                .build();
        personRepository.save(person);
    }
}
