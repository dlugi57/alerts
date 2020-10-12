
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
public class MedicalRecordIT {

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
    void getMedicalRecordByFirstNameAndLastName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord")
                .param("lastName", "Boyd")
                .param("firstName", "John"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"birthdate\":\"1984-03-06\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]}"))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void getMedicalRecords()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecords/"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"birthdate\":\"1984-03-06\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"birthdate\":\"1989-03-06\",\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"birthdate\":\"2012-02-18\",\"medications\":[],\"allergies\":[\"peanut\"]},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"birthdate\":\"2017-09-06\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"birthdate\":\"1986-01-08\",\"medications\":[\"tetracyclaz:650mg\"],\"allergies\":[\"xilliathal\"]},{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"birthdate\":\"1989-01-03\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Tessa\",\"lastName\":\"Carman\",\"birthdate\":\"2012-02-18\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"birthdate\":\"2000-09-06\",\"medications\":[],\"allergies\":[\"shellfish\"]},{\"firstName\":\"Foster\",\"lastName\":\"Shepard\",\"birthdate\":\"1980-01-08\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"birthdate\":\"1994-03-06\",\"medications\":[\"hydrapermazol:300mg\",\"dodoxadin:30mg\"],\"allergies\":[\"shellfish\"]},{\"firstName\":\"Lily\",\"lastName\":\"Cooper\",\"birthdate\":\"1994-03-06\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"birthdate\":\"1988-03-06\",\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"]},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"birthdate\":\"1985-03-06\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"birthdate\":\"2017-03-06\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"birthdate\":\"1979-08-30\",\"medications\":[\"thradox:700mg\"],\"allergies\":[\"illisoxian\"]},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"birthdate\":\"1982-03-06\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Ron\",\"lastName\":\"Peters\",\"birthdate\":\"1965-04-06\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Allison\",\"lastName\":\"Boyd\",\"birthdate\":\"1965-03-15\",\"medications\":[\"aznol:200mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"birthdate\":\"1975-12-06\",\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"birthdate\":\"1980-07-08\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"birthdate\":\"2014-03-06\",\"medications\":[\"noxidian:100mg\",\"pharmacol:2500mg\"],\"allergies\":[]},{\"firstName\":\"Clive\",\"lastName\":\"Ferguson\",\"birthdate\":\"1994-03-06\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"birthdate\":\"1945-08-06\",\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}]"))
                .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void addMedicalRecord() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord")
                .contentType("application/json")
                .content("{\"firstName\":\"Piotr\",\"lastName\":\"Dlugosz\"," +
                        "\"birthdate\":\"12/06/1975\"}"))
                .andExpect(status().isCreated());
    }

    @Order(4)
    @Test
    public void updateMedicalRecord() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord")
                .contentType("application/json")
                .content("{\"firstName\":\"John\",\"lastName\":\"Boyd\"," +
                        "\"birthdate\":\"12/06/1988\"}"))
                .andExpect(status().isCreated());
    }

    @Order(4)
    @Test
    public void updateMedicalRecord_NotNull() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord")
                .contentType("application/json")
                .content("{\"firstName\":\"John\",\"lastName\":\"Boyd\"," +
                        "\"birthdate\":\"12/06/1988\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]}"))
                .andExpect(status().isCreated());
    }

    @Order(5)
    @Test
    public void deletePerson() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord")
                .contentType("application/json")
                .content("{\"firstName\":\"John\",\"lastName\":\"Boyd\"," +
                        "\"birthdate\":\"12/06/1988\"}"))
                .andExpect(status().isOk());
    }
}
