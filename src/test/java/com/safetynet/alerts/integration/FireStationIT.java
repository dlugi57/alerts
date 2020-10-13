package com.safetynet.alerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.model.FireStation;
import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@Rollback()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FireStationIT {

    @Autowired
    FireStationDao fireStationDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Order(1)
    @Test
    void getFireStationsByStationId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestations/3"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"address\":\"1509 Culver St\",\"station\":3},{\"address\":\"834 Binoc Ave\",\"station\":3},{\"address\":\"748 Townings Dr\",\"station\":3},{\"address\":\"112 Steppes Pl\",\"station\":3}]"))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void getFireStationByStationAddress()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation/644 Gershwin Cir"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"address\":\"644 Gershwin Cir\",\"station\":1}"))
                .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void getFireStationByStationAddress_Null()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation/644 Gershwin Cir123"))
                .andExpect(MockMvcResultMatchers.content().string(IsEmptyString.emptyOrNullString()))
                .andExpect(status().isNotFound());
    }

    @Order(4)
    @Test
    public void getFireStations()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestations/"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"address\":\"1509 Culver St\",\"station\":3},{\"address\":\"29 15th St\",\"station\":2},{\"address\":\"834 Binoc Ave\",\"station\":3},{\"address\":\"644 Gershwin Cir\",\"station\":1},{\"address\":\"748 Townings Dr\",\"station\":3},{\"address\":\"112 Steppes Pl\",\"station\":3},{\"address\":\"489 Manchester St\",\"station\":4},{\"address\":\"892 Downing Ct\",\"station\":2},{\"address\":\"908 73rd St\",\"station\":1},{\"address\":\"947 E. Rose Dr\",\"station\":1},{\"address\":\"951 LoneTree Rd\",\"station\":2}]"))
                .andExpect(status().isOk());
    }

    @Order(5)
    @Test
    public void addFireStation() throws Exception {

        FireStation fireStation = new FireStation("10avenue du Bosquet", 3);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType("application/json")
                .content(mapper.writeValueAsString(fireStation)))
                .andExpect(status().isCreated());

        FireStation fireStationAdd = fireStationDao
                .getFireStationByStationAddress("10 avenue du Bosquet");

        assertNotNull(fireStationAdd);
        assertThat(mapper.writeValueAsString(fireStation)).isEqualTo(mapper.writeValueAsString(fireStationAdd));
    }

    @Order(5)
    @Test
    public void updateFireStation() throws Exception {

        FireStation fireStation = new FireStation("1509 Culver St", 4);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType("application/json")
                .content(mapper.writeValueAsString(fireStation)))
                .andExpect(status().isCreated());

        FireStation fireStationUpdate = fireStationDao
                .getFireStationByStationAddress("1509 Culver St");

        assertNotNull(fireStationUpdate);
        assertThat(mapper.writeValueAsString(fireStation)).isEqualTo(mapper.writeValueAsString(fireStationUpdate));
    }

    @Order(5)
    @Test
    public void deleteFireStation() throws Exception {

        FireStation fireStation = new FireStation("1509 Culver St", 3);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .contentType("application/json")
                .content(mapper.writeValueAsString(fireStation)))
                .andExpect(status().isOk());

        FireStation fireStationDelete = fireStationDao
                .getFireStationByStationAddress("1509 Culver St");

        assertThat(fireStationDelete).isEqualTo(null);
    }
}
