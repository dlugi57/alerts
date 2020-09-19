package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Initialization of medical records data with all crud methods
 */
@Component
public class MedicalRecordDaoImpl implements MedicalRecordDao {

    // list of medical records
    private List<MedicalRecord> medicalRecordsList;

    /**
     * Initialize all medical records
     *
     * @param dataService all data from file
     */
    @Autowired
    public MedicalRecordDaoImpl(DataService dataService) {
        medicalRecordsList = dataService.getDataAlert().getMedicalrecords();
    }

    /**
     * get all medical records
     *
     * @return List of medical records
     */
    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordsList;
    }

    /**
     * Get medical record by first and last name
     *
     * @param firstName first name
     * @param lastName  last name
     * @return medical record object
     */
    @Override
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        for (MedicalRecord medicalRecord : medicalRecordsList) {
            // check if first name and last name are the same without considering capitals
            if (medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
                return medicalRecord;
            }
        }

        return null;
    }

    /**
     * Add medical record
     *
     * @param medicalRecord medical record object
     * @return true if success
     */
    @Override
    public boolean addMedicalRecord(MedicalRecord medicalRecord) {

        medicalRecordsList.add(medicalRecord);

        return true;
    }

    /**
     * Update medical record
     *
     * @param medicalRecord medical record object
     * @return true if success
     */
    @Override
    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        for (MedicalRecord existingMedicalRecord : medicalRecordsList) {
            // check at firs obligatory parameters
            if (existingMedicalRecord.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
                    && existingMedicalRecord.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {
                // update only send data and if is null leave existing data
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

    /**
     * Delete medical record
     *
     * @param medicalRecord medical record object
     * @return true if success
     */
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
