package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataAlert;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class DataServiceImpl implements DataService {

    DataAlert dataAlert;

    @Override
    public void init() {

        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json")) {

            ObjectMapper mapper = new ObjectMapper();

            dataAlert = mapper.readValue(in, DataAlert.class);

            String jsonString = mapper.writeValueAsString(dataAlert);
            System.out.println(jsonString);




        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Person person : dataAlert.getPersons()){
            for (MedicalRecord medicalRecord : dataAlert.getMedicalrecords()){
                if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())){
                    person.setMedicalRecord(medicalRecord);
                }
            }

            // TODO: 05/09/2020 mayby is good to add firestation to the person
        }
    }

    @Override
    public DataAlert getDataAlert(){
        return this.dataAlert;
    }

}
