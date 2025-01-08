package com.example.demo.controller;


import com.example.demo.dao.request.PersonRequestDao;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public void savePerson(@RequestBody PersonRequestDao person){
        personService.savePerson(person);
    }

}
