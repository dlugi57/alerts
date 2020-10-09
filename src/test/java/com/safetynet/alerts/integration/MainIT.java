package com.safetynet.alerts.integration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MainIT {

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
    void getPersonsInFireStationArea() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation/")
                .param("stationNumber", "1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"childQty\":1,\"adultQty\":5,\"persons\":[{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"address\":\"644 Gershwin Cir\",\"phone\":\"841-874-6512\"},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"address\":\"908 73rd St\",\"phone\":\"841-874-8547\"},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"address\":\"908 73rd St\",\"phone\":\"841-874-7462\"},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"}]}"))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void getChildrenByAddress()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .param("address", "1509 Culver St")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"adults\":[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"age\":36},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"age\":31},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"age\":34}],\"children\":[{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"age\":8},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"age\":3}]}"))
                .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void getPhoneNumbersInFireStationArea()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .param("stationNumber", "1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[\"841-874-6512\",\"841-874-8547\",\"841-874-7462\",\"841-874-7784\",\"841-874-7784\",\"841-874-7784\"]"))
                .andExpect(status().isOk());
    }

/*    @Order(4)
    @Test
    public void getPersonsAndStationByAddress()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .param("address", "1509 Culver St")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"station\":3,\"persons\":[{\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":36,\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-6513\",\"age\":31,\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":8,\"medications\":[],\"allergies\":[\"peanut\"]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":3,\"medications\":[],\"allergies\":[]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-6544\",\"age\":34,\"medications\":[\"tetracyclaz:650mg\"],\"allergies\":[\"xilliathal\"]}]}"))
                .andExpect(status().isOk());
    }*/

    @Order(5)
    @Test
    public void getPersonsAndAddressesByStations()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .param("stations", "1", "2")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"address\":\"644 Gershwin Cir\",\"persons\":[{\"lastName\":\"Duncan\",\"phone\":\"841-874-6512\",\"age\":20,\"medications\":[],\"allergies\":[\"shellfish\"]}]},{\"address\":\"908 73rd St\",\"persons\":[{\"lastName\":\"Walker\",\"phone\":\"841-874-8547\",\"age\":41,\"medications\":[\"thradox:700mg\"],\"allergies\":[\"illisoxian\"]},{\"lastName\":\"Peters\",\"phone\":\"841-874-7462\",\"age\":38,\"medications\":[],\"allergies\":[]}]},{\"address\":\"947 E. Rose Dr\",\"persons\":[{\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":44,\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]},{\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":40,\"medications\":[],\"allergies\":[]},{\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":6,\"medications\":[\"noxidian:100mg\",\"pharmacol:2500mg\"],\"allergies\":[]}]},{\"address\":\"29 15th St\",\"persons\":[{\"lastName\":\"Marrack\",\"phone\":\"841-874-6513\",\"age\":31,\"medications\":[],\"allergies\":[]}]},{\"address\":\"892 Downing Ct\",\"persons\":[{\"lastName\":\"Zemicks\",\"phone\":\"841-874-7878\",\"age\":32,\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"]},{\"lastName\":\"Zemicks\",\"phone\":\"841-874-7512\",\"age\":35,\"medications\":[],\"allergies\":[]},{\"lastName\":\"Zemicks\",\"phone\":\"841-874-7512\",\"age\":3,\"medications\":[],\"allergies\":[]}]},{\"address\":\"951 LoneTree Rd\",\"persons\":[{\"lastName\":\"Cadigan\",\"phone\":\"841-874-7458\",\"age\":75,\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}]}]"))
                .andExpect(status().isOk());
    }

    @Order(7)
    @Test
    public void getPersonsInfo()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .param("firstName", "John")
                .param("lastName", "Boyd")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"age\":36,\"email\":\"jaboyd@email.com\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]},{\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"age\":31,\"email\":\"jaboyd@email.com\",\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]},{\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"age\":8,\"email\":\"jaboyd@email.com\",\"medications\":[],\"allergies\":[\"peanut\"]},{\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"age\":3,\"email\":\"jaboyd@email.com\",\"medications\":[],\"allergies\":[]},{\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"age\":34,\"email\":\"jaboyd@email.com\",\"medications\":[\"tetracyclaz:650mg\"],\"allergies\":[\"xilliathal\"]}]"))
                .andExpect(status().isOk());
    }

    @Order(8)
    @Test
    public void getCommunityEmails()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .param("city", "Culver")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"tcoop@ymail.com\",\"lily@email.com\",\"soph@email.com\",\"ward@email.com\",\"zarc@email.com\",\"reg@email.com\",\"jpeter@email.com\",\"jpeter@email.com\",\"aly@imail.com\",\"bstel@email.com\",\"ssanw@email.com\",\"bstel@email.com\",\"clivfd@ymail.com\",\"gramps@email.com\"]"))
                .andExpect(status().isOk());
    }
}
