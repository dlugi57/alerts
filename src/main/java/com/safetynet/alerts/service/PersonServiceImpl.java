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
}
