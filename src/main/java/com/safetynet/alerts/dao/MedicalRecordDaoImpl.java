package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MedicalRecordDaoImpl implements MedicalRecordDao {

    private List<MedicalRecord> medicalRecordsList;

    @Autowired
    public MedicalRecordDaoImpl(DataService dataService) {
        medicalRecordsList = dataService.getDataAlert().getMedicalrecords();
    }

    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordsList;
    }


    @Override
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
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
        for (MedicalRecord existingMedicalRecord : medicalRecordsList) {
            if (existingMedicalRecord.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
                    && existingMedicalRecord.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {

                if (medicalRecord.getBirthdate() != null)
                    existingMedicalRecord.setBirthdate(medicalRecord.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                if (medicalRecord.getAllergies() != null)
                    existingMedicalRecord.setAllergies(medicalRecord.getAllergies());
                if (medicalRecord.getMedications() != null)
                    existingMedicalRecord.setMedications(medicalRecord.getMedications());

                return true;
            }

        }
        return false;
    }

    @Override
    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        for (MedicalRecord existingMedicalRecord : medicalRecordsList) {
            if (existingMedicalRecord.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
                    && existingMedicalRecord.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {
                medicalRecordsList.remove(existingMedicalRecord);
                return true;
            }

        }
        return false;
    }


}
