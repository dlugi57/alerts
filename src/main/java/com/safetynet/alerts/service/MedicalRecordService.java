package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

    boolean addMedicalRecord(MedicalRecord medicalRecord);

    boolean updateMedicalRecord(MedicalRecord medicalRecord);

    List<MedicalRecord> getMedicalRecords();

    boolean deleteMedicalRecord(MedicalRecord medicalRecord);
}
