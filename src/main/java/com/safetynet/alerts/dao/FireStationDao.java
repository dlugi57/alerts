package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.FireStation;

import java.util.List;

public interface FireStationDao {
    List<FireStation> getFireStationsByStationId(Integer id);

    List<FireStation> getFireStations();

    FireStation getFireStationByStationAddress(String address);

    FireStation getFireStation(Integer station, String address);

    boolean addFireStation(FireStation fireStation);

    boolean updateFireStation(FireStation fireStation);

    void deleteFireStationsByStation(int station);

    void deleteFireStationByAddress(String address);
}
