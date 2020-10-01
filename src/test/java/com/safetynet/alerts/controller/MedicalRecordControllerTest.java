package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService service;


    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    static {
        medicalRecordList.add(new MedicalRecord("John", "Boyd", "03/06/1984",
                new ArrayList<>(), new ArrayList<>()));
        medicalRecordList.add(new MedicalRecord(
                "Jacob", "Boyd", "03/06/1989", new ArrayList<>(), new ArrayList<>()));
        medicalRecordList.add(new MedicalRecord("Tenley", "Boyd",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
    }

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
    void addMedicalRecord() throws Exception {

        when(service.addMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        this.mockMvc.perform(post("/medicalrecord")
                .content("{\"firstName\":\"Piotr\",\"lastName\":\"Dlugosz\"," +
                        "\"birthdate\":\"12/06/1975\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/medicalrecord/?firstName=Piotr&lastName=Dlugosz"))
                .andExpect(status().isCreated());
    }

    @Test
    void addMedicalRecordConflict() throws Exception {

        when(service.addMedicalRecord(any(MedicalRecord.class))).thenReturn(false);

        this.mockMvc.perform(post("/medicalrecord")
                .content("{\"firstName\":\"Piotr\",\"lastName\":\"Dlugosz\"," +
                        "\"birthdate\":\"12/06/1975\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    // UPDATE
    @Test
    void updateMedicalRecord() throws Exception {

        when(service.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        this.mockMvc.perform(put("/medicalrecord")
                .content("{\"firstName\":\"Piotr\",\"lastName\":\"Dlugosz\"," +
                        "\"birthdate\":\"12/06/1988\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/medicalrecord/?firstName=Piotr&lastName=Dlugosz"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateMedicalRecordNotFound() throws Exception {

        when(service.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(false);

        this.mockMvc.perform(put("/medicalrecord")
                .content("{\"firstName\":\"Piotr\",\"lastName\":\"Dlugosz\"," +
                        "\"birthdate\":\"12/06/1988\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // DELETE
    @Test
    void deleteMedicalRecord() throws Exception {

        when(service.deleteMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        this.mockMvc.perform(delete("/medicalrecord")
                .content("{\"firstName\":\"Piotr\",\"lastName\":\"Dlugosz\"," +
                        "\"birthdate\":\"12/06/1988\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteMedicalRecordNotFound() throws Exception {

        when(service.deleteMedicalRecord(any(MedicalRecord.class))).thenReturn(false);

        this.mockMvc.perform(delete("/medicalrecord")
                .content("{\"firstName\":\"Piotr\",\"lastName\":\"Dlugosz\"," +
                        "\"birthdate\":\"12/06/1988\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getMedicalRecords() throws Exception {
        when(service.getMedicalRecords()).thenReturn(medicalRecordList);

        this.mockMvc.perform(get("/medicalrecords")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$", hasSize(3)));
    }


    @Test
    void getMedicalRecordsNull() throws Exception {
        when(service.getMedicalRecords()).thenReturn(null);

        this.mockMvc.perform(get("/medicalrecords")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getMedicalRecordsEmpty() throws Exception {
        when(service.getMedicalRecords()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/medicalrecords")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}