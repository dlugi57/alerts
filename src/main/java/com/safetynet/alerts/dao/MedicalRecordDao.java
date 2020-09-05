package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface MedicalRecordDao {

    List<MedicalRecord> getMedicalRecords();


    MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

    boolean addMedicalRecord(MedicalRecord medicalRecord);

    boolean updateMedicalRecord(MedicalRecord medicalRecord);

    boolean deleteMedicalRecord(MedicalRecord medicalRecord);

}
