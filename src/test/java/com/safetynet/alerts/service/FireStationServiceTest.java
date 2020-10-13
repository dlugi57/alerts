package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.PersonFireStation;
import com.safetynet.alerts.dto.PersonsInFireStationArea;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FireStationServiceTest {

    @TestConfiguration
    static class FireStationServiceImplTestContextConfiguration {
        @Bean
        public FireStationService fireStationService() {
            return new FireStationServiceImpl();
        }
    }

    @Resource
    FireStationService fireStationService;
    @MockBean
    FireStationDao fireStationDao;
    @MockBean
    PersonDao personDao;
    @MockBean
    MedicalRecordDao medicalRecordDao;

    public static List<FireStation> fireStations = new ArrayList<>();

    static {
        fireStations.add(new FireStation("644 Gershwin Cir", 1));
        fireStations.add(new FireStation("908 73rd St", 1));
        fireStations.add(new FireStation("947 E. Rose Dr", 1));
    }

    public static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new Person("John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person("Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person("Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    static {
        medicalRecordList.add(new MedicalRecord("John", "Boyd", "03/06/1984",
                new ArrayList<>(), new ArrayList<>()));
        medicalRecordList.add(new MedicalRecord(
                "Jacob", "Boyd", "03/06/2015", new ArrayList<>(), new ArrayList<>()));
        medicalRecordList.add(new MedicalRecord("Tenley", "Boyd",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
    }

    public static List<PersonFireStation> personsFireStation = new ArrayList<>();

    static {
        personsFireStation.add(new PersonFireStation("Peter", "Duncan",
                "644 Gershwin Cir", "841-874-6512"));
        personsFireStation.add(new PersonFireStation("Reginold", "Walker",
                "908 73rd St", "841-874-8547"));
        personsFireStation.add(new PersonFireStation("Jamie", "Peters",
                "908 73rd St", "841-874-7462"));
        personsFireStation.add(new PersonFireStation("Brian", "Stelzer",
                "947 E. Rose Dr", "841-874-7784"));
        personsFireStation.add(new PersonFireStation("Shawna", "Stelzer",
                "947 E. Rose Dr", "841-874-7784"));
        personsFireStation.add(new PersonFireStation("Kendrik", "Stelzer",
                "947 E. Rose Dr", "841-874-7784"));
    }

    public static List<String> phoneNumbersInFireStationArea = new ArrayList<>();

    static {
        for (PersonFireStation personFireStation : personsFireStation) {
            phoneNumbersInFireStationArea.add(personFireStation.getPhone());
        }
    }

    @Test
    void getFireStationsByStationId() {
        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(fireStations);
        // WHEN
        List<FireStation> fireStationsById = fireStationService.getFireStationsByStationId(1);
        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());
        assertThat(fireStationsById.size()).isEqualTo(3);
        assertThat(fireStationsById).isEqualTo(fireStations);
        assertThat(fireStationsById.get(0).getAddress()).isEqualTo("644 Gershwin Cir");
        assertThat(fireStationsById.get(0).getStation()).isEqualTo(1);
    }

    @Test
    void getFireStations() {
        // GIVEN
        given(fireStationDao.getFireStations()).willReturn(fireStations);
        // WHEN
        List<FireStation> fireStationsAll = fireStationService.getFireStations();
        // THEN
        verify(fireStationDao, times(1)).getFireStations();
        assertThat(fireStationsAll.size()).isEqualTo(3);
        assertThat(fireStationsAll).isEqualTo(fireStations);
        assertThat(fireStationsAll.get(0).getAddress()).isEqualTo("644 Gershwin Cir");
        assertThat(fireStationsAll.get(0).getStation()).isEqualTo(1);
    }

    @Test
    void getFireStationByStationAddress() {
        // GIVEN
        given(fireStationDao.getFireStationByStationAddress(anyString())).willReturn(fireStations.get(0));
        // WHEN
        FireStation fireStationByAddress = fireStationService.getFireStationByStationAddress("644" +
                " Gershwin Cir");
        // THEN
        verify(fireStationDao, times(1)).getFireStationByStationAddress(anyString());
        assertThat(fireStationByAddress).isEqualTo(fireStations.get(0));
        assertThat(fireStationByAddress.getAddress()).isEqualTo("644 Gershwin Cir");
        assertThat(fireStationByAddress.getStation()).isEqualTo(1);
    }

    @Test
    void addFireStation() {
        // GIVEN
        given(fireStationDao.getFireStation(anyInt(), anyString())).willReturn(null);

        given(fireStationDao.addFireStation(any(FireStation.class))).willReturn(true);
        // WHEN
        boolean fireStation = fireStationService.addFireStation(new FireStation("644 Gershwin " +
                "Cir1", 1));
        // THEN
        verify(fireStationDao, times(1)).getFireStation(anyInt(), anyString());
        verify(fireStationDao, times(1)).addFireStation(any(FireStation.class));

        assertThat(fireStation).isEqualTo(true);
    }

    @Test
    void addFireStation_AlreadyExist() {
        // GIVEN
        given(fireStationDao.getFireStation(anyInt(), anyString())).willReturn(fireStations.get(0));

        // WHEN
        boolean fireStation = fireStationService.addFireStation(new FireStation("644 Gershwin " +
                "Cir", 1));
        // THEN
        verify(fireStationDao, times(1)).getFireStation(anyInt(), anyString());

        assertThat(fireStation).isEqualTo(false);
    }

    @Test
    void getFireStation() {
        // GIVEN
        given(fireStationDao.getFireStation(anyInt(), anyString())).willReturn(fireStations.get(0));
        // WHEN
        FireStation fireStationByAddress = fireStationService.getFireStation(1, "644" +
                " Gershwin Cir");
        // THEN
        verify(fireStationDao, times(1)).getFireStation(anyInt(), anyString());
        assertThat(fireStationByAddress).isEqualTo(fireStations.get(0));
        assertThat(fireStationByAddress.getAddress()).isEqualTo("644 Gershwin Cir");
        assertThat(fireStationByAddress.getStation()).isEqualTo(1);
    }

    @Test
    void updateFireStation() {
        // GIVEN
        given(fireStationDao.getFireStationByStationAddress(anyString())).willReturn(fireStations.get(0));

        given(fireStationDao.updateFireStation(any(FireStation.class))).willReturn(true);
        // WHEN
        boolean fireStation = fireStationService.updateFireStation(new FireStation("644 Gershwin " +
                "Cir1", 1));
        // THEN
        verify(fireStationDao, times(1)).getFireStationByStationAddress(anyString());
        verify(fireStationDao, times(1)).updateFireStation(any(FireStation.class));

        assertThat(fireStation).isEqualTo(true);
    }

    @Test
    void updateFireStation_DontExist() {
        // GIVEN
        given(fireStationDao.getFireStationByStationAddress(anyString())).willReturn(null);

        // WHEN
        boolean fireStation = fireStationService.updateFireStation(new FireStation("644 Gershwin " +
                "Cir1", 1));
        // THEN
        verify(fireStationDao, times(1)).getFireStationByStationAddress(anyString());

        assertThat(fireStation).isEqualTo(false);
    }

    @Test
    void deleteFireStation_ByStation() {
        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(fireStations);

        doNothing().when(fireStationDao).deleteFireStationsByStation(anyInt());
        // WHEN
        boolean fireStation = fireStationService.deleteFireStation(new FireStation(null, 1));
        // THEN
        assertThat(fireStation).isEqualTo(true);
    }

    @Test
    void deleteFireStation_ByAddress() {
        // GIVEN
        given(fireStationDao.getFireStationByStationAddress(anyString())).willReturn
                (fireStations.get(0));

        doNothing().when(fireStationDao).deleteFireStationByAddress(anyString());
        // WHEN
        boolean fireStation = fireStationService.deleteFireStation(new FireStation("644 Gershwin " +
                "Cir1", 1));
        // THEN
        assertThat(fireStation).isEqualTo(true);
    }

    @Test
    void deleteFireStation_Station_DontExist() {

        // WHEN
        boolean fireStation = fireStationService.deleteFireStation(new FireStation(null, 0));
        // THEN
        assertThat(fireStation).isEqualTo(false);
    }

    @Test
    void deleteFireStation_ByStation_DontExist() {
        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(null);

        doNothing().when(fireStationDao).deleteFireStationsByStation(anyInt());
        // WHEN
        boolean fireStation = fireStationService.deleteFireStation(new FireStation(null, 1));
        // THEN
        assertThat(fireStation).isEqualTo(false);
    }

    @Test
    void deleteFireStation_ByAddress_DontExist() {
        // GIVEN
        given(fireStationDao.getFireStationByStationAddress(anyString())).willReturn(null);

        doNothing().when(fireStationDao).deleteFireStationByAddress(anyString());
        // WHEN
        boolean fireStation = fireStationService.deleteFireStation(new FireStation("644 Gershwin " +
                "Cir", 1));
        // THEN
        assertThat(fireStation).isEqualTo(false);
    }

    @Test
    void getPersonsInFireStationArea_Parents() {

        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(fireStations);
        given(personDao.getPersonsByAddress(anyString())).willReturn(personList);
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecordList.get(0));

        // WHEN
        PersonsInFireStationArea personsInFireStationAreaTest =
                fireStationService.getPersonsInFireStationArea(1);
        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());
        verify(personDao, times(3)).getPersonsByAddress(anyString());
        verify(medicalRecordDao, times(9)).getMedicalRecordByFirstNameAndLastName(anyString(),
                anyString());

        assertThat(personsInFireStationAreaTest.getPersons().size()).isEqualTo(9);
        assertThat(personsInFireStationAreaTest.getAdultQty()).isEqualTo(9);
        assertThat(personsInFireStationAreaTest.getChildQty()).isEqualTo(0);
    }

    @Test
    void getPersonsInFireStationArea_Children() {

        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(fireStations);
        given(personDao.getPersonsByAddress(anyString())).willReturn(personList);
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecordList.get(1));

        // WHEN
        PersonsInFireStationArea personsInFireStationAreaTest =
                fireStationService.getPersonsInFireStationArea(1);
        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());
        verify(personDao, times(3)).getPersonsByAddress(anyString());
        verify(medicalRecordDao, times(9)).getMedicalRecordByFirstNameAndLastName(anyString(),
                anyString());

        assertThat(personsInFireStationAreaTest.getPersons().size()).isEqualTo(9);
        assertThat(personsInFireStationAreaTest.getAdultQty()).isEqualTo(0);
        assertThat(personsInFireStationAreaTest.getChildQty()).isEqualTo(9);
    }

    @Test
    void getPersonsInFireStationArea_FireStations_Empty() {

        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(Collections.emptyList());

        // WHEN
        PersonsInFireStationArea personsInFireStationAreaTest =
                fireStationService.getPersonsInFireStationArea(1);
        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());

        assertThat(personsInFireStationAreaTest).isEqualTo(null);
    }

    @Test
    void getPersonsInFireStationArea_FireStations_Null() {

        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(null);

        // WHEN
        PersonsInFireStationArea personsInFireStationAreaTest =
                fireStationService.getPersonsInFireStationArea(1);
        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());

        assertThat(personsInFireStationAreaTest).isEqualTo(null);
    }

    @Test
    void getPersonsInFireStationArea_Persons_Null() {

        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(fireStations);
        given(personDao.getPersonsByAddress(anyString())).willReturn(null);

        // WHEN
        PersonsInFireStationArea personsInFireStationAreaTest =
                fireStationService.getPersonsInFireStationArea(1);
        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());
        verify(personDao, times(3)).getPersonsByAddress(anyString());

        assertThat(personsInFireStationAreaTest.getPersons()).isEqualTo(null);
        assertThat(personsInFireStationAreaTest.getAdultQty()).isEqualTo(0);
        assertThat(personsInFireStationAreaTest.getChildQty()).isEqualTo(0);
    }

    @Test
    void getPersonsInFireStationArea_MedicalRecords_Null() {

        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(fireStations);
        given(personDao.getPersonsByAddress(anyString())).willReturn(personList);
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString()))
                .willReturn(null);

        // WHEN
        PersonsInFireStationArea personsInFireStationAreaTest =
                fireStationService.getPersonsInFireStationArea(1);
        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());
        verify(personDao, times(3)).getPersonsByAddress(anyString());
        verify(medicalRecordDao, times(9)).getMedicalRecordByFirstNameAndLastName(anyString(), anyString());

        assertThat(personsInFireStationAreaTest.getPersons().size()).isEqualTo(9);
        assertThat(personsInFireStationAreaTest.getAdultQty()).isEqualTo(0);
        assertThat(personsInFireStationAreaTest.getChildQty()).isEqualTo(0);
    }

    @Test
    void getPhoneNumbersInFireStationArea() {
        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(fireStations);
        given(personDao.getPersonsByAddress(anyString())).willReturn(personList);

        // WHEN
        List<String> phoneNumbersInFireStationAreaTest =
                fireStationService.getPhoneNumbersInFireStationArea(1);

        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());
        verify(personDao, times(3)).getPersonsByAddress(anyString());

        assertThat(phoneNumbersInFireStationAreaTest.size()).isEqualTo(9);
        assertThat(phoneNumbersInFireStationAreaTest.get(0)).isEqualTo("841-874-6512");
    }

    @Test
    void getPhoneNumbersInFireStationArea_FireStation_Null() {
        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(null);

        // WHEN
        List<String> phoneNumbersInFireStationAreaTest =
                fireStationService.getPhoneNumbersInFireStationArea(1);

        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());

        assertThat(phoneNumbersInFireStationAreaTest).isEqualTo(null);
    }

    @Test
    void getPhoneNumbersInFireStationArea_Persons_Null() {
        // GIVEN
        given(fireStationDao.getFireStationsByStationId(anyInt())).willReturn(fireStations);
        given(personDao.getPersonsByAddress(anyString())).willReturn(null);

        // WHEN
        List<String> phoneNumbersInFireStationAreaTest =
                fireStationService.getPhoneNumbersInFireStationArea(1);

        // THEN
        verify(fireStationDao, times(1)).getFireStationsByStationId(anyInt());
        verify(personDao, times(3)).getPersonsByAddress(anyString());

        assertThat(phoneNumbersInFireStationAreaTest.size()).isEqualTo(0);
    }
}