package com.safetynet.alerts.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MedicalRecord {
//"firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984", "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"]
    //private int id;??

    private LocalDate birthdate;

    private List<String> medications;

    private List<String> allergies;


    public MedicalRecord(LocalDate birthdate, List<String> medications, List<String> allergies) {
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public MedicalRecord() {
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "birthdate=" + birthdate +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
