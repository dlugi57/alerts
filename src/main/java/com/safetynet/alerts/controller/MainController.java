package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Main controller is response to the several different endpoints using several available services
 */
@RestController
public class MainController {

    // initialize necessary services
    FireStationService fireStationService;
    PersonService personService;

    /**
     * Main controller constructor
     *
     * @param fireStationService fire station service initialization
     * @param personService      persons service initialization
     */
    @Autowired
    public MainController(FireStationService fireStationService, PersonService personService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
    }

    /**
     * Get persons in fire station area
     *
     * @param stationNumber fire station id
     * @return List of persons and qty of children and adults
     */
    @GetMapping(path = "firestation")
    public PersonsInFireStationArea getPersonsInFireStationArea(@RequestParam() Integer stationNumber) {

        PersonsInFireStationArea personsInFireStationArea = fireStationService.getPersonsInFireStationArea(stationNumber);
        // if there is no persons in area send status and error message
        if (personsInFireStationArea == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire station with this id " + stationNumber + " don't exist or there is no persons on this area");

        return personsInFireStationArea;
    }

    /**
     * Get children by adress
     *
     * @param address Address
     * @return Lists of children and adult living in specific address
     */
    @GetMapping(path = "childAlert")
    public ChildrenByAddress getChildrenByAddress(@RequestParam() String address) {

        ChildrenByAddress childrenByAddress = personService.getChildrenByAddress(address);
        // if there is no person on this address or address don't exist send status and error message
        if (childrenByAddress == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "At this address " + address + " there is no persons or this address don't exist");

        return childrenByAddress;
    }

    /**
     * Get phone numbers in fire station area
     *
     * @param stationNumber station id
     * @return List of phone numbers
     */
    @GetMapping(path = "phoneAlert")
    public List<String> getPhoneNumbersInFireStationArea(@RequestParam() Integer stationNumber) {

        List<String> phoneNumbersInFireStationArea = fireStationService.getPhoneNumbersInFireStationArea(stationNumber);
        // if there is no station with this id or list is empty send status and error message
        if (phoneNumbersInFireStationArea == null || phoneNumbersInFireStationArea.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire station with this id " + stationNumber + " don't exist or there is no persons on this area");

        return phoneNumbersInFireStationArea;
    }

    /**
     * Get perssons and station by address
     *
     * @param address address
     * @return List of persons and station id
     */
    @GetMapping(path = "fire")
    public PersonsAndStationByAddress getPersonsAndStationByAddress(@RequestParam() String address) {

        PersonsAndStationByAddress personsAndStationByAddress = personService.getPersonsAndStationByAddress(address);
        // if there is no persons or the address is wrong send status and error message
        if (personsAndStationByAddress == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "At this address " + address + " there is no persons or address is wrong");

        return personsAndStationByAddress;
    }

    /**
     * Get persons and addresses by stations ids
     *
     * @param stations list of stations
     * @return List of lists of persons sorted by addresses
     */
    @GetMapping(path = "flood/stations")
    public List<PersonsAndAddressesByStation> getPersonsAndAddressesByStations(@RequestParam() List<Integer> stations) {

        List<PersonsAndAddressesByStation> personsAndAddressesByStation = personService.getPersonsAndAddressesByStations(stations);

        // if there is no persons or station ids not exist send status and error message
        if (personsAndAddressesByStation == null || personsAndAddressesByStation.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire stations with this ids " + stations + " don't exist or there is no persons on this area");

        return personsAndAddressesByStation;
    }

    /**
     * Get persons info living in the place with searched person
     *
     * @param firstName first name
     * @param lastName  last name
     * @return List of persons
     */
    @GetMapping(path = "personInfo")
    public List<PersonInfo> getPersonsInfo(@RequestParam() String firstName, @RequestParam() String lastName) {

        List<PersonInfo> personsInfo = personService.getPersonsInfo(firstName, lastName);
        // if there is no person searched send status and error message
        if (personsInfo == null || personsInfo.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no persons with this name: " + lastName + " " + firstName + " at this address");

        return personsInfo;
    }

    /**
     * Get list of email addresses by city
     *
     * @param city City name
     * @return List of email addresses
     */
    @GetMapping(path = "communityEmail")
    public List<String> getPersonsInfo(@RequestParam() String city) {

        List<String> communityEmails = personService.getCommunityEmails(city);
        // if there is no email or city don't exist send status and error message
        if (communityEmails == null || communityEmails.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no persons living in this city: " + city + " or this city don't exist");

        return communityEmails;
    }
}
