package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.FireStation;
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
    FireStationDao fireStationDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao, MedicalRecordDao medicalRecordDao, FireStationDao fireStationDao) {
        this.personDao = personDao;
        this.medicalRecordDao = medicalRecordDao;
        this.fireStationDao = fireStationDao;
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

        if (persons != null) {
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

    @Override
    public PersonsAndStationByAddress getPersonsAndStationByAddress(String address) {
        List<Person> persons = personDao.getPersonsByAddress(address);
        FireStation fireStation = fireStationDao.getFireStationByStationAddress(address);

        PersonsAndStationByAddress personsAndStationByAddress = null;
        List<PersonFire> personsFire = new ArrayList<PersonFire>();

        if (persons != null) {
            for (Person person : persons) {
                PersonFire personFire = new PersonFire();

                MedicalRecord personMedicalRecord =
                        medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (personMedicalRecord != null) {

                    Integer age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                    personFire.setLastName(person.getLastName());
                    personFire.setAge(age);
                    personFire.setPhone(person.getPhone());
                    personFire.setAllergies(personMedicalRecord.getAllergies());
                    personFire.setMedications(personMedicalRecord.getMedications());

                }

                personsFire.add(personFire);
            }
            personsAndStationByAddress = new PersonsAndStationByAddress();
            personsAndStationByAddress.setPersons(personsFire);
        }

        if (personsAndStationByAddress != null) {
            if (fireStation != null) {
                personsAndStationByAddress.setStation(fireStation.getStation());
            }

            return personsAndStationByAddress;
        }

        return null;
    }

    @Override
    public List<PersonsAndAddressesByStation> getPersonsAndAddressesByStations(List<Integer> stations) {

        List<PersonsAndAddressesByStation> personsAndAddressesByStationList = new ArrayList<PersonsAndAddressesByStation>();
        PersonsAndAddressesByStation personsAndAddressesByStation = null;


        if (stations != null || !stations.isEmpty()) {

            for (Integer station : stations) {
                List<FireStation> fireStations = fireStationDao.getFireStationsByStationId(station);
                if (fireStations != null || !fireStations.isEmpty()) {


                    for (FireStation fireStation : fireStations) {
                        List<Person> persons = personDao.getPersonsByAddress(fireStation.getAddress());
                        List<PersonFire> personsFire = new ArrayList<PersonFire>();
                        if (persons != null) {
                            for (Person person : persons) {
                                PersonFire personFire = new PersonFire();

                                MedicalRecord personMedicalRecord =
                                        medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                                if (personMedicalRecord != null) {

                                    Integer age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                                    personFire.setLastName(person.getLastName());
                                    personFire.setAge(age);
                                    personFire.setPhone(person.getPhone());
                                    personFire.setAllergies(personMedicalRecord.getAllergies());
                                    personFire.setMedications(personMedicalRecord.getMedications());

                                }

                                personsFire.add(personFire);
                            }
                            personsAndAddressesByStation = new PersonsAndAddressesByStation();
                            personsAndAddressesByStation.setPersons(personsFire);
                        }


                        if (personsAndAddressesByStation != null) {
                            if (fireStation != null) {
                                personsAndAddressesByStation.setAddress(fireStation.getAddress());

                                personsAndAddressesByStationList.add(personsAndAddressesByStation);
                            }
                        }

                    }


                }


            }

            if (personsAndAddressesByStationList != null || !personsAndAddressesByStationList.isEmpty()) {
                return personsAndAddressesByStationList;
            }


        }

        return null;
    }
}
