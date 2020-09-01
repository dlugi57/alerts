package com.safetynet.alerts.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class MedicalRecord {
//"firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984", "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"]
    //private int id;??


     DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private String firstName;

    private String lastName;

    private LocalDate birthdate;

    private List<String> medications;

    private List<String> allergies;


    public MedicalRecord(String firstName, String lastName, final String pBirthdate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = LocalDate.parse(pBirthdate, dateTimeFormatter);
        this.medications = medications;
        this.allergies = allergies;
    }

    public MedicalRecord() {
    }

    //todo convert string to date
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String pBirthdate) {

        this.birthdate = LocalDate.parse(pBirthdate, dateTimeFormatter);
        //this.birthdate = birthdate;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
