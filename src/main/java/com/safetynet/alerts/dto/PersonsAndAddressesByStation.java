package com.safetynet.alerts.dto;

import java.util.List;

public class PersonsAndAddressesByStation {
    private String address;
    private List<PersonFire> persons;

    public PersonsAndAddressesByStation(String address, List<PersonFire> persons) {
        this.address = address;
        this.persons = persons;
    }

    public PersonsAndAddressesByStation() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonFire> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonFire> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "PersonsAndAddressesByStation{" +
                "address='" + address + '\'' +
                ", persons=" + persons +
                '}';
    }
}
