package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonDao personDao;

    @Override
    public Person getPersonById(int id){
        return personDao.getPersonById(id);
    }

    @Override
    public List<Person> getPersons(){
        return personDao.getPersons();
    }

    @Override
    public boolean addPerson(Person person) {



/*        if (!personDao.getPersons().contains(person)) {
            personDao.addPerson(person);
            return true;
        }

        return false;*/


        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson == null){
            personDao.addPerson(person);
            return true;
        }
        return false;

    }

    @Override
    public Person getPersonByFirstNameAndLastName(String firstName, String lastName) {
        return personDao.getByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public boolean updatePerson(Person person) {
        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson != null){
            personDao.updatePerson(person);
            return true;
        }

        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson != null){
            personDao.deletePerson(person);
            return true;
        }

        return false;
    }
}
