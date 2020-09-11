package com.safetynet.alerts.dto;

import java.util.List;

public class PersonsAndStationByAddress {
    private int station;
    private List<PersonFire> persons;

    public PersonsAndStationByAddress(int station, List<PersonFire> persons) {
        this.station = station;
        this.persons = persons;
    }

    public PersonsAndStationByAddress() {
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public List<PersonFire> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonFire> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "PersonsAndStationByAddress{" +
                "station=" + station +
                ", persons=" + persons +
                '}';
    }
}
