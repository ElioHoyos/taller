package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.dao.request.PersonRequestDao;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<PersonDao> getPersons();
    Optional<PersonDao> getPerson(Long Id);
    void savePerson(PersonRequestDao requestDao);

}
