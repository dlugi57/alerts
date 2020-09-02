package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping(value = "Test")
    public String test() {
        return "Ty chuju jebany";
    }

    @GetMapping(value = "personById")
    public Person getPersonById(@RequestParam(required = true) Integer id) {
        return personService.getPersonById(id);
    }

    @GetMapping(value = "persons")
    public List<Person>  getPersons() {
        return personService.getPersons();
    }



}
