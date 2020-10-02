package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;
    @MockBean
    private PersonService personService;

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

    static int childQty = 1;
    static int adultQty = 5;

    public static PersonsInFireStationArea personsInFireStationArea =
            new PersonsInFireStationArea(childQty,
                    adultQty, personsFireStation);

    @Test
    void getPersonsInFireStationArea() throws Exception {

        when(fireStationService.getPersonsInFireStationArea(anyInt())).thenReturn(personsInFireStationArea);

        this.mockMvc.perform(get("/firestation")
                .param("stationNumber", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons", hasSize(6)))
                .andExpect(jsonPath("$.adultQty").value("5"))
                .andExpect(jsonPath("$.childQty").value("1"))
                .andExpect(jsonPath("$.persons[0].firstName").value("Peter"));
    }

    @Test
    void getPersonsInFireStationArea_NotFound() throws Exception {

        when(fireStationService.getPersonsInFireStationArea(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/firestation")
                .param("stationNumber", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    public static List<PersonAlert> adults = new ArrayList<>();

    static {
        adults.add(new PersonAlert("John", "Boyd", 36));
        adults.add(new PersonAlert("Jacob", "Boyd", 31));
        adults.add(new PersonAlert("Felicia", "Boyd", 34));
    }

    public static List<PersonAlert> children = new ArrayList<>();

    static {
        children.add(new PersonAlert("Tenley", "Boyd", 8));
        children.add(new PersonAlert("Roger", "Boyd", 3));
    }

    public static ChildrenByAddress childrenByAddress =
            new ChildrenByAddress(adults, children);

    @Test
    void getChildrenByAddress() throws Exception {
        when(personService.getChildrenByAddress(anyString())).thenReturn(childrenByAddress);

        this.mockMvc.perform(get("/childAlert")
                .param("address", "1509 Culver St")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adults", hasSize(3)))
                .andExpect(jsonPath("$.children", hasSize(2)))
                .andExpect(jsonPath("$.adults[0].firstName").value("John"))
                .andExpect(jsonPath("$.children[0].firstName").value("Tenley"));
    }

    @Test
    void getChildrenByAddress_NotFound() throws Exception {
        when(personService.getChildrenByAddress(anyString())).thenReturn(null);

        this.mockMvc.perform(get("/childAlert")
                .param("address", "1509 Culver St")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    public static List<String> phoneNumbersInFireStationArea = new ArrayList<>();

    static {
        for (PersonFireStation personFireStation : personsFireStation) {
            phoneNumbersInFireStationArea.add(personFireStation.getPhone());
        }
    }

    @Test
    void getPhoneNumbersInFireStationArea() throws Exception {
        when(fireStationService.getPhoneNumbersInFireStationArea(anyInt())).thenReturn(phoneNumbersInFireStationArea);

        this.mockMvc.perform(get("/phoneAlert")
                .param("stationNumber", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$.[0]").value("841-874-6512"));
    }

    @Test
    void getPhoneNumbersInFireStationArea_Empty() throws Exception {
        when(fireStationService.getPhoneNumbersInFireStationArea(anyInt())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/phoneAlert")
                .param("stationNumber", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getPhoneNumbersInFireStationArea_NotFound() throws Exception {
        when(fireStationService.getPhoneNumbersInFireStationArea(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/phoneAlert")
                .param("stationNumber", "1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    public static List<PersonFire> personsFire = new ArrayList<>();

    // TODO: 02/10/2020 how to create array in this example
    static {
        personsFire.add(new PersonFire("Boyd", "841-874-6512", 36, Arrays.asList("aznol:350mg", "hydrapermazol:100mg"),
                Collections.singletonList("nillacilan")));
        personsFire.add(new PersonFire("Boyd", "841-874-6513", 31, Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()));
        personsFire.add(new PersonFire("Boyd", "841-874-6512", 8, Collections.emptyList(),
                Collections.singletonList("peanut")));
        personsFire.add(new PersonFire("Boyd", "841-874-6512", 3, Collections.emptyList(),
                Collections.emptyList()));
        personsFire.add(new PersonFire("Boyd", "841-874-6544", 34, Collections.singletonList("tetracyclaz:650mg"),
                Collections.singletonList("xilliathal")));
    }

    static int station = 3;

    public static PersonsAndStationByAddress personsAndStationByAddress =
            new PersonsAndStationByAddress(station, personsFire);

    @Test
    void getPersonsAndStationByAddress() throws Exception {
        when(personService.getPersonsAndStationByAddress(anyString())).thenReturn(personsAndStationByAddress);

        this.mockMvc.perform(get("/fire")
                .param("address", "1509 Culver St")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons", hasSize(5)))
                .andExpect(jsonPath("$.station").value("3"))
                .andExpect(jsonPath("$.persons[0].lastName").value("Boyd"));
    }

    @Test
    void getPersonsAndStationByAddress_NotFound() throws Exception {
        when(personService.getPersonsAndStationByAddress(anyString())).thenReturn(null);

        this.mockMvc.perform(get("/fire")
                .param("address", "1509 Culver St")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    static String address = "644 Gershwin Cir";
    static String address2 = "908 73rd St";

    public static PersonsAndAddressesByStation personsAndAddressesByStation1 =
            new PersonsAndAddressesByStation(address, personsFire);
    public static PersonsAndAddressesByStation personsAndAddressesByStation2 =
            new PersonsAndAddressesByStation(address2, personsFire);

    public static List<PersonsAndAddressesByStation> personsAndAddressesByStationList =
            new ArrayList<>();

    static {
        personsAndAddressesByStationList.add(personsAndAddressesByStation1);
        personsAndAddressesByStationList.add(personsAndAddressesByStation2);

    }

    @Test
    void getPersonsAndAddressesByStations() throws Exception {
        when(personService.getPersonsAndAddressesByStations(anyList())).thenReturn(personsAndAddressesByStationList);

        this.mockMvc.perform(get("/flood/stations")
                .param("stations", "1", "2")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].persons", hasSize(5)))
                .andExpect(jsonPath("$.[0].address").value("644 Gershwin Cir"))
                .andExpect(jsonPath("$.[0].persons[0].lastName").value("Boyd"));
    }

    @Test
    void getPersonsAndAddressesByStations_Empty() throws Exception {
        when(personService.getPersonsAndAddressesByStations(anyList())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/flood/stations")
                .param("stations", "1", "2")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getPersonsAndAddressesByStations_NotFound() throws Exception {
        when(personService.getPersonsAndAddressesByStations(anyList())).thenReturn(null);

        this.mockMvc.perform(get("/flood/stations")
                .param("stations", "1", "2")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    public static List<PersonInfo> personsInfo = new ArrayList<>();

    static {
        personsInfo.add(new PersonInfo("Boyd", "1509 Culver St", 36, "jaboyd@email.com",
                Arrays.asList("aznol:350mg", "hydrapermazol:100mg"),
                Collections.singletonList("nillacilan")));
        personsInfo.add(new PersonInfo("Boyd", "1509 Culver St", 31, "jaboyd@email.com", Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()));
        personsInfo.add(new PersonInfo("Boyd", "1509 Culver St", 8, "jaboyd@email.com", Collections.emptyList(),
                Collections.singletonList("peanut")));
        personsInfo.add(new PersonInfo("Boyd", "1509 Culver St", 3, "jaboyd@email.com", Collections.emptyList(),
                Collections.emptyList()));
        personsInfo.add(new PersonInfo("Boyd", "1509 Culver St", 34, "jaboyd@email.com", Collections.singletonList("tetracyclaz:650mg"),
                Collections.singletonList("xilliathal")));
    }


    @Test
    void getPersonsInfo() throws Exception {
        when(personService.getPersonsInfo(anyString(), anyString())).thenReturn(personsInfo);

        this.mockMvc.perform(get("/personInfo")
                .param("firstName", "John")
                .param("lastName", "Boyd")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$.[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$.[0].address").value("1509 Culver St"));
    }

    @Test
    void getPersonsInfo_Empty() throws Exception {
        when(personService.getPersonsInfo(anyString(), anyString())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/personInfo")
                .param("firstName", "John")
                .param("lastName", "Boyd")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getPersonsInfo_NotFound() throws Exception {
        when(personService.getPersonsInfo(anyString(), anyString())).thenReturn(null);

        this.mockMvc.perform(get("/personInfo")
                .param("firstName", "John")
                .param("lastName", "Boyd")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    public static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person("John", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        persons.add(new Person("Johnathan", "Barrack", "29 15th St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        persons.add(new Person("Doc", "Spring",
                "1515 Java St - Beverly Hills", "Los Angeles", "90211",
                "123-456-7890", "Doc.Spring@email.com"));
        persons.add(new Person("Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
        persons.add(new Person("Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6515", "tenz@email.com"));
        persons.add(new Person("Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        persons.add(new Person("Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        persons.add(new Person("Felicia", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6544", "jaboyd@email.com"));
    }

    public static List<String> communityEmails = new ArrayList<>();

    static {
        for (Person person : persons) {
            if (person.getCity().equals("Culver")) {
                communityEmails.add(person.getEmail());
            }
        }
    }

    @Test
    void getCommunityEmails() throws Exception {
        when(personService.getCommunityEmails(anyString())).thenReturn(communityEmails);

        this.mockMvc.perform(get("/communityEmail")
                .param("city", "Culver")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$.[0]").value("jaboyd@email.com"))
                .andExpect(jsonPath("$.[1]").value("drk@email.com"));
    }

    @Test
    void getCommunityEmails_Empty() throws Exception {
        when(personService.getCommunityEmails(anyString())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/communityEmail")
                .param("city", "Culver")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getCommunityEmailsNotFound() throws Exception {
        when(personService.getCommunityEmails(anyString())).thenReturn(null);

        this.mockMvc.perform(get("/communityEmail")
                .param("city", "Culver")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}