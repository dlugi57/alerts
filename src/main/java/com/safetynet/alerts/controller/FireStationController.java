package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.DataService;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class FireStationController {

    //@Autowired
    FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
        //personList = dataService.getDataAlert().getPersons();
    }


    @GetMapping(value = "firestations/{station}")
    public List<FireStation> getFireStationsByStationId(@PathVariable Integer station) throws ResponseStatusException {

        List<FireStation> fireStations = fireStationService.getFireStationsByStationId(station);
        if (fireStations == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire stations with this id " + station + " don't exist");

        return fireStations;
    }

    @GetMapping(value = "firestation/{address}")
    public FireStation getFireStationByStationAddress(@PathVariable() String address) throws ResponseStatusException {

        FireStation fireStation = fireStationService.getFireStationByStationAddress(address);
        if (fireStation == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire station with this address " + address + " don't exist");

        return fireStation;
    }

    @PostMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addFireStation(@Valid @RequestBody FireStation fireStation) {

        if (!fireStationService.addFireStation(fireStation)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This station already exist");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{address}")
                //.queryParam("address", fireStation.getAddress()).build().toUri();
                .buildAndExpand(fireStation.getAddress()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updateFireStation(@Valid @RequestBody FireStation fireStation) {


        if (!fireStationService.updateFireStation(fireStation)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This fire station don't exist");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .queryParam("address", fireStation.getAddress()).build().toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFireStation(@RequestBody FireStation fireStation) {

        if (!fireStationService.deleteFireStation(fireStation)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This fire station don't exist");
        }
    }

    @GetMapping(value = "/firestations")
    public List<FireStation> getFireStations() {

        return fireStationService.getFireStations();
    }
}
