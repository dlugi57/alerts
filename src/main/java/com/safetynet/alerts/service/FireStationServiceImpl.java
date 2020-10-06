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

/**
 * Fire station data manipulation
 */
@Service
public class FireStationServiceImpl implements FireStationService {

    // initialize objects
    //@Autowired
    FireStationDao fireStationDao;
    PersonDao personDao;
    MedicalRecordDao medicalRecordDao;

    // TODO: 06/10/2020 this method for injection was proposed because when field injection error shows in intellij apparently field injection is not recomended 

    /**
     * Field injection of fire station dao
     *
     * @param fireStationDao fire station dao
     */
    @Autowired
    public void setFireStationDao(FireStationDao fireStationDao) {
        this.fireStationDao = fireStationDao;
    }


    /**
     * Field injection of person dao
     *
     * @param personDao person dao
     */
    @Autowired
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    /**
     * Field injection of medical record dao
     *
     * @param medicalRecordDao medical record dao
     */
    @Autowired
    public void setMedicalRecordDao(MedicalRecordDao medicalRecordDao) {
        this.medicalRecordDao = medicalRecordDao;
    }


    /**
     * Get fire stations by station id
     *
     * @param id fire station id
     * @return List of fire stations
     */
    @Override
    public List<FireStation> getFireStationsByStationId(Integer id) {
        return fireStationDao.getFireStationsByStationId(id);
    }

    /**
     * Get all fire stations
     *
     * @return List of all fire stations
     */
    @Override
    public List<FireStation> getFireStations() {
        return fireStationDao.getFireStations();
    }

    /**
     * Get fire station by station id
     *
     * @param address Fire station address
     * @return Fire station object
     */
    @Override
    public FireStation getFireStationByStationAddress(String address) {
        return fireStationDao.getFireStationByStationAddress(address);
    }

    /**
     * Get fire station by station id and address
     *
     * @param station station id
     * @param address Fire station address
     * @return Fire station object
     */
    @Override
    public FireStation getFireStation(Integer station, String address) {
        return fireStationDao.getFireStation(station, address);
    }

    /**
     * Add fire station
     *
     * @param fireStation fire station object
     * @return true if success
     */
    @Override
    public boolean addFireStation(FireStation fireStation) {
        // verify if station exist
        FireStation checkFireStation = fireStationDao.getFireStation(fireStation.getStation(), fireStation.getAddress());
        if (checkFireStation == null) {
            fireStationDao.addFireStation(fireStation);
            return true;
        }
        return false;
    }

    /**
     * Update fire station
     *
     * @param fireStation fire station object
     * @return true if success
     */
    @Override
    public boolean updateFireStation(FireStation fireStation) {
        // check if this fire station exist
        FireStation checkFireStation = fireStationDao.getFireStationByStationAddress(fireStation.getAddress());
        if (checkFireStation != null) {
            fireStationDao.updateFireStation(fireStation);
            return true;
        }
        return false;
    }

    /**
     * Delete fire station
     *
     * @param fireStation fire station object
     * @return true if success
     */
    @Override
    public boolean deleteFireStation(FireStation fireStation) {

        // if there is only station id delete all addresses linked
        if (fireStation.getStation() != 0 && fireStation.getAddress() == null) {
            // check if station with this id exist
            List<FireStation> checkFireStations = fireStationDao.getFireStationsByStationId(fireStation.getStation());
            if (checkFireStations != null) {
                fireStationDao.deleteFireStationsByStation(fireStation.getStation());
                return true;
            }
            // if there is address in received data delete only one station with right address
        } else {
            // check if station exist by it's address
            FireStation checkFireStation = fireStationDao.getFireStationByStationAddress(fireStation.getAddress());
            if (checkFireStation != null) {
                fireStationDao.deleteFireStationByAddress(fireStation.getAddress());
                return true;
            }
        }
        return false;
    }

    /**
     * Get list of persons in fire station area
     *
     * @param stationNumber fire station id
     * @return List of persons and qty of children and adults
     */
    @Override
    public PersonsInFireStationArea getPersonsInFireStationArea(Integer stationNumber) {
        // get fire station
        List<FireStation> fireStations = fireStationDao.getFireStationsByStationId(stationNumber);
        // initialize DTO object
        PersonsInFireStationArea personsInFireStationArea = new PersonsInFireStationArea();

        // check if there is data
        if (fireStations != null && !fireStations.isEmpty()) {

            for (FireStation fireStation : fireStations) {
                String address = fireStation.getAddress();
                // get persons
                List<Person> persons = personDao.getPersonsByAddress(address);
                if (persons != null) {
                    for (Person person : persons) {
                        // initialize medical record by firstname and second name
                        MedicalRecord personMedicalRecord =
                                medicalRecordDao.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                        if (personMedicalRecord != null) {
                            // initialize age
                            int age = AgeCalculator.calculateAge(personMedicalRecord.getBirthdate());
                            // verify if is adult or not and increment value in existing object
                            if (age <= 18) {
                                personsInFireStationArea.setChildQty(personsInFireStationArea.getChildQty() + 1);
                            } else {
                                personsInFireStationArea.setAdultQty(personsInFireStationArea.getAdultQty() + 1);
                            }
                        }
                        // list of persons
                        List<PersonFireStation> personsInArea = personsInFireStationArea.getPersons();
                        // if there was no persons in list initialize it
                        if (personsInArea == null) {
                            personsInArea = new ArrayList<>();
                        }
                        // person from dto with selected data
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
            return personsInFireStationArea;

        }

        return null;
    }

    /**
     * Get all phone numbers in fire station area
     *
     * @param stationNumber fire station id
     * @return list of phone numbers
     */
    @Override
    public List<String> getPhoneNumbersInFireStationArea(Integer stationNumber) {
        // get fire stations
        List<FireStation> fireStations = fireStationDao.getFireStationsByStationId(stationNumber);
        // initialize phone number list
        List<String> phoneNumbersInFireStationArea = new ArrayList<>();

        if (fireStations != null) {

            for (FireStation fireStation : fireStations) {
                String address = fireStation.getAddress();
                // get persons
                List<Person> persons = personDao.getPersonsByAddress(address);
                if (persons != null) {
                    for (Person person : persons) {
                        phoneNumbersInFireStationArea.add(person.getPhone());
                    }
                }
            }

            return phoneNumbersInFireStationArea;

        }

        return null;
    }


}
