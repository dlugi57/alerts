package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
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
public class MedicalRecordController {



    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping(value = "/medicalrecord")
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(@RequestParam(required = true) String firstName, String lastName) throws ResponseStatusException {

        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFirstNameAndLastName(firstName, lastName);
        if (medicalRecord == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person named  " + firstName + " " + lastName + " don't exist");

        return medicalRecord;
    }

    @PostMapping(value = "/medicalrecord")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {

        if (!medicalRecordService.addMedicalRecord(medicalRecord)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This medical record already exist");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", medicalRecord.getFirstName())
                .queryParam("lastName", medicalRecord.getLastName()).build().toUri();
        return ResponseEntity.created(location).build();
    }







    @PutMapping(value = "/medicalrecord")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updateMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {

        if (!medicalRecordService.updateMedicalRecord(medicalRecord)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This medical record don't exist");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", medicalRecord.getFirstName())
                .queryParam("lastName", medicalRecord.getLastName()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/medicalrecord")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
//unique???
        if (!medicalRecordService.deleteMedicalRecord(medicalRecord)) {
            throw new ResponseStatusException(HttpStatus.FOUND, "This medical record don't exist");
        }

    }

    @GetMapping(value = "/medicalrecords")
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }




}
