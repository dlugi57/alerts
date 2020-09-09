package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {

    @Autowired
    FireStationDao fireStationDao;

    @Override
    public List<FireStation> getFireStationsByStationId(Integer id) {

        return fireStationDao.getFireStationsByStationId(id);
    }

    @Override
    public List<FireStation> getFireStations() {
        return fireStationDao.getFireStations();
    }

    @Override
    public FireStation getFireStationByStationAddress(String address) {
        return fireStationDao.getFireStationByStationAddress(address);
    }

    @Override
    public FireStation getFireStation(Integer station, String address) {
        return fireStationDao.getFireStation(station, address);
    }

    @Override
    public boolean addFireStation(FireStation fireStation) {
        FireStation checkFireStation = fireStationDao.getFireStation(fireStation.getStation(), fireStation.getAddress());
        if (checkFireStation == null) {

            fireStationDao.addFireStation(fireStation);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateFireStation(FireStation fireStation) {
        FireStation checkFireStation = fireStationDao.getFireStationByStationAddress(fireStation.getAddress());
        if (checkFireStation != null) {
            fireStationDao.updateFireStation(fireStation);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFireStation(FireStation fireStation) {

        // TODO: 08/09/2020 make three checks and then 3 deletes

        if (fireStation.getStation() != 0 && fireStation.getAddress() == null) {
            List<FireStation> checkFireStations = fireStationDao.getFireStationsByStationId(fireStation.getStation());
            if (checkFireStations != null) {
                fireStationDao.deleteFireStationsByStation(fireStation.getStation());
                return true;
            }

        } else {
            FireStation checkFireStation = fireStationDao.getFireStationByStationAddress(fireStation.getAddress());
            if (checkFireStation != null) {
                fireStationDao.deleteFireStationByAddress(fireStation.getAddress());
                return true;
            }


        }





        return false;
    }


}
