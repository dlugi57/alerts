package com.safetynet.alerts.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FireStation {

    @NotBlank(message = "Please enter the address")
    private String address;
    @NotNull(message = "Please enter the station id")
    @Min(value = 1)
    private int station;

    public FireStation(@NotBlank(message = "Please enter the address") String address,@NotNull(message = "Please enter the station id") int station) {
        this.address = address;
        this.station = station;
    }

    public FireStation() {
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
        return "FireStation{" +
                "address='" + address + '\'' +
                ", station=" + station +
                '}';
    }
}
