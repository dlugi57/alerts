package com.safetynet.alerts.controller;


import com.safetynet.alerts.dto.PersonsInFireStationArea;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    //: prénom, nom, adresse, numéro de téléphone
    // sty adultes / qty childes
    @GetMapping(path = "firestation")
    public List<PersonsInFireStationArea> getPersonsInFireStationArea(@RequestParam(required = true) Integer stationNumber){


        return null;
    }



}
