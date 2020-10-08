package com.safetynet.alerts.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FireStationController;
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.model.DataAlert;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.DataService;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class FireStationIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    // Data creation for tests
    // ADDRESSES ----------------------------------------------------
    public static List<FireStation> addressEntityList = new ArrayList<>();
    static {
        addressEntityList
                .add(new FireStation( "1509 Culver St", 3));
        addressEntityList
                .add(new FireStation("29 15th St", 2));
        addressEntityList
                .add(new FireStation( "834 Binoc Ave", 3));
    }
    // -------------------------------------------------------------------------------

    @Test // GET
    @Tag("Test_FindByAddress")
    public void givenAnAddressFireStationToFind_whenGetByAddress_thenReturnsIt()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation/644 Gershwin Cir"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"address\":\"644 Gershwin Cir\",\"station\":1}"))
                .andExpect(status().isOk());
    }

}
