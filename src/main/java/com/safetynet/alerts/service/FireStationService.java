package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonsInFireStationArea;
import com.safetynet.alerts.model.FireStation;

import java.util.List;

public interface FireStationService {
    List<FireStation> getFireStationsByStationId(Integer id);

    List<FireStation> getFireStations();

    FireStation getFireStationByStationAddress(String address);

    boolean addFireStation(FireStation fireStation);

    FireStation getFireStation(Integer station, String address);

    boolean updateFireStation(FireStation fireStation);

    boolean deleteFireStation(FireStation fireStation);

    PersonsInFireStationArea getPersonsInFireStationArea(Integer stationNumber);
}
