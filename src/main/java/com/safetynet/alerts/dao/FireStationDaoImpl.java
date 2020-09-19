package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Initialization of data for fire station with all CRUD methods
 * using interface
 */
@Component
public class FireStationDaoImpl implements FireStationDao {

    // list of fire stations
    private List<FireStation> fireStations;

    //

    /**
     * initialization of data to the fire station list
     *
     * @param dataService whole data from file
     */
    @Autowired
    public FireStationDaoImpl(DataService dataService) {
        fireStations = dataService.getDataAlert().getFirestations();
    }

    /**
     * Get fire station by station id
     *
     * @param id id of fire station
     * @return list of fire stations
     */
    @Override
    public List<FireStation> getFireStationsByStationId(Integer id) {

        List<FireStation> filteredFireStations = new ArrayList<>();
        for (FireStation fireStation : fireStations) {
            // if found id in list set it into return list
            if (fireStation.getStation() == id) {
                filteredFireStations.add(fireStation);
            }
        }

        return filteredFireStations;
    }

    /**
     * Get fire station by address
     *
     * @param address address of fire station
     * @return fire station object
     */
    @Override
    public FireStation getFireStationByStationAddress(String address) {

        for (FireStation fireStation : fireStations) {
            // compare fire station address in the list without taking care of capitalizing or white spaces
            if (fireStation.getAddress().replaceAll("\\s+", "").equalsIgnoreCase(address.replaceAll("\\s+", ""))) {
                return fireStation;
            }
        }

        return null;
    }

    /**
     * Get fire station by station id and address
     *
     * @param station station id
     * @param address fires station address
     * @return fire station object
     */
    @Override
    public FireStation getFireStation(Integer station, String address) {

        for (FireStation fireStation : fireStations) {
            // check the address without taking care of capitalizing or white spaces
            if (fireStation.getAddress().replaceAll("\\s+", "")
                    .equalsIgnoreCase(address.replaceAll("\\s+", "")) && fireStation.getStation() == station) {
                return fireStation;
            }
        }

        return null;

    }

    /**
     * Add fire station
     *
     * @param fireStation fire station object
     * @return true if is done
     */
    @Override
    public boolean addFireStation(FireStation fireStation) {

        fireStations.add(fireStation);

        return true;
    }

    /**
     * Update fire station
     *
     * @param fireStation fire station object
     * @return true when is done
     */
    @Override
    public boolean updateFireStation(FireStation fireStation) {
        // find existing station
        for (FireStation existingFireStation : fireStations) {
            // check the address without taking care of capitalizing or white spaces
            if (existingFireStation.getAddress().replaceAll("\\s+", "")
                    .equalsIgnoreCase(fireStation.getAddress().replaceAll("\\s+", ""))
            ) {
                // change station id
                existingFireStation.setStation(fireStation.getStation());
                return true;
            }

        }
        return false;
    }

    /**
     * Delete fire station by station id
     *
     * @param station station id
     */
    @Override
    public void deleteFireStationsByStation(int station) {

        for (FireStation fireStation : new ArrayList<>(fireStations)) {
            if (fireStation.getStation() == station) {
                fireStations.remove(fireStation);
            }
        }
    }

    /**
     * Delete fire station by station address
     *
     * @param address fire station address
     */
    @Override
    public void deleteFireStationByAddress(String address) {
        for (FireStation fireStation : fireStations) {
            // check the address without taking care of capitalizing or white spaces
            if (fireStation.getAddress().replaceAll("\\s+", "")
                    .equalsIgnoreCase(address.replaceAll("\\s+", ""))
            ) {
                fireStations.remove(fireStation);
                return;
            }

        }
    }

    /**
     * Get all fire stations
     *
     * @return list of all existing fire stations
     */
    @Override
    public List<FireStation> getFireStations() {
        return fireStations;
    }

}
