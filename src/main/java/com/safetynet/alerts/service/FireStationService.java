package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;

import java.util.List;

public interface FireStationService {
    List<FireStation> getFireStationsByStationId(Integer id);

    List<FireStation> getFireStations();

    List<FireStation> getFireStationsByStationAddress(String address);

    boolean addFireStation(FireStation fireStation);

    FireStation getFireStation(Integer station, String address);

    boolean updateFireStation(FireStation fireStation, Integer newStation);

    boolean deleteFireStation(FireStation fireStation);
}
