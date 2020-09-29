package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService service;

    @Test
    void getMedicalRecordByFirstNameAndLastName() throws Exception {

        when(service.getMedicalRecordByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(new MedicalRecord("John", "Boyd", "03/06/1984",
                new ArrayList<>(), new ArrayList<>()));

        this.mockMvc.perform(get("/medicalrecord")
                .param("lastName", "Boyd")
                .param("firstName", "John")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Boyd"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getMedicalRecordByFirstNameAndLastNameNotFound() throws Exception {

        when(service.getMedicalRecordByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(null);

        this.mockMvc.perform(get("/medicalrecord")
                .param("lastName", "Boyd")
                .param("firstName", "John")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    //POST
    @Test
    void addMedicalRecord()  throws Exception {

        ObjectMapper mapper = new ObjectMapper();


        MedicalRecord medicalRecord = new MedicalRecord("Piotr", "Dlugosz", "12/06/1975",
               new ArrayList<>(), new ArrayList<>());

        when(service.addMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        System.out.println(mapper.writeValueAsString(medicalRecord));
        this.mockMvc.perform(post("/medicalrecord")
                //.content(mapper.writeValueAsString(medicalRecord))
                .content("{\"firstName\":\"Piotr\",\"lastName\":\"Dlugosz\"," +
                        "\"birthdate\":\"12/06/1975\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateMedicalRecord()  throws Exception {
    }

    @Test
    void deleteMedicalRecord()  throws Exception {
    }

    @Test
    void getMedicalRecords()  throws Exception {
    }
}