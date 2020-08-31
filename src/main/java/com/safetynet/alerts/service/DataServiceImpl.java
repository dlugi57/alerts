package com.safetynet.alerts.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public void init() {

        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json")) {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(in);

            List<Person> persons = mapper.convertValue(jsonNode.get("persons"), ArrayList.class);

            List<Firestation> fireStations = mapper.convertValue(jsonNode.get("firestations"), ArrayList.class);

            List<MedicalRecord> medicalRecords = mapper.convertValue(jsonNode.get("medicalrecords"), ArrayList.class);

            String jsonString = mapper.writeValueAsString(persons);
            System.out.println(jsonString);

            String jsonString1 = mapper.writeValueAsString(fireStations);
            System.out.println(jsonString1);

            String jsonString2 = mapper.writeValueAsString(medicalRecords);
            System.out.println(jsonString2);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
