package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component // or service
public class FireStationDaoImpl implements FireStationDao {

    @Autowired
    private DataService dataService;

    private List<FireStation> fireStations;


    @Override
    public List<FireStation> getFireStationsByStationId(Integer id) {

        fireStations = dataService.getDataAlert().getFirestations();

        List<FireStation> filteredFireStations =  new ArrayList<FireStation>();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getStation() == id) {
                filteredFireStations.add(fireStation);
            }

        }

        return filteredFireStations;
    }

    @Override
    public List<FireStation> getFireStationsByStationAddress(String address) {

        fireStations = dataService.getDataAlert().getFirestations();

        List<FireStation> filteredFireStations =  new ArrayList<FireStation>();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().replaceAll("\\s+","").equalsIgnoreCase(address.replaceAll("\\s+",""))) {
                filteredFireStations.add(fireStation);
            }
        }
        if (filteredFireStations.isEmpty()){
            return null;
        }
        return filteredFireStations;
    }

    @Override
    public FireStation getFireStation(Integer station, String address) {
        fireStations = dataService.getDataAlert().getFirestations();
        // TODO: 07/09/2020 this i possible to check if list contains object ? if (fireStations.contains(fireStation)){

        for (FireStation fireStation : fireStations){
            if (fireStation.getAddress().replaceAll("\\s+","")
                    .equalsIgnoreCase(address.replaceAll("\\s+","")) && fireStation.getStation() == station){
                return fireStation;
            }
        }

        return null;

    }

    @Override
    public boolean addFireStation(FireStation fireStation) {

        fireStations.add(fireStation);

        return true;
    }

    @Override
    public boolean updateFireStation(FireStation fireStation, Integer newStation) {
        fireStations = dataService.getDataAlert().getFirestations();
        //Person person;
        for (FireStation existingFireStations : fireStations) {
            if (existingFireStations.getAddress().replaceAll("\\s+","")
                    .equalsIgnoreCase(fireStation.getAddress().replaceAll("\\s+",""))
                    && existingFireStations.getStation() == fireStation.getStation()) {

                existingFireStations.setStation(newStation);

                return true;
            }

        }
        return false;
    }

    @Override
    public List<FireStation> getFireStations() {
        return dataService.getDataAlert().getFirestations();
    }


}
