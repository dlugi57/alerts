package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component // or service
public class FireStationDaoImpl implements FireStationDao {

/*    @Autowired
    private DataService dataService;*/

    private List<FireStation> fireStations;

    @Autowired
    public FireStationDaoImpl(DataService dataService) {
        fireStations = dataService.getDataAlert().getFirestations();
    }

    @Override
    public List<FireStation> getFireStationsByStationId(Integer id) {

       // fireStations = dataService.getDataAlert().getFirestations();

        List<FireStation> filteredFireStations =  new ArrayList<FireStation>();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getStation() == id) {
                filteredFireStations.add(fireStation);
            }

        }

        return filteredFireStations;
    }

    @Override
    public FireStation getFireStationByStationAddress(String address) {

      //  fireStations = dataService.getDataAlert().getFirestations();

        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().replaceAll("\\s+","").equalsIgnoreCase(address.replaceAll("\\s+",""))) {
                 return fireStation;
            }
        }

            return null;


    }

    @Override
    public FireStation getFireStation(Integer station, String address) {
       // fireStations = dataService.getDataAlert().getFirestations();

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
    public boolean updateFireStation(FireStation fireStation) {
       // fireStations = dataService.getDataAlert().getFirestations();
        //Person person;
        for (FireStation existingFireStation : fireStations) {
            if (existingFireStation.getAddress().replaceAll("\\s+","")
                    .equalsIgnoreCase(fireStation.getAddress().replaceAll("\\s+",""))
                    ) {

                existingFireStation.setStation(fireStation.getStation());

                return true;
            }

        }
        return false;
    }

    @Override
    public List<FireStation> getFireStations() {
        return fireStations;
    }


}
