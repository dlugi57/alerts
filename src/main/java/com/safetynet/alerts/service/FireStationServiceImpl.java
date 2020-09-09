package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.PersonsInFireStationArea;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.AgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {


    FireStationDao fireStationDao;
    PersonDao personDao;
    MedicalRecordDao medicalRecordDao;

    @Autowired
    public FireStationServiceImpl(FireStationDao fireStationDao, PersonDao personDao, MedicalRecordDao medicalRecordDao){
        this.fireStationDao = fireStationDao;
        this.personDao = personDao;
        this.medicalRecordDao = medicalRecordDao;
    }

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

    @Override
    public List<PersonsInFireStationArea> getPersonsInFireStationArea(Integer stationNumber) {

        List<FireStation> fireStations = fireStationDao.getFireStationsByStationId(stationNumber);
        List<PersonsInFireStationArea> personsInFireStationArea = new ArrayList<PersonsInFireStationArea>();

        Integer childQty;
        Integer adultQty;

        if (fireStations != null) {

            for (FireStation fireStation : fireStations){
                String address = fireStation.getAddress();

                List<Person> persons = personDao.getPersonsByAddress(address);
                if ( persons!= null){
                    for (Person person : persons){
                        MedicalRecord personMedicalRecord =
                                medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                        if (personMedicalRecord != null){

                            Integer age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());


                            //personMedicalRecord
                        }


                    }
                }



            }



            return null;
        }

        return null;
    }


}
