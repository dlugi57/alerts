package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
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

/**
 * Fire station controller class which gives as possibilities of CRUD every fire station data
 */
@RestController
public class FireStationController {

    // Service initialization
    FireStationService fireStationService;


    /**
     * Class constructor with service initialization
     *
     * @param fireStationService init of fire station service
     */
    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    /**
     * Get fire station by station id
     *
     * @param station id of station
     * @return List of fireStations objects
     * @throws ResponseStatusException when list is empty or don't exist
     */
    @GetMapping(value = "firestations/{station}")
    public List<FireStation> getFireStationsByStationId(@PathVariable Integer station) throws ResponseStatusException {

        List<FireStation> fireStations = fireStationService.getFireStationsByStationId(station);
        // send error message when is empty
        if (fireStations == null || fireStations.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire stations with this id " + station + " don't exist");

        return fireStations;
    }

    /**
     * Get fire station by address
     *
     * @param address address of station
     * @return Fire station object
     * @throws ResponseStatusException when list is empty or don't exist
     */
    @GetMapping(value = "firestation/{address}")
    public FireStation getFireStationByStationAddress(@PathVariable() String address) throws ResponseStatusException {

        FireStation fireStation = fireStationService.getFireStationByStationAddress(address);
        // if station don't exist send error message
        if (fireStation == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fire station with this address " + address + " don't exist");

        return fireStation;
    }

    /**
     * Add fire station
     *
     * @param fireStation fire station object
     * @return link to the created object in header
     */
    @PostMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addFireStation(@Valid @RequestBody FireStation fireStation) {

        // if the station already exist with this address and id send error message
        if (!fireStationService.addFireStation(fireStation)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This station already exist");
        }

        // Create uri with new added fire station
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{address}")
                .buildAndExpand(fireStation.getAddress()).toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Update fire station
     *
     * @param fireStation Fire station object
     * @return status and url in the header of response
     */
    @PutMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> updateFireStation(@Valid @RequestBody FireStation fireStation) {

        // if fire station don't exist send error message
        if (!fireStationService.updateFireStation(fireStation)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This fire station don't exist");
        }

        // Create uri with updated fire station
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .queryParam("address", fireStation.getAddress()).build().toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Delete fire station
     *
     * @param fireStation fire station object
     */
    @DeleteMapping(value = "/firestation")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFireStation(@RequestBody FireStation fireStation) {
        // if fire station don't exist send error message
        if (!fireStationService.deleteFireStation(fireStation)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This fire station don't exist");
        }
    }

    /**
     * Get all fire stations
     *
     * @return List of fire stations
     */
    @GetMapping(value = "/firestations")
    public List<FireStation> getFireStations() {
        List<FireStation> fireStations = fireStationService.getFireStations();

        if (fireStations == null || fireStations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no fire station in the data base");
        }

        return fireStations;
    }
}
