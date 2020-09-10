package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.PersonAlert;
import com.safetynet.alerts.dto.ChildrenByAddress;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.AgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {


    PersonDao personDao;
    MedicalRecordDao medicalRecordDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao, MedicalRecordDao medicalRecordDao){
        this.personDao = personDao;
        this.medicalRecordDao = medicalRecordDao;
    }

    @Override
    public List<Person> getPersons() {
        return personDao.getPersons();
    }

    @Override
    public boolean addPerson(Person person) {

        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson == null) {
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
        if (checkPerson != null) {
            personDao.updatePerson(person);
            return true;
        }

        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson != null) {
            personDao.deletePerson(person);
            return true;
        }

        return false;
    }

    @Override
    public ChildrenByAddress getChildrenByAddress(String address) {
        List<Person> persons = personDao.getPersonsByAddress(address);
        List<PersonAlert> children = new ArrayList<PersonAlert>();
        List<PersonAlert> adults = new ArrayList<PersonAlert>();
        ChildrenByAddress childrenByAddress = new ChildrenByAddress();

        if (persons!=null){
            for (Person person : persons) {
                PersonAlert personAlert = new PersonAlert();

                MedicalRecord personMedicalRecord =
                        medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (personMedicalRecord != null) {

                    Integer age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                    personAlert.setFirstName(person.getFirstName());
                    personAlert.setLastName(person.getLastName());
                    personAlert.setAge(age);

                    if (age <= 18) {
                        children.add(personAlert);
                    } else {
                        adults.add(personAlert);
                    }
                }

                childrenByAddress.setChildren(children);
                childrenByAddress.setAdults(adults);
            }
        }

        if (childrenByAddress != null) {
            return childrenByAddress;
        }

        return null;
    }
}
