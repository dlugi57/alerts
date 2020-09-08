package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MedicalRecordDaoImpl implements MedicalRecordDao {

    @Autowired
    private DataService dataService;

    private List<MedicalRecord> medicalRecordsList;

    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordsList = dataService.getDataAlert().getMedicalrecords();
    }







    @Override
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        medicalRecordsList = dataService.getDataAlert().getMedicalrecords();
        //Person person;
        for (MedicalRecord medicalRecord : medicalRecordsList) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
                return medicalRecord;
            }

        }

        return null;
    }




    @Override
    public boolean addMedicalRecord(MedicalRecord medicalRecord) {

        medicalRecordsList.add(medicalRecord);

        return true;
    }







    @Override
    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordsList = dataService.getDataAlert().getMedicalrecords();
        //Person person;
        for (MedicalRecord existingMedicalRecord : medicalRecordsList) {
            if (existingMedicalRecord.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
                    && existingMedicalRecord.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {

                if (medicalRecord.getBirthdate() != null)existingMedicalRecord.setBirthdate(medicalRecord.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                if (medicalRecord.getAllergies() != null)existingMedicalRecord.setAllergies(medicalRecord.getAllergies());
                if (medicalRecord.getMedications() != null)existingMedicalRecord.setMedications(medicalRecord.getMedications());

                return true;
            }

        }
        return false;
    }

    @Override
    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordsList = dataService.getDataAlert().getMedicalrecords();
        for (MedicalRecord existingMedicalRecord : medicalRecordsList) {
            if (existingMedicalRecord.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
                    && existingMedicalRecord.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {
                // TODO: 05/09/2020 if that is a correct way to remove ?
                medicalRecordsList.remove(existingMedicalRecord);
                return true;
            }

        }
        return false;
    }




}
