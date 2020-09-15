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
        List<PersonAlert> children = new ArrayList<>();
        List<PersonAlert> adults = new ArrayList<>();
        ChildrenByAddress childrenByAddress = new ChildrenByAddress();

        if (persons != null) {
            for (Person person : persons) {
                PersonAlert personAlert = new PersonAlert();

                MedicalRecord personMedicalRecord =
                        medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (personMedicalRecord != null) {

                    int age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
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

/*        if (childrenByAddress != null) {
            return childrenByAddress;
        }*/

        return childrenByAddress;
    }

    @Override
    public PersonsAndStationByAddress getPersonsAndStationByAddress(String address) {
        List<Person> persons = personDao.getPersonsByAddress(address);
        FireStation fireStation = fireStationDao.getFireStationByStationAddress(address);

        PersonsAndStationByAddress personsAndStationByAddress = null;
        //List<PersonFire> personsFire = new ArrayList<PersonFire>();

        if (persons != null) {
            List<PersonFire> personsFire  = parsePersonsFire(persons);
/*            for (Person person : persons) {
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
            }*/
            // TODO: 14/09/2020 how to resolve this warning
            // TODO: 15/09/2020 is better to send the empty list than null 
            if (!personsFire.isEmpty()){
                personsAndStationByAddress = new PersonsAndStationByAddress();
                personsAndStationByAddress.setPersons(personsFire);
            }

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

        List<PersonsAndAddressesByStation> personsAndAddressesByStationList = new ArrayList<>();
        PersonsAndAddressesByStation personsAndAddressesByStation = null;


        if (stations != null || !stations.isEmpty()) {

            for (Integer station : stations) {
                List<FireStation> fireStations = fireStationDao.getFireStationsByStationId(station);
                if (fireStations != null || !fireStations.isEmpty()) {


                    for (FireStation fireStation : fireStations) {
                        List<Person> persons = personDao.getPersonsByAddress(fireStation.getAddress());
                        //List<PersonFire> personsFire = new ArrayList<PersonFire>();
                        if (persons != null) {
                            List<PersonFire> personsFire = parsePersonsFire(persons);
/*                            for (Person person : persons) {
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
                            }*/

                            if (personsFire!= null){
                                personsAndAddressesByStation = new PersonsAndAddressesByStation();
                                personsAndAddressesByStation.setPersons(personsFire);
                            }

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

    @Override
    public List<PersonInfo> getPersonsInfo(String firstName, String lastName) {

        Person person = personDao.getByFirstNameAndLastName(firstName, lastName);
        List<PersonInfo> personsInfo = new ArrayList<>();

        if (person != null){

            List<Person> persons = personDao.getPersonsByAddress(person.getAddress());
            if (persons != null || !persons.isEmpty()){
                for (Person personFromList : persons){
                    PersonInfo personInfo = new PersonInfo();

                    MedicalRecord personMedicalRecord =
                            medicalRecordDao.getMedicalRecordByFirstNameAndLastName(personFromList.getFirstName(), personFromList.getLastName());
                    if (personMedicalRecord != null) {

                        int age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                        personInfo.setLastName(person.getLastName());
                        personInfo.setAddress(person.getAddress());
                        personInfo.setAge(age);
                        personInfo.setEmail(person.getEmail());
                        personInfo.setAllergies(personMedicalRecord.getAllergies());
                        personInfo.setMedications(personMedicalRecord.getMedications());

                    }

                    personsInfo.add(personInfo);
                }

                if (!personsInfo.isEmpty()){
                    return personsInfo;
                }
            }



        }


        return null;
    }

    @Override
    public List<String> getCommunityEmails(String city) {
        List <Person> persons = personDao.getPersonsByCity(city);
        List <String> communityEmails = new ArrayList<>();
        if (persons != null && !persons.isEmpty()){
            for (Person person:persons){
                communityEmails.add(person.getEmail());
            }

            if (!communityEmails.isEmpty()){
                return communityEmails;
            }
        }
        return null;
    }

    private List<PersonFire> parsePersonsFire(List<Person> persons) {
        List<PersonFire> personsFire = new ArrayList<>();

        for (Person person : persons) {
            PersonFire personFire = new PersonFire();

            MedicalRecord personMedicalRecord =
                    medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            if (personMedicalRecord != null) {

                int age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                personFire.setLastName(person.getLastName());
                personFire.setAge(age);
                personFire.setPhone(person.getPhone());
                personFire.setAllergies(personMedicalRecord.getAllergies());
                personFire.setMedications(personMedicalRecord.getMedications());

            }

            personsFire.add(personFire);
        }
        if (!personsFire.isEmpty()){
            return personsFire;
        }
    return null;
    }
}
