package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public void init() {

        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json")) {
            //pass InputStream to JSON-Library, e.g. using Jackson
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON file to map
            Map<?, ?> map = mapper.readValue(in, Map.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }


            /*
            Person person = mapper.readValue(in, Person.class);
            String jsonString = mapper.writeValueAsString(person);
            System.out.println(jsonString);
            */
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}
