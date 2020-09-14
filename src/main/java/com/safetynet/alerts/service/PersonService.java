package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildrenByAddress;
import com.safetynet.alerts.dto.PersonInfo;
import com.safetynet.alerts.dto.PersonsAndAddressesByStation;
import com.safetynet.alerts.dto.PersonsAndStationByAddress;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface PersonService {
    //Person getPersonById(int id);

    List<Person> getPersons();

    boolean addPerson(Person person);

    Person getPersonByFirstNameAndLastName(String firstName, String lastName);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);

    ChildrenByAddress getChildrenByAddress(String address);

    PersonsAndStationByAddress getPersonsAndStationByAddress(String address);

    List<PersonsAndAddressesByStation> getPersonsAndAddressesByStations(List<Integer> stations);

    List<PersonInfo> getPersonsInfo(String firstName, String lastName);

    List<String> getCommunityEmails(String city);
}
