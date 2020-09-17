package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.PersonFireStation;
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
    public FireStationServiceImpl(FireStationDao fireStationDao, PersonDao personDao, MedicalRecordDao medicalRecordDao) {
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
    public PersonsInFireStationArea getPersonsInFireStationArea(Integer stationNumber) {

        List<FireStation> fireStations = fireStationDao.getFireStationsByStationId(stationNumber);
        PersonsInFireStationArea personsInFireStationArea = new PersonsInFireStationArea();


        if (fireStations != null) {

            for (FireStation fireStation : fireStations) {
                String address = fireStation.getAddress();

                List<Person> persons = personDao.getPersonsByAddress(address);
                if (persons != null) {
                    for (Person person : persons) {

                        MedicalRecord personMedicalRecord =
                                medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                        if (personMedicalRecord != null) {

                            Integer age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());

                            if (age <= 18) {
                                personsInFireStationArea.setChildQty(personsInFireStationArea.getChildQty() + 1);

                            } else {
                                personsInFireStationArea.setAdultQty(personsInFireStationArea.getAdultQty() + 1);

                            }
                        }

                        List<PersonFireStation> personsInArea = personsInFireStationArea.getPersons();
                        if (personsInArea == null) {
                            personsInArea = new ArrayList<PersonFireStation>();
                        }

                        PersonFireStation personFireStation = new PersonFireStation();

                        personFireStation.setFirstName(person.getFirstName());
                        personFireStation.setLastName(person.getLastName());
                        personFireStation.setAddress(person.getAddress());
                        personFireStation.setPhone(person.getPhone());

                        personsInArea.add(personFireStation);
                        personsInFireStationArea.setPersons(personsInArea);


                    }

                }


            }

            if (personsInFireStationArea != null) {
                return personsInFireStationArea;
            }

        }

        return null;
    }

    @Override
    public List<String> getPhoneNumbersInFireStationArea(Integer stationNumber) {
        List<FireStation> fireStations = fireStationDao.getFireStationsByStationId(stationNumber);
        List<String> phoneNumbersInFireStationArea = new ArrayList<>();

        if (fireStations != null) {

            for (FireStation fireStation : fireStations) {
                String address = fireStation.getAddress();

                List<Person> persons = personDao.getPersonsByAddress(address);
                if (persons != null) {
                    for (Person person : persons) {
                        phoneNumbersInFireStationArea.add(person.getPhone());
                    }

                }


            }

            if (phoneNumbersInFireStationArea != null) {
                return phoneNumbersInFireStationArea;
            }

        }

        return null;
    }


}
