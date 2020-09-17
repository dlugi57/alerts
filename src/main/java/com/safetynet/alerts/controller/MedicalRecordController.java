package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
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
 * Medical records controller class which gives as possibilities of CRUD every fire medical record data
 */
@RestController
public class MedicalRecordController {

    // init of medical records service
    MedicalRecordService medicalRecordService;

    /**
     * Merdical record controller constructor
     *
     * @param medicalRecordService initialization of medical record service
     */
    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Get medical record by first and last name
     *
     * @param firstName First name
     * @param lastName  Last name
     * @return Medical record object
     */
    @GetMapping(value = "/medicalrecord")
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(@RequestParam() String firstName, String lastName) {

        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFirstNameAndLastName(firstName, lastName);
        // if there is no medical record send back status and error message
        if (medicalRecord == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person named " + firstName + " " + lastName + " don't exist");

        return medicalRecord;
    }

    /**
     * Add medical record
     *
     * @param medicalRecord medical record object
     * @return status and uri with new created medical record
     */
    @PostMapping(value = "/medicalrecord")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {

        // if medical record already exist send status and error message
        if (!medicalRecordService.addMedicalRecord(medicalRecord)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This medical record already exist");
        }

        // create url with new created medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", medicalRecord.getFirstName())
                .queryParam("lastName", medicalRecord.getLastName()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Update medical record
     *
     * @param medicalRecord meical record object
     * @return status and uri with updated medical record in response header
     */
    @PutMapping(value = "/medicalrecord")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updateMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {

        // if there is no medical record send status and error message
        if (!medicalRecordService.updateMedicalRecord(medicalRecord)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This medical record don't exist");
        }

        // create url with updated medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", medicalRecord.getFirstName())
                .queryParam("lastName", medicalRecord.getLastName()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Delete medical record
     *
     * @param medicalRecord medical record object
     */
    @DeleteMapping(value = "/medicalrecord")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
        // if there is no medical record send status and error message
        if (!medicalRecordService.deleteMedicalRecord(medicalRecord)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This medical record don't exist");
        }

    }

    /**
     * Get all medical records
     *
     * @return List of medical records
     */
    @GetMapping(value = "/medicalrecords")
    public List<MedicalRecord> getMedicalRecords() {

        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();

        // if there is no medical record in data base send error message
        if (medicalRecords == null || medicalRecords.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no medical records in the data base");
        }

        return medicalRecords;
    }


}
