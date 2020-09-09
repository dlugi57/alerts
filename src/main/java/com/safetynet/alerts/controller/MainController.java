package com.safetynet.alerts.controller;


import com.safetynet.alerts.dto.PersonsInFireStationArea;
import com.safetynet.alerts.service.FireStationService;
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

    @Autowired
    public MainController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
        //personList = dataService.getDataAlert().getPersons();
    }

    //: prénom, nom, adresse, numéro de téléphone
    // sty adultes / qty childes
    @GetMapping(path = "firestation")
    public List<PersonsInFireStationArea> getPersonsInFireStationArea(@RequestParam(required = true) Integer stationNumber){

        List<PersonsInFireStationArea> personsInFireStationArea = fireStationService.getPersonsInFireStationArea(stationNumber);

        if (personsInFireStationArea == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire station with this id " + stationNumber + " don't exist or there is no persons on this area");

        return personsInFireStationArea;
    }



}
