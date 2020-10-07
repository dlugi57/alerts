package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Medical record data manipulation
 */
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    // initialize medical record object
    MedicalRecordDao medicalRecordDao;

    /**
     * Field injection of medical record dao
     *
     * @param medicalRecordDao medical records data
     */
    @Autowired
    public void setMedicalRecordDao(MedicalRecordDao medicalRecordDao) {
        this.medicalRecordDao = medicalRecordDao;
    }

    /**
     * Get all medical records
     *
     * @return List of medical records
     */
    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordDao.getMedicalRecords();
    }

    /**
     * Add medical record
     *
     * @param medicalRecord medical record object
     * @return true when success
     */
    @Override
    public boolean addMedicalRecord(MedicalRecord medicalRecord) {
        // check if this medical record already exist
        MedicalRecord checkMedicalRecord = medicalRecordDao.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (checkMedicalRecord == null) {
            medicalRecordDao.addMedicalRecord(medicalRecord);
            return true;
        }
        return false;

    }

    /**
     * Get medical record by first and last name
     *
     * @param firstName First name
     * @param lastName  Last name
     * @return Medical record
     */
    @Override
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        return medicalRecordDao.getMedicalRecordByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Update medical record
     *
     * @param medicalRecord medical record object
     * @return true when success
     */
    @Override
    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {

        // check if this medical record exist
        MedicalRecord checkMedicalRecord = medicalRecordDao.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (checkMedicalRecord != null) {
            medicalRecordDao.updateMedicalRecord(medicalRecord);
            return true;
        }

        return false;
    }

    /**
     * Delete medical record
     *
     * @param medicalRecord medical record object
     * @return true when success
     */
    @Override
    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        // check if this medical record exist
        MedicalRecord checkMedicalRecord = medicalRecordDao.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (checkMedicalRecord != null) {
            medicalRecordDao.deleteMedicalRecord(medicalRecord);
            return true;
        }

        return false;
    }


}
