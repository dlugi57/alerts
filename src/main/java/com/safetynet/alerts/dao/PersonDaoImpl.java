package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// TODO: 07/09/2020
@Component // or service
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private DataService dataService;

    private List<Person> personList;

    // TODO: 01/09/2020 it not work
/*   public PersonDaoImpl() {
        personList = dataService.getDataAlert().getPersons();
    }*/


    @Override
    public Person getPersonById(int id) {
        personList = dataService.getDataAlert().getPersons();
        //Person person;
        for (Person person : personList) {
            if (person.getId() == id) {
                return person;
            }

        }

        return null;
    }

    @Override
    public List<Person> getPersons() {
        return personList = dataService.getDataAlert().getPersons();
    }

    @Override
    public Person getByFirstNameAndLastName(String firstName, String lastName) {
        personList = dataService.getDataAlert().getPersons();
        //Person person;
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
        personList = dataService.getDataAlert().getPersons();
        //Person person;
        for (Person existingPerson : personList) {
            if (existingPerson.getFirstName().equalsIgnoreCase(person.getFirstName())
                    && existingPerson.getLastName().equalsIgnoreCase(person.getLastName())) {
                //if (medicalRecord != null)existingPerson.setMedicalRecord(medicalRecord);
                if (person.getAddress() != null)existingPerson.setAddress(person.getAddress());
                if (person.getCity() != null)existingPerson.setCity(person.getCity());
                if (person.getEmail() != null)existingPerson.setEmail(person.getEmail());
                if (person.getPhone() != null)existingPerson.setPhone(person.getPhone());
                if (person.getZip() != null)existingPerson.setZip(person.getZip());


                //if (person.getMedicalRecord() != null)existingPerson.setMedicalRecord(person.getMedicalRecord());
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        personList = dataService.getDataAlert().getPersons();
        //Person person;
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


}
