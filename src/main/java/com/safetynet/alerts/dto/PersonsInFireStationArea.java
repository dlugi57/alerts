package com.safetynet.alerts.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.safetynet.alerts.model.Person;

import java.util.List;

public class PersonsInFireStationArea {
    private int childQty;
    private int adultQty;


    private List<PersonFireStation> persons;

    public PersonsInFireStationArea(int childQty, int adultQty, List<PersonFireStation> persons) {
        this.childQty = childQty;
        this.adultQty = adultQty;
        this.persons = persons;
    }

    public PersonsInFireStationArea() {

    }

    public int getChildQty() {
        return childQty;
    }

    public void setChildQty(int childQty) {
        this.childQty = childQty;
    }

    public int getAdultQty() {
        return adultQty;
    }

    public void setAdultQty(int adultQty) {
        this.adultQty = adultQty;
    }

    public List<PersonFireStation> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonFireStation> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "PersonsInFireStationArea{" +
                "childQty=" + childQty +
                ", adultQty=" + adultQty +
                ", persons=" + persons +
                '}';
    }
}
