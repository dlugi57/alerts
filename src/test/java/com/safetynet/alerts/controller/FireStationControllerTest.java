package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService service;

    public static List<FireStation> fireStations = new ArrayList<>();

    static {
        fireStations.add(new FireStation("1509 Culver St", 3));
        fireStations.add(new FireStation("29 15th St", 2));
        fireStations.add(new FireStation("834 Binoc Ave", 2));
    }

    //GET
    @Test
    void getFireStationsByStationId() throws Exception {

        when(service.getFireStationsByStationId(anyInt())).thenReturn(fireStations);

        this.mockMvc.perform(get("/firestations/3")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].address").value("1509 Culver St"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getFireStationsByStationId_Null() throws Exception {
        when(service.getFireStationsByStationId(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/firestations/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getFireStationsByStationId_Empty() throws Exception {
        when(service.getFireStationsByStationId(anyInt())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/firestations/3")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getFireStationByStationAddress() throws Exception {
        when(service.getFireStationByStationAddress(anyString())).thenReturn(new FireStation("1509 Culver St", 3));

        this.mockMvc.perform(get("/firestation/1509 Culver St")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.address").value("1509 Culver St"))
                .andExpect(jsonPath("$.station").value(3));
    }

    @Test
    void getFireStationByStationAddress_NotFound() throws Exception {
        when(service.getFireStationByStationAddress(anyString())).thenReturn(null);

        this.mockMvc.perform(get("/firestation/1509 Culver St")
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // POST
    @Test
    void addFireStation() throws Exception {
        when(service.addFireStation(any(FireStation.class))).thenReturn(true);

        this.mockMvc.perform(post("/firestation")
                .content("{\"address\":\"10 Forest Avenue\",\"station\":\"3\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/firestation/10%20Forest%20Avenue"))
                .andExpect(status().isCreated());
    }

    @Test
    void addFireStation_Conflict() throws Exception {
        when(service.addFireStation(any(FireStation.class))).thenReturn(false);

        this.mockMvc.perform(post("/firestation")
                .content("{\"address\":\"10 Forest Avenue\",\"station\":\"3\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    // UPDATE
    @Test
    void updateFireStation() throws Exception {
        when(service.updateFireStation(any(FireStation.class))).thenReturn(true);

        this.mockMvc.perform(put("/firestation")
                .content("{\"address\":\"10 Forest Avenue\",\"station\":\"3\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(header().string("Location", "http://localhost/firestation/10%20Forest%20Avenue"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateFireStation_NotFound() throws Exception {
        when(service.updateFireStation(any(FireStation.class))).thenReturn(false);

        this.mockMvc.perform(put("/firestation")
                .content("{\"address\":\"10 Forest Avenue\",\"station\":\"3\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFireStation() throws Exception {
        when(service.deleteFireStation(any(FireStation.class))).thenReturn(true);

        this.mockMvc.perform(delete("/firestation")
                .content("{\"address\":\"10 Forest Avenue\",\"station\":\"3\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // DELETE
    @Test
    void deleteFireStation_NotFound() throws Exception {
        when(service.deleteFireStation(any(FireStation.class))).thenReturn(false);

        this.mockMvc.perform(delete("/firestation")
                .content("{\"address\":\"10 Forest Avenue\",\"station\":\"3\"}")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getFireStations() throws Exception {

        when(service.getFireStations()).thenReturn(fireStations);

        this.mockMvc.perform(get("/firestations")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].address").value("1509 Culver St"))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getFireStations_Empty() throws Exception {
        when(service.getFireStations()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/firestations")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getFireStations_Null() throws Exception {
        when(service.getFireStations()).thenReturn(null);

        this.mockMvc.perform(get("/firestations")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}