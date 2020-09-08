package com.safetynet.alerts.service;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService{

    @Autowired
    MedicalRecordDao medicalRecordDao;
    //PersonDao personDao;



/*    @Override
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        MedicalRecord medicalRecord = personDao.getByFirstNameAndLastName(firstName, lastName).getMedicalRecord();
        return medicalRecord;
    }*/

/*    @Override
    public boolean addMedicalRecord(MedicalRecord medicalRecord) {
        Person person = personDao.getByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());

        if (person != null && person.getMedicalRecord() == null ){
            person.setMedicalRecord(medicalRecord);
            personDao.updatePerson(person);
            return true;
        }
        return false;
    }*/

 //   @Override
/*    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        Person person = personDao.getByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (person != null && person.getMedicalRecord() != null ){
            personDao.updatePerson(person);
            return true;
        }

        return false;
    }*/



    @Override
    public List<MedicalRecord> getMedicalRecords(){
        return medicalRecordDao.getMedicalRecords();
    }

    @Override
    public boolean addMedicalRecord(MedicalRecord medicalRecord) {



/*        if (!personDao.getPersons().contains(person)) {
            personDao.addPerson(person);
            return true;
        }

        return false;*/


        MedicalRecord checkMedicalRecord = medicalRecordDao.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (checkMedicalRecord == null){
            medicalRecordDao.addMedicalRecord(medicalRecord);
            return true;
        }
        return false;

    }

    @Override
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        return medicalRecordDao.getMedicalRecordByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord checkMedicalRecord = medicalRecordDao.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (checkMedicalRecord != null){
            medicalRecordDao.updateMedicalRecord(medicalRecord);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord checkMedicalRecord = medicalRecordDao.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (checkMedicalRecord != null){
            medicalRecordDao.deleteMedicalRecord(medicalRecord);
            return true;
        }

        return false;
    }










}
