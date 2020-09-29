package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    static final Logger logger = LogManager
            .getLogger(MedicalRecordController.class);

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
        if (medicalRecord == null) {
            logger.error("GET medicalrecord -> getMedicalRecordByFirstNameAndLastName /**/ " +
                    "HttpStatus : " + HttpStatus.NOT_FOUND + " /**/ Message : Person named " + firstName + " " + lastName + " don't exist");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person named " + firstName + " " + lastName + " don't exist");
        }

        logger.info("GET medicalrecord -> getMedicalRecordByFirstNameAndLastName /**/ HttpStatus " +
                ": " + HttpStatus.OK + " /**/ Result : '{}'.", medicalRecord.toString());

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
            logger.error("POST medicalrecord -> " +
                    "addMedicalRecord /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message :  This medical record already exist");

            throw new ResponseStatusException(HttpStatus.CONFLICT, "This medical record already exist");
        }

        // create url with new created medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", medicalRecord.getFirstName())
                .queryParam("lastName", medicalRecord.getLastName()).build().toUri();

        logger.info("POST medicalrecord -> addMedicalRecord /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ Result : '{}'.", location);

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
            logger.error("POST medicalrecord -> " + "updateMedicalRecord /**/ HttpStatus : " + HttpStatus.CONFLICT + " /**/ Message :  This medical record don't exist");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This medical record don't exist");
        }

        // create url with updated medical record
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").queryParam("firstName", medicalRecord.getFirstName())
                .queryParam("lastName", medicalRecord.getLastName()).build().toUri();

        logger.info("POST medicalrecord -> updateMedicalRecord /**/ HttpStatus : " + HttpStatus.CREATED + " /**/ Result : '{}'.", location);

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
            logger.error("DELETE medicalrecord -> deleteMedicalRecord /**/ Result : " + HttpStatus.NOT_FOUND
                    + " /**/ Message : This medical record don't exist : '{}'.", medicalRecord.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This medical record don't exist");
        }
        logger.info("DELETE medicalrecord -> deleteMedicalRecord /**/ HttpStatus : " + HttpStatus.OK);
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
            logger.error("GET medicalrecords -> getMedicalRecords /**/ Result : " + HttpStatus.NOT_FOUND + " /**/ Message : There is no medical records in the data base");

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no medical records in the data base");
        }
        logger.info("GET medicalrecords -> getMedicalRecords /**/ HttpStatus : " + HttpStatus.OK + " /**/ Result : '{}'.", medicalRecords.toString());

        return medicalRecords;
    }


}
