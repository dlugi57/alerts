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

/**
 * Person controller class which gives as possibilities of CRUD every person data
 */
@RestController
public class PersonController {

    // init of person service
    PersonService personService;

    /**
     * Person controller constructor
     *
     * @param personService Person service initialization
     */
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Get person by first and last name
     *
     * @param firstName First name
     * @param lastName  Last name
     * @return Person object
     */
    @GetMapping(value = "person")
    public Person getPersonByFirstNameAndLastName(@RequestParam() String firstName, String lastName) {
        // get person
        Person person = personService.getPersonByFirstNameAndLastName(firstName, lastName);
        // if person don't exist send error message
        if (person == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person named " + firstName + " " + lastName + " don't exist");

        return person;
    }

    /**
     * Create person
     *
     * @param person person object with necessary data
     * @return status and uri in header to the new created person
     */
    @PostMapping(value = "/person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addPerson(@Valid @RequestBody Person person) {

        // if person already exist send error message
        if (!personService.addPerson(person)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This person already exist");
        }

        // create url with new created person
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", person.getFirstName()).queryParam("lastName", person.getLastName()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Update person
     *
     * @param person person object
     * @return status and uri to the updated person
     */
    @PutMapping(value = "/person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updatePerson(@Valid @RequestBody Person person) {

        //if person don't exist send error message with status
        if (!personService.updatePerson(person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This person don't exist");
        }
        // create url with updated person
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", person.getFirstName()).queryParam("lastName", person.getLastName()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Delete person
     *
     * @param person Person object with obligatory parameters
     */
    @DeleteMapping(value = "/person")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@Valid @RequestBody Person person) {

        //if person don't exist send error message with status
        if (!personService.deletePerson(person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This person don't exist");
        }
    }

    /**
     * Get all persons from data
     *
     * @return List of persons
     */
    @GetMapping(value = "persons")
    public List<Person> getPersons() {

        List<Person> persons = personService.getPersons();

        // if there is no persons in data base send error message
        if (persons == null || persons.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no persons in the data base");
        }

        return persons;
    }


}
