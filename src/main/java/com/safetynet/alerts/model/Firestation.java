package com.safetynet.alerts.model;

public class Firestation {

    //"address":"1509 Culver St", "station":"3"
    private String address;

    private int station;

    public Firestation(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public Firestation() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Firestation{" +
                "address='" + address + '\'' +
                ", station=" + station +
                '}';
    }
}
