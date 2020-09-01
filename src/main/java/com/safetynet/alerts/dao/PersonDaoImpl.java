package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private DataService dataService;

    private List<Person> personList;

    // TODO: 01/09/2020 it not works
/*   public PersonDaoImpl() {
        personList = dataService.getDataAlert().getPersons();
    }*/


    @Override
    public Person getPersonById(int id){
        personList = dataService.getDataAlert().getPersons();
        //Person person;
        for (Person person: personList){
            if (person.getId() == id){
                return person;
            }

        }

        return null;
    }

    @Override
    public List<Person> getPersons() {
        return personList = dataService.getDataAlert().getPersons();
    }


}
