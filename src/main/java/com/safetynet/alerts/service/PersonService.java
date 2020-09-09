package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;

import java.util.List;

public interface PersonService {
    //Person getPersonById(int id);

    List<Person> getPersons();

    boolean addPerson(Person person);

    Person getPersonByFirstNameAndLastName(String firstName, String lastName);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);
}
