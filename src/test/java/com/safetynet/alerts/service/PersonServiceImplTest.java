package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.ChildrenByAddress;
import com.safetynet.alerts.dto.PersonsAndStationByAddress;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @TestConfiguration
    static class PersonServiceImplTestContextConfiguration {
        @Bean
        public PersonService personService() {
            return new PersonServiceImpl();
        }
    }

    @Resource
    PersonService personService;
    @MockBean
    FireStationDao fireStationDao;
    @MockBean
    PersonDao personDao;
    @MockBean
    MedicalRecordDao medicalRecordDao;

    public static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jaboyd@email.com"));
        persons.add(new Person("Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        persons.add(new Person("Tenley", "Boyd", "1509 Culver St",
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

    public static List<FireStation> fireStations = new ArrayList<>();

    static {
        fireStations.add(new FireStation("1509 Culver St", 1));
        fireStations.add(new FireStation("908 73rd St", 1));
        fireStations.add(new FireStation("947 E. Rose Dr", 1));
    }

    @Test
    void getPersons() {
        // GIVEN
        given(personDao.getPersons()).willReturn(persons);
        // WHEN
        List<Person> personsAll = personService.getPersons();
        // THEN
        verify(personDao, times(1)).getPersons();
        assertThat(personsAll.size()).isEqualTo(3);
        assertThat(personsAll).isEqualTo(persons);
        assertThat(personsAll.get(0).getFirstName()).isEqualTo("John");
        assertThat(personsAll.get(0).getLastName()).isEqualTo("Boyd");
    }

    @Test
    void addPerson() {
        // GIVEN
        given(personDao.getByFirstNameAndLastName(anyString(), anyString())).willReturn(null);

        given(personDao.addPerson(any(Person.class))).willReturn(true);
        // WHEN
        boolean fireStation = personService.addPerson(new Person("Piotr", "Dlugosz", "1509 Culver" +
                " St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
        // THEN
        verify(personDao, times(1)).getByFirstNameAndLastName(anyString(), anyString());
        verify(personDao, times(1)).addPerson(any(Person.class));

        assertThat(fireStation).isEqualTo(true);
    }

    @Test
    void addPerson_AlreadyExist() {
        // GIVEN
        given(personDao.getByFirstNameAndLastName(anyString(), anyString())).willReturn(persons.get(0));

        // WHEN
        boolean fireStation = personService.addPerson(new Person("Piotr", "Dlugosz", "1509 Culver" +
                " St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
        // THEN
        verify(personDao, times(1)).getByFirstNameAndLastName(anyString(), anyString());

        assertThat(fireStation).isEqualTo(false);
    }

    @Test
    void getPersonByFirstNameAndLastName() {
        // GIVEN
        given(personDao.getByFirstNameAndLastName(anyString(), anyString())).willReturn(persons.get(0));
        // WHEN
        Person personByFirstNameAndLastName = personService.getPersonByFirstNameAndLastName("John", "Boyd");
        // THEN
        verify(personDao, times(1)).getByFirstNameAndLastName(anyString(), anyString());
        assertThat(personByFirstNameAndLastName).isEqualTo(persons.get(0));
        assertThat(personByFirstNameAndLastName.getFirstName()).isEqualTo("John");
        assertThat(personByFirstNameAndLastName.getLastName()).isEqualTo("Boyd");
    }

    @Test
    void updatePerson() {
        // GIVEN
        given(personDao.getByFirstNameAndLastName(anyString(), anyString())).willReturn(persons.get(0));

        given(personDao.updatePerson(any(Person.class))).willReturn(true);
        // WHEN
        boolean person = personService.updatePerson(new Person("Piotr", "Dlugosz", "1509 Culver" +
                " St", "Culver", "97451", "841-874-6512", "dlugi@email.com"));
        // THEN
        verify(personDao, times(1)).getByFirstNameAndLastName(anyString(), anyString());
        verify(personDao, times(1)).updatePerson(any(Person.class));

        assertThat(person).isEqualTo(true);
    }

    @Test
    void updatePerson_DonExist() {
        // GIVEN
        given(personDao.getByFirstNameAndLastName(anyString(), anyString())).willReturn(null);

        // WHEN
        boolean person = personService.updatePerson(new Person("Piotr", "Dlugosz", "1509 Culver" +
                " St", "Culver", "97451", "841-874-6512", "dlugi@email.com"));
        // THEN
        verify(personDao, times(1)).getByFirstNameAndLastName(anyString(), anyString());

        assertThat(person).isEqualTo(false);
    }

    @Test
    void deletePerson() {
        // GIVEN
        given(personDao.getByFirstNameAndLastName(anyString(), anyString())).willReturn(persons.get(0));

        given(personDao.deletePerson(any(Person.class))).willReturn(true);
        // WHEN
        boolean person = personService.deletePerson(new Person("John", "Boyd", "1509 Culver" +
                " St", "Culver", "97451", "841-874-6512", "dlugi@email.com"));
        // THEN
        verify(personDao, times(1)).getByFirstNameAndLastName(anyString(), anyString());
        verify(personDao, times(1)).deletePerson(any(Person.class));

        assertThat(person).isEqualTo(true);
    }

    @Test
    void deletePerson_DontExist() {
        // GIVEN
        given(personDao.getByFirstNameAndLastName(anyString(), anyString())).willReturn(null);

        // WHEN
        boolean person = personService.deletePerson(new Person("John", "Boyd", "1509 Culver" +
                " St", "Culver", "97451", "841-874-6512", "dlugi@email.com"));
        // THEN
        verify(personDao, times(1)).getByFirstNameAndLastName(anyString(), anyString());

        assertThat(person).isEqualTo(false);
    }

    @Test
    void getChildrenByAddress_Adults() {

        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(persons);
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecordList.get(0));

        // WHEN
        ChildrenByAddress childrenByAddress = personService.getChildrenByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());
        verify(medicalRecordDao, times(3)).getMedicalRecordByFirstNameAndLastName(anyString(),
                anyString());

        assertThat(childrenByAddress.getAdults().size()).isEqualTo(3);
        assertThat(childrenByAddress.getChildren()).isEqualTo(Collections.emptyList());
        assertThat(childrenByAddress.getAdults().get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void getChildrenByAddress_Children() {

        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(persons);
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecordList.get(1));

        // WHEN
        ChildrenByAddress childrenByAddress = personService.getChildrenByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());
        verify(medicalRecordDao, times(3)).getMedicalRecordByFirstNameAndLastName(anyString(),
                anyString());

        assertThat(childrenByAddress.getAdults()).isEqualTo(Collections.emptyList());
        assertThat(childrenByAddress.getChildren().size()).isEqualTo(3);
        assertThat(childrenByAddress.getChildren().get(0).getFirstName()).isEqualTo("John");

    }

    @Test
    void getChildrenByAddress_Persons_EmptyList() {

        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(Collections.emptyList());

        // WHEN
        ChildrenByAddress childrenByAddress = personService.getChildrenByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());

        assertThat(childrenByAddress).isEqualTo(null);

    }

    @Test
    void getChildrenByAddress_Persons_Null() {

        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(null);

        // WHEN
        ChildrenByAddress childrenByAddress = personService.getChildrenByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());

        assertThat(childrenByAddress).isEqualTo(null);

    }

    @Test
    void getChildrenByAddress_MedicalRecord_Null() {

        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(persons);
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(null);

        // WHEN
        ChildrenByAddress childrenByAddress = personService.getChildrenByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());
        verify(medicalRecordDao, times(3)).getMedicalRecordByFirstNameAndLastName(anyString(),
                anyString());

        assertThat(childrenByAddress.getAdults()).isEqualTo(Collections.emptyList());
        assertThat(childrenByAddress.getChildren()).isEqualTo(Collections.emptyList());
    }

    @Test
    void getPersonsAndStationByAddress() {
        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(persons);
        given(fireStationDao.getFireStationByStationAddress(anyString())).willReturn(fireStations.get(0));
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecordList.get(1));

        // WHEN
        PersonsAndStationByAddress personsAndStationByAddress =
                personService.getPersonsAndStationByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());
        verify(fireStationDao, times(1)).getFireStationByStationAddress(
                anyString());
        verify(medicalRecordDao, times(3)).getMedicalRecordByFirstNameAndLastName(
                anyString(), anyString());

        assertThat(personsAndStationByAddress.getStation()).isEqualTo(1);
        assertThat(personsAndStationByAddress.getPersons().size()).isEqualTo(3);
        assertThat(personsAndStationByAddress.getPersons().get(0).getLastName()).isEqualTo("Boyd");

    }

    @Test
    void getPersonsAndStationByAddress_Persons_Empty() {
        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(Collections.emptyList());

        // WHEN
        PersonsAndStationByAddress personsAndStationByAddress =
                personService.getPersonsAndStationByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());

        assertThat(personsAndStationByAddress).isEqualTo(null);

    }

    @Test
    void getPersonsAndStationByAddress_Persons_Null() {
        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(null);

        // WHEN
        PersonsAndStationByAddress personsAndStationByAddress =
                personService.getPersonsAndStationByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());

        assertThat(personsAndStationByAddress).isEqualTo(null);

    }

    @Test
    void getPersonsAndStationByAddress_FireStation_Null() {
        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(persons);
        given(fireStationDao.getFireStationByStationAddress(anyString())).willReturn(null);

        // WHEN
        PersonsAndStationByAddress personsAndStationByAddress =
                personService.getPersonsAndStationByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());
        verify(fireStationDao, times(1)).getFireStationByStationAddress(
                anyString());

        assertThat(personsAndStationByAddress.getStation()).isEqualTo(0);
        assertThat(personsAndStationByAddress.getPersons().get(0).getLastName()).isEqualTo(null);

    }

    @Test
    void getPersonsAndStationByAddress_MedicalRecord_Null() {
        // GIVEN
        given(personDao.getPersonsByAddress(anyString())).willReturn(persons);
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(null);

        // WHEN
        PersonsAndStationByAddress personsAndStationByAddress =
                personService.getPersonsAndStationByAddress("1509 Culver St");

        // THEN
        verify(personDao, times(1)).getPersonsByAddress(anyString());
        verify(medicalRecordDao, times(3)).getMedicalRecordByFirstNameAndLastName(
                anyString(), anyString());

        assertThat(personsAndStationByAddress.getStation()).isEqualTo(0);
        assertThat(personsAndStationByAddress.getPersons().size()).isEqualTo(3);
        assertThat(personsAndStationByAddress.getPersons().get(0).getLastName()).isEqualTo(null);

    }

    @Test
    void getPersonsAndAddressesByStations() {
    }

    @Test
    void getPersonsInfo() {
    }

    @Test
    void getCommunityEmails() {
    }
}