package com.safetynet.alerts.model;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MedicalRecord {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @NotBlank(message = "Please enter the first name")
    private String firstName;

    @NotBlank(message = "Please enter the last name")
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String pBirthdate) {

        this.birthdate = LocalDate.parse(pBirthdate, dateTimeFormatter);
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
