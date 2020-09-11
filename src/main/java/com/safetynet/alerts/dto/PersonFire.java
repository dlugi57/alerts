package com.safetynet.alerts.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class PersonFire {


    private String lastName;

    private String phone;

    private String age;

    @Email(message = "Email should be valid")
    private String email;

    private List<String> medications;

    private List<String> allergies;

    public PersonFire(String lastName, String phone, String age, @Email(message = "Email should be valid") String email, List<String> medications, List<String> allergies) {
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.email = email;
        this.medications = medications;
        this.allergies = allergies;
    }

    public PersonFire() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "PersonFire{" +
                "lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
