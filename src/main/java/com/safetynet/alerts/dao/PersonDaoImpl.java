package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public class PersonDaoImpl implements PersonDao {

    private List<Person> personList;

    @Autowired
    private DataService dataService;


    public PersonDaoImpl() {
        //todo: send at the place copy of this list
        personList = dataService.getDataAlert().getPersons();

    }


}
