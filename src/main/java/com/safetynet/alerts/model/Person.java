package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import java.util.Objects;


public class Person {

    //"firstName":"John", "lastName":"Boyd", "address":"1509 Culver St", "city":"Culver", "zip":"97451", "phone":"841-874-6512", "email":"jaboyd@email.com"

    //set safety for invalid chaines
    // TODO: 05/09/2020 delete id from person object
    private int id;
    private static int personIdCounter = 0;


    @NotBlank(message = "Please enter the first name")
    private String firstName;

    @NotBlank(message = "Please enter the last name")
    private String lastName;

    @Length(min = 3, message = "Please provide correct address")
    private String address;

    private String city;

    private String zip;

    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    @JsonIgnoreProperties(value = { "firstName","lastName" })
    private MedicalRecord medicalRecord;

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email, MedicalRecord medicalRecord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.medicalRecord = medicalRecord;
    }

    public Person() {
        this.id= personIdCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                Objects.equals(address, person.address) &&
                Objects.equals(city, person.city) &&
                Objects.equals(zip, person.zip) &&
                Objects.equals(phone, person.phone) &&
                Objects.equals(email, person.email) &&
                Objects.equals(medicalRecord, person.medicalRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, city, zip, phone, email, medicalRecord);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", medicalRecord=" + medicalRecord +
                '}';
    }

}
