package com.safetynet.alerts.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.alerts.dto.ChildrenByAddress;
import com.safetynet.alerts.dto.PersonsInFireStationArea;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MainController {

    FireStationService fireStationService;
    PersonService personService;

    @Autowired
    public MainController(FireStationService fireStationService, PersonService personService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
        //personList = dataService.getDataAlert().getPersons();
    }

    //: prénom, nom, adresse, numéro de téléphone
    // sty adultes / qty childes
    @GetMapping(path = "firestation")
    public PersonsInFireStationArea getPersonsInFireStationArea(@RequestParam(required = true) Integer stationNumber){

        PersonsInFireStationArea personsInFireStationArea = fireStationService.getPersonsInFireStationArea(stationNumber);

        if (personsInFireStationArea == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire station with this id " + stationNumber + " don't exist or there is no persons on this area");




        return personsInFireStationArea;
    }

    @GetMapping(path = "childAlert")
    public ChildrenByAddress getChildrenByAddress(@RequestParam(required = true) String address){

        ChildrenByAddress childrenByAddress = personService.getChildrenByAddress(address);

        if (childrenByAddress == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "At this address " + address + " there is no persons at all");

        return childrenByAddress;
    }




}
