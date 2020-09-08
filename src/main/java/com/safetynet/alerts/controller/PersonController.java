package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    //get swagger to this
    //@ApiOperation(value= "Recupere un produit selon son ID")
    /*
    @GetMapping(value = "person")
    public Person getPersonById(@RequestParam(required = true) Integer id) throws ResponseStatusException {

        Person person = personService.getPersonById(id);
        if (person == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id " + id + " don't exist");

        return person;
    }
    */

    @GetMapping(value = "person")
    public Person getPersonByFirstNameAndLastName(@RequestParam(required = true) String firstName, String lastName) throws ResponseStatusException {

        Person person = personService.getPersonByFirstNameAndLastName(firstName, lastName);
        if (person == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person named  " + firstName + " " + lastName + " don't exist");

        return person;
    }

    @PostMapping(value = "/person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addPerson(@Valid @RequestBody Person person) {

        if (!personService.addPerson(person)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This person already exist");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", person.getFirstName()).queryParam("lastName", person.getLastName()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updatePerson(@Valid @RequestBody Person person) {

        if (!personService.updatePerson(person)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This person don't exist");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", person.getFirstName()).queryParam("lastName", person.getLastName()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/person")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@Valid @RequestBody Person person) {
    //unique???
        if (!personService.deletePerson(person)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This person don't exist");
        }
    }

    @GetMapping(value = "persons")
    public List<Person> getPersons() {
        return personService.getPersons();
    }


}
