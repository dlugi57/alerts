package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
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

    @Autowired
    FireStationService fireStationService;

    @GetMapping(value = "firestations/{station}")
    public List<FireStation>  getFireStationsByStationId(@PathVariable Integer station) throws ResponseStatusException {

        List<FireStation> fireStations = fireStationService.getFireStationsByStationId(station);
        if (fireStations == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire stations with this id " + station + " don't exist");

        return fireStations;
    }

    @GetMapping(value = "firestations/{address}")
    public List<FireStation>  getFireStationsByStationAddress(@PathVariable String address) throws ResponseStatusException {

        List<FireStation> fireStations = fireStationService.getFireStationsByStationAddress(address);
        if (fireStations == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire stations with this address " + address + " don't exist");

        return fireStations;
    }

    @GetMapping(value = "firestation")
    public FireStation getFireStation(@RequestParam(required = true) Integer station, String address) throws ResponseStatusException {

        FireStation fireStation = fireStationService.getFireStation(station, address);
        if (fireStation == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire station No " + station + " and with address " + address + " don't exist");

        return fireStation;
    }

    @PostMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addFireStation(@Valid @RequestBody FireStation fireStation) {

        if (!fireStationService.addFireStation(fireStation)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This station already exist");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .queryParam("station", fireStation.getStation())
                .queryParam("address", fireStation.getAddress()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.CREATED)
    // TODO: 07/09/2020 i can add new request parameter
    public ResponseEntity<Void> updateFireStation( @RequestBody ObjectNode objectNode) {

        FireStation fireStation = new FireStation();

        fireStation.setAddress(objectNode.get("address").asText());
        fireStation.setStation(objectNode.get("station").asInt());
        Integer newStation = objectNode.get("newStation").asInt();

        if (!fireStationService.updateFireStation(fireStation, newStation)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This fire station don't exist");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .queryParam("station", newStation)
                .queryParam("address", fireStation.getAddress()).build().toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.OK)
    // TODO: 07/09/2020 how to do this in proper way
    public void deleteFireStation( @RequestBody FireStation fireStation) {

        if (!fireStationService.deleteFireStation(fireStation)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This person don't exist");
        }
    }

    @GetMapping(value = "/firestations")
    public List<FireStation>  getFireStations() {

        return fireStationService.getFireStations();
    }
}
