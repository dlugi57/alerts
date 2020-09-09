package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataAlert;
import com.safetynet.alerts.model.FireStation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DataServiceImpl implements DataService {

    DataAlert dataAlert;

    @Override
    public DataAlert getDataAlert() {
        return this.dataAlert;
    }

    @PostConstruct
    public void run() {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json")) {

            ObjectMapper mapper = new ObjectMapper();

            dataAlert = mapper.readValue(in, DataAlert.class);

            String jsonString = mapper.writeValueAsString(dataAlert);
            System.out.println(jsonString);
            // TODO: 09/09/2020 how to remove duplicates

            List<FireStation> result = new ArrayList<FireStation>();
            Set<String> titles = new HashSet<String>();

            for (FireStation fireStation : dataAlert.getFirestations()) {
                if (titles.add(fireStation.getAddress())) {
                    result.add(fireStation);
                }
            }

            dataAlert.setFirestations(result);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
