package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
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
    public List<FireStation> getFireStationsByStationAddress(String address) {
        return fireStationDao.getFireStationsByStationAddress(address);
    }

    @Override
    public FireStation getFireStation(Integer station, String address) {
        return fireStationDao.getFireStation(station, address);
    }

    @Override
    public boolean addFireStation(FireStation fireStation) {
        FireStation checkFireStation = fireStationDao.getFireStation(fireStation.getStation(), fireStation.getAddress());
        if (checkFireStation == null){

            fireStationDao.addFireStation(fireStation);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateFireStation(FireStation fireStation, Integer newStation) {
        FireStation checkFireStation = fireStationDao.getFireStation(fireStation.getStation(), fireStation.getAddress());
        if (checkFireStation != null){

            fireStationDao.updateFireStation(fireStation, newStation);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFireStation(FireStation fireStation) {

        // TODO: 08/09/2020 make three checks and then 3 delets


/*        Person checkPerson = personDao.getByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (checkPerson != null){
            personDao.deletePerson(person);
            return true;
        }*/

        return false;
    }


}
