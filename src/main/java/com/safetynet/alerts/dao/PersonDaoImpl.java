package com.safetynet.alerts.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDaoImpl implements PersonDao {

    private List<Person> personList;

    @Autowired
   public PersonDaoImpl(DataService dataService) {
        personList = dataService.getDataAlert().getPersons();
    }


    @Override
    public List<Person> getPersons() {
        return null;
    }

    @Override
    public Person getByFirstNameAndLastName(String firstName, String lastName) {
        for (Person person : personList) {
            if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
                return person;
            }

        }

        return null;
    }

    @Override
    public boolean addPerson(Person person) {

        personList.add(person);

        return true;
    }

    @Override
    public boolean updatePerson(Person person) {
        for (Person existingPerson : personList) {
            if (existingPerson.getFirstName().equalsIgnoreCase(person.getFirstName())
                    && existingPerson.getLastName().equalsIgnoreCase(person.getLastName())) {
                if (person.getAddress() != null)existingPerson.setAddress(person.getAddress());
                if (person.getCity() != null)existingPerson.setCity(person.getCity());
                if (person.getEmail() != null)existingPerson.setEmail(person.getEmail());
                if (person.getPhone() != null)existingPerson.setPhone(person.getPhone());
                if (person.getZip() != null)existingPerson.setZip(person.getZip());
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        for (Person existingPerson : personList) {
            if (existingPerson.getFirstName().equalsIgnoreCase(person.getFirstName())
                    && existingPerson.getLastName().equalsIgnoreCase(person.getLastName())) {
                // TODO: 05/09/2020 if that is a correct way to remove ?
                personList.remove(existingPerson);
                return true;
            }

        }
        return false;
    }

    @Override
    public List<Person> getPersonsByAddress(String address) {

        List<Person> filteredPersonsList = new ArrayList<Person>();

        for (Person person : personList) {

            if (person.getAddress().replaceAll("\\s+", "").equalsIgnoreCase(address.replaceAll("\\s+", ""))) {

                filteredPersonsList.add(person);

            }

        }
        if (!filteredPersonsList.isEmpty()){
            return filteredPersonsList;
        }
        return null;
    }


}
