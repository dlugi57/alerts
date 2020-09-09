package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Person;

import java.util.List;

public interface PersonDao {

    List<Person> getPersons();

    Person getByFirstNameAndLastName(String firstName, String lastName);

    boolean addPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);

    List<Person> getPersonsByAddress(String address);
}
