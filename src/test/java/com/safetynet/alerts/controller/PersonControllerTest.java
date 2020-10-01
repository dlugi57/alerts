package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    public static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person("John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jaboyd@email.com"));
        personList.add(new Person("Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6513", "drk@email.com"));
        personList.add(new Person("Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    //GET
    @Test
    void getPersonByFirstNameAndLastName() throws Exception {

        when(service.getPersonByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(new Person("John", "Boyd",
                "1509 Culver St", "Culver", "97451",
                "841-874-6512", "jaboyd@email.com"));

        this.mockMvc.perform(get("/person")
                .param("lastName", "Boyd")
                .param("firstName", "John")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Boyd"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getPersonByFirstNameAndLastNameNotFound() throws Exception {

        when(service.getPersonByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(null);

        this.mockMvc.perform(get("/person")
                .param("lastName", "Boyd")
                .param("firstName", "John")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    //POST
    @Test
    void addPerson() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Person person = new Person("Piotr", "Dlugosz", "1509 Culver St",
                "Culver", "12345", "841-874-6512", "piotr@email.com");

        when(service.addPerson(any(Person.class))).thenReturn(true);

        this.mockMvc.perform(post("/person")
                .content((mapper.writeValueAsString(person)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/person/?firstName=Piotr&lastName=Dlugosz"))
                .andExpect(status().isCreated());
    }

    @Test
    void addPersonInvalid() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Person person = new Person("Piotr", "Dlugosz", "1509 Culver St",
                "Culver", "12345", "841-874-6512", "piotr@email.com");

        when(service.addPerson(any(Person.class))).thenReturn(false);

        this.mockMvc.perform(post("/person")
                .content((mapper.writeValueAsString(person)))
                .contentType("application/json"))
                .andExpect(status().isConflict());
    }

    //UPDATE
    @Test
    void updatePerson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Person person = new Person("Piotr", "Dlugosz", "1509 Culver St",
                "Culver", "12345", "841-874-6512", "piotr@email.com");

        person.setAddress("15 Culver St");

        when(service.updatePerson(any(Person.class))).thenReturn(true);

        this.mockMvc.perform(put("/person")
                .content((mapper.writeValueAsString(person)))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/person/?firstName=Piotr&lastName=Dlugosz"))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePersonInvalid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Person person = new Person("Piotr", "Dlugosz", "1509 Culver St",
                "Culver", "12345", "841-874-6512", "piotr@email.com");

        person.setAddress("15 Culver St");

        when(service.updatePerson(any(Person.class))).thenReturn(false);

        this.mockMvc.perform(put("/person")
                .content((mapper.writeValueAsString(person)))
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    // DELETE
    @Test
    void deletePerson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Person person = new Person("Piotr", "Dlugosz", "1509 Culver St",
                "Culver", "12345", "841-874-6512", "piotr@email.com");

        when(service.deletePerson(any(Person.class))).thenReturn(true);

        this.mockMvc.perform(delete("/person")
                .content((mapper.writeValueAsString(person)))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deletePersonInvalid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Person person = new Person("Piotr", "Dlugosz", "1509 Culver St",
                "Culver", "12345", "841-874-6512", "piotr@email.com");

        when(service.deletePerson(any(Person.class))).thenReturn(false);

        this.mockMvc.perform(delete("/person")
                .content((mapper.writeValueAsString(person)))
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPersons() throws Exception {

        when(service.getPersons()).thenReturn(personList);

        this.mockMvc.perform(get("/persons")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$", hasSize(3)));
        // TODO: 29/09/2020  
        /*
          .andExpect(jsonPath("$.persons", hasSize(1)))
          .andExpect(jsonPath("$.adults", is(0)))
          .andExpect(jsonPath("$.children", is(1)))
          .andExpect(jsonPath("$.persons[0].firstName", is(p.getFirstName())));
        */
    }

    @Test
    void getPersonsNull() throws Exception {

        when(service.getPersons()).thenReturn(null);

        this.mockMvc.perform(get("/persons")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPersonsEmpty() throws Exception {

        when(service.getPersons()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/persons")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}