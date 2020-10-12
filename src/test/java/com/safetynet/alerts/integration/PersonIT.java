
package com.safetynet.alerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
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
public class PersonIT {

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
    void getPersonByFirstNameAndLastName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person")
                .param("lastName", "Boyd")
                .param("firstName", "John"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"}"))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void getPersons()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tenz@email.com\"},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6544\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"address\":\"29 15th St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"},{\"firstName\":\"Tessa\",\"lastName\":\"Carman\",\"address\":\"834 Binoc Ave\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tenz@email.com\"},{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"address\":\"644 Gershwin Cir\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Foster\",\"lastName\":\"Shepard\",\"address\":\"748 Townings Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6544\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6874\",\"email\":\"tcoop@ymail.com\"},{\"firstName\":\"Lily\",\"lastName\":\"Cooper\",\"address\":\"489 Manchester St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-9845\",\"email\":\"lily@email.com\"},{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7878\",\"email\":\"soph@email.com\"},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7512\",\"email\":\"ward@email.com\"},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7512\",\"email\":\"zarc@email.com\"},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-8547\",\"email\":\"reg@email.com\"},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7462\",\"email\":\"jpeter@email.com\"},{\"firstName\":\"Ron\",\"lastName\":\"Peters\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-8888\",\"email\":\"jpeter@email.com\"},{\"firstName\":\"Allison\",\"lastName\":\"Boyd\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-9888\",\"email\":\"aly@imail.com\"},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"bstel@email.com\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"ssanw@email.com\"},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"bstel@email.com\"},{\"firstName\":\"Clive\",\"lastName\":\"Ferguson\",\"address\":\"748 Townings Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6741\",\"email\":\"clivfd@ymail.com\"},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"address\":\"951 LoneTree Rd\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7458\",\"email\":\"gramps@email.com\"}]"
                )).andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void addPerson() throws Exception {

        Person person = new Person("Piotr", "Dlugosz", "av Bosquet", "VL", "06270", "0600000000",
                "email@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType("application/json")
                .content(mapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
    }

    @Order(4)
    @Test
    public void updatePerson() throws Exception {

        Person person = new Person("John", "Boyd", "av Bosquet", "VL", "06270", "0600000000",
                "email@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType("application/json")
                .content(mapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
    }

    @Order(4)
    @Test
    public void updatePerson_Null() throws Exception {

        Person person = new Person("John", "Boyd", null, null, null, null,
                null);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType("application/json")
                .content(mapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
    }

    @Order(5)
    @Test
    public void deletePerson() throws Exception {

        Person person = new Person("John", "Boyd", "av Bosquet", "VL", "06270", "0600000000",
                "email@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .contentType("application/json")
                .content(mapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }
}
