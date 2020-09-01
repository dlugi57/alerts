package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;

import java.util.List;

public interface PersonService {
    Person getPersonById(int id);

    List<Person> getPersons();
}
