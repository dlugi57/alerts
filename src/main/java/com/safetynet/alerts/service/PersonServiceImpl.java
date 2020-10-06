package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Person data manipulation
 */
@Service
public class PersonServiceImpl implements PersonService {

    // logger init
    private static final Logger logger = LogManager
            .getLogger(PersonServiceImpl.class);

    // initialize objects
    PersonDao personDao;
    MedicalRecordDao medicalRecordDao;
    FireStationDao fireStationDao;

    /**
     * Field injection of person dao
     *
     * @param personDao person data
     */
    @Autowired
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    /**
     * Field injection of medical record dao
     *
     * @param medicalRecordDao medical record data
     */
    @Autowired
    public void setMedicalRecordDao(MedicalRecordDao medicalRecordDao) {
        this.medicalRecordDao = medicalRecordDao;
    }

    /**
     * Field injection of medical record dao
     *
     * @param fireStationDao fire station data
     */
    @Autowired
    public void setFireStationDao(FireStationDao fireStationDao) {
        this.fireStationDao = fireStationDao;
    }

    /**
     * Get list of all persons
     *
     * @return List of persons
     */
    @Override
    public List<Person> getPersons() {
        return personDao.getPersons();
    }

    /**
     * Add person
     *
     * @param person person object
     * @return true when success
     */
    @Override
    public boolean addPerson(Person person) {

        // check if person exist already
        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson == null) {
            personDao.addPerson(person);
            return true;
        }
        return false;

    }

    /**
     * Get person by first and last name
     *
     * @param firstName first name
     * @param lastName  last name
     * @return person object
     */
    @Override
    public Person getPersonByFirstNameAndLastName(String firstName, String lastName) {
        return personDao.getByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Update person
     *
     * @param person person object
     * @return true if success
     */
    @Override
    public boolean updatePerson(Person person) {
        //check if person exist already
        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson != null) {
            personDao.updatePerson(person);
            return true;
        }

        return false;
    }

    /**
     * Delete person
     *
     * @param person person object
     * @return true when success
     */
    @Override
    public boolean deletePerson(Person person) {
        // check if person exist already
        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson != null) {
            personDao.deletePerson(person);
            return true;
        }

        return false;
    }

    /**
     * Get children by address
     *
     * @param address address
     * @return List of children and adults in address given
     */
    @Override
    public ChildrenByAddress getChildrenByAddress(String address) {
        // initialize persons list
        List<Person> persons = personDao.getPersonsByAddress(address);
        // prepare list for adults and children
        List<PersonAlert> children = new ArrayList<>();
        List<PersonAlert> adults = new ArrayList<>();
        // initialize DTO object
        ChildrenByAddress childrenByAddress = new ChildrenByAddress();
        // verify if there are some persons in address given
        if (persons != null && !persons.isEmpty()) {
            for (Person person : persons) {
                // initialize new DTO person
                PersonAlert personAlert = new PersonAlert();
                // get medical records
                MedicalRecord personMedicalRecord =
                        medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (personMedicalRecord != null) {
                    // get age and set DTO person
                    int age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                    personAlert.setFirstName(person.getFirstName());
                    personAlert.setLastName(person.getLastName());
                    personAlert.setAge(age);
                    // verify age and increment right list
                    if (age <= 18) {
                        children.add(personAlert);
                    } else {
                        adults.add(personAlert);
                    }
                }
                // set results
                childrenByAddress.setChildren(children);
                childrenByAddress.setAdults(adults);
            }
            return childrenByAddress;
        }

        return null;
    }

    /**
     * Get persons and station by address
     *
     * @param address address
     * @return List of persons and right number of station in address given
     */
    @Override
    public PersonsAndStationByAddress getPersonsAndStationByAddress(String address) {
        // get persons
        List<Person> persons = personDao.getPersonsByAddress(address);
        // get fire stations
        FireStation fireStation = fireStationDao.getFireStationByStationAddress(address);

        // initialize DTO object to null to prove that data exist
        PersonsAndStationByAddress personsAndStationByAddress = null;
        // if there is persons
        if (persons != null && !persons.isEmpty()) {
            // parse data and create list of DTO persons
            List<PersonFire> personsFire = parsePersonsFire(persons);
            // initialize DTO object and set data
            if (personsFire != null) {
                personsAndStationByAddress = new PersonsAndStationByAddress();
                personsAndStationByAddress.setPersons(personsFire);
            }
        }
        // if object is initialized set station
        if (personsAndStationByAddress != null) {
            if (fireStation != null) {
                personsAndStationByAddress.setStation(fireStation.getStation());
            }

            return personsAndStationByAddress;
        }

        return null;
    }

    /**
     * Get persons and addresses by stations
     *
     * @param stations List of fire stations ids
     * @return List of Persons grouped by address
     */
    @Override
    public List<PersonsAndAddressesByStation> getPersonsAndAddressesByStations(List<Integer> stations) {

        // Initialize empty list of DTO objects
        List<PersonsAndAddressesByStation> personsAndAddressesByStationList = new ArrayList<>();
        // Initialize DTO object to null to prove that data exist
        PersonsAndAddressesByStation personsAndAddressesByStation = null;
        // check if there are station in sent list
        if (stations != null && !stations.isEmpty()) {

            for (Integer station : stations) {
                // get fire station
                List<FireStation> fireStations = fireStationDao.getFireStationsByStationId(station);
                // check if fire station exist
                if (fireStations != null && !fireStations.isEmpty()) {

                    for (FireStation fireStation : fireStations) {
                        // get persons
                        List<Person> persons = personDao.getPersonsByAddress(fireStation.getAddress());
                        // check if person exist
                        if (persons != null) {
                            // parse data and create list of DTO persons
                            List<PersonFire> personsFire = parsePersonsFire(persons);
                            // initialize DTO object and set data
                            if (personsFire != null) {
                                personsAndAddressesByStation = new PersonsAndAddressesByStation();
                                personsAndAddressesByStation.setPersons(personsFire);
                            }

                        }

                        // if object is initialized set station
                        if (personsAndAddressesByStation != null) {
                            personsAndAddressesByStation.setAddress(fireStation.getAddress());
                            personsAndAddressesByStationList.add(personsAndAddressesByStation);
                        }
                    }
                }
            }
            // send only one the list is not empty
            if (!personsAndAddressesByStationList.isEmpty()) {
                return personsAndAddressesByStationList;
            }
        }

        return null;
    }

    /**
     * Get persons with medical records by first and last name living in the same address as given parameter person
     *
     * @param firstName first name
     * @param lastName  last name
     * @return List of persons with medical records
     */
    @Override
    public List<PersonInfo> getPersonsInfo(String firstName, String lastName) {
        // get persons
        Person person = personDao.getByFirstNameAndLastName(firstName, lastName);
        // initialize new list of DTO persons
        List<PersonInfo> personsInfo = new ArrayList<>();

        // if this persons exist
        if (person != null) {
            // get persons from the same address
            List<Person> persons = personDao.getPersonsByAddress(person.getAddress());
            // if persons exist
            if (persons != null && !persons.isEmpty()) {
                for (Person personFromList : persons) {
                    // initialize new DTO person object
                    PersonInfo personInfo = new PersonInfo();
                    // get medical records
                    MedicalRecord personMedicalRecord =
                            medicalRecordDao.getMedicalRecordByFirstNameAndLastName(personFromList.getFirstName(), personFromList.getLastName());
                    if (personMedicalRecord != null) {
                        // calculate age of person
                        int age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                        // set data into DTO person object
                        personInfo.setLastName(person.getLastName());
                        personInfo.setAddress(person.getAddress());
                        personInfo.setAge(age);
                        personInfo.setEmail(person.getEmail());
                        personInfo.setAllergies(personMedicalRecord.getAllergies());
                        personInfo.setMedications(personMedicalRecord.getMedications());
                    }
                    // increment list
                    personsInfo.add(personInfo);
                }
                // send result if not empty
                if (!personsInfo.isEmpty()) {
                    return personsInfo;
                }
            }
        }

        return null;
    }

    /**
     * Get email from given city
     *
     * @param city city
     * @return list of emails
     */
    @Override
    public List<String> getCommunityEmails(String city) {
        // get persons
        List<Person> persons = personDao.getPersonsByCity(city);
        // initialize empty list of emails
        List<String> communityEmails = new ArrayList<>();
        // if there are persons
        if (persons != null && !persons.isEmpty()) {
            for (Person person : persons) {
                communityEmails.add(person.getEmail());
            }
            // send if there are some results
            if (!communityEmails.isEmpty()) {
                return communityEmails;
            }
        }
        return null;
    }

    /**
     * Parse list of persons and create new DTO fire person
     *
     * @param persons list of persons
     * @return list of DTO persons
     */
    private List<PersonFire> parsePersonsFire(List<Person> persons) {
        logger.debug("Methode : parsePersonsFire /**/ Start parsing person data and set it to the" +
                " new DTO model person");

        // initialize empty list of DTO persons
        List<PersonFire> personsFire = new ArrayList<>();

        for (Person person : persons) {
            // initialize new DTO person
            PersonFire personFire = new PersonFire();
            // get medical record
            MedicalRecord personMedicalRecord =
                    medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            if (personMedicalRecord != null) {
                // calculate age
                int age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                // set all data
                personFire.setLastName(person.getLastName());
                personFire.setAge(age);
                personFire.setPhone(person.getPhone());
                personFire.setAllergies(personMedicalRecord.getAllergies());
                personFire.setMedications(personMedicalRecord.getMedications());
            }
            // increment list
            personsFire.add(personFire);
        }
        // if there is a results send it
        if (!personsFire.isEmpty()) {
            return personsFire;
        }
        logger.error("Methode : parsePersonsFire /**/ Error when parsing person data");

        return null;
    }
}
