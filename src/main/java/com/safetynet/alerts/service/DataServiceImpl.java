package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataAlert;
import com.safetynet.alerts.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Get data from file and set it into object
 */
@Service
public class DataServiceImpl implements DataService {

    // Initialization of data object
    DataAlert dataAlert;

    // logger init
    private static final Logger logger = LogManager
            .getLogger(DataServiceImpl.class);


    /**
     * Get all data from object
     *
     * @return data alert object
     */
    @Override
    public DataAlert getDataAlert() {
        return this.dataAlert;
    }

    /**
     * When start application read and save to object data from file
     */
    @PostConstruct
    public void run() {
        // get file
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json")) {
            // init of mapper
            ObjectMapper mapper = new ObjectMapper();
            // read data
            dataAlert = mapper.readValue(in, DataAlert.class);

            String jsonString = mapper.writeValueAsString(dataAlert);
            logger.debug("Data initialized -> '{}'", jsonString);

            // delete all duplicates from fire stations
            List<FireStation> result = new ArrayList<>();
            Set<String> titles = new HashSet<>();
            logger.debug("Start of deletion of fire station duplicates");
            for (FireStation fireStation : dataAlert.getFirestations()) {
                if (titles.add(fireStation.getAddress())) {
                    result.add(fireStation);
                }
            }
            logger.debug("Fire station without duplicates initialized");
            dataAlert.setFirestations(result);

        } catch (Exception e) {
            // TODO: 07/10/2020 how to test this exception
            logger.error("Method : run /**/ Message : error when reading file '{}'.", e.toString());

            throw new RuntimeException(e);
        }
    }
}
