package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Initialization of person data with all crud methods
 */
@Component
public class PersonDaoImpl implements PersonDao {

    // list of persons
    final private List<Person> personList;

    /**
     * Init list of person from global data
     *
     * @param dataService all data from file
     */
    @Autowired
    public PersonDaoImpl(DataService dataService) {
        personList = dataService.getDataAlert().getPersons();
    }

    /**
     * Get all persons
     *
     * @return list of all persons
     */
    @Override
    public List<Person> getPersons() {
        return personList;
    }

    /**
     * Get person by first name and last name
     *
     * @param firstName first name
     * @param lastName  last name
     * @return person object
     */
    @Override
    public Person getByFirstNameAndLastName(String firstName, String lastName) {
        for (Person person : personList) {
            // check if first name and last name are the same without considering capitals
            if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
                return person;
            }

        }

        return null;
    }

    /**
     * Add person
     *
     * @param person person object
     * @return true if success
     */
    @Override
    public boolean addPerson(Person person) {

        personList.add(person);

        return true;
    }

    /**
     * Update person
     *
     * @param person person object
     * @return true when success
     */
    @Override
    public boolean updatePerson(Person person) {
        for (Person existingPerson : personList) {
            // check at firs obligatory parameters
            if (existingPerson.getFirstName().equalsIgnoreCase(person.getFirstName())
                    && existingPerson.getLastName().equalsIgnoreCase(person.getLastName())) {
                // update only send data and if is null leave existing data
                if (person.getAddress() != null) existingPerson.setAddress(person.getAddress());
                if (person.getCity() != null) existingPerson.setCity(person.getCity());
                if (person.getEmail() != null) existingPerson.setEmail(person.getEmail());
                if (person.getPhone() != null) existingPerson.setPhone(person.getPhone());
                if (person.getZip() != null) existingPerson.setZip(person.getZip());
                return true;
            }

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
        for (Person existingPerson : personList) {
            // check if first name and last name are the same without considering capitals
            if (existingPerson.getFirstName().equalsIgnoreCase(person.getFirstName())
                    && existingPerson.getLastName().equalsIgnoreCase(person.getLastName())) {
                personList.remove(existingPerson);
                return true;
            }

        }
        return false;
    }

    /**
     * Get list of persons by address
     *
     * @param address address of person
     * @return List of persons
     */
    @Override
    public List<Person> getPersonsByAddress(String address) {
        // initialize list of persons to send
        List<Person> filteredPersonsList = new ArrayList<>();

        for (Person person : personList) {
            // check if address is the same without considering capitals and white spaces
            if (person.getAddress().replaceAll("\\s+", "").equalsIgnoreCase(address.replaceAll("\\s+", ""))) {
                filteredPersonsList.add(person);
            }
        }

        return filteredPersonsList;
    }

    /**
     * Get list of persons by city
     *
     * @param city city of person
     * @return List of persons
     */
    @Override
    public List<Person> getPersonsByCity(String city) {

        // initialize list of persons to send
        List<Person> filteredPersonsList = new ArrayList<>();

        for (Person person : personList) {
            // check if city is the same without considering capitals and white spaces
            if (person.getCity().replaceAll("\\s+", "").equalsIgnoreCase(city.replaceAll("\\s+", ""))) {
                filteredPersonsList.add(person);
            }
        }

        return filteredPersonsList;
    }


}
