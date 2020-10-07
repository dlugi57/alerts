package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MedicalRecordServiceTest {

    @TestConfiguration
    static class MedicalRecordServiceImplTestContextConfiguration {
        @Bean
        public MedicalRecordService medicalRecordService() {
            return new MedicalRecordServiceImpl();
        }
    }

    @Resource
    MedicalRecordService medicalRecordService;

    @MockBean
    MedicalRecordDao medicalRecordDao;

    public static List<MedicalRecord> medicalRecords = new ArrayList<>();

    static {
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984",
                new ArrayList<>(), new ArrayList<>()));
        medicalRecords.add(new MedicalRecord(
                "Jacob", "Boyd", "03/06/2015", new ArrayList<>(), new ArrayList<>()));
        medicalRecords.add(new MedicalRecord("Tenley", "Boyd",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    void getMedicalRecords() {
        // GIVEN
        given(medicalRecordDao.getMedicalRecords()).willReturn(medicalRecords);
        // WHEN
        List<MedicalRecord> medicalRecordsAll = medicalRecordService.getMedicalRecords();
        // THEN
        verify(medicalRecordDao, times(1)).getMedicalRecords();
        assertThat(medicalRecordsAll.size()).isEqualTo(3);
        assertThat(medicalRecordsAll).isEqualTo(medicalRecords);
        assertThat(medicalRecordsAll.get(0).getFirstName()).isEqualTo("John");
        assertThat(medicalRecordsAll.get(0).getLastName()).isEqualTo("Boyd");
    }

    @Test
    void addMedicalRecord() {
        // GIVEN
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(null);

        given(medicalRecordDao.addMedicalRecord(any(MedicalRecord.class))).willReturn(true);
        // WHEN
        boolean medicalRecord = medicalRecordService.addMedicalRecord(new MedicalRecord("Piotr",
                "Dlugosz",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
        // THEN
        verify(medicalRecordDao, times(1)).getMedicalRecordByFirstNameAndLastName(anyString(), anyString());
        verify(medicalRecordDao, times(1)).addMedicalRecord(any(MedicalRecord.class));

        assertThat(medicalRecord).isEqualTo(true);
    }

    @Test
    void addMedicalRecord_AlreadyExist() {
        // GIVEN
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecords.get(0));

        // WHEN
        boolean medicalRecord = medicalRecordService.addMedicalRecord(new MedicalRecord("Piotr",
                "Dlugosz",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
        // THEN
        verify(medicalRecordDao, times(1)).getMedicalRecordByFirstNameAndLastName(anyString(), anyString());

        assertThat(medicalRecord).isEqualTo(false);
    }

    @Test
    void getMedicalRecordByFirstNameAndLastName() {
        // GIVEN
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecords.get(0));
        // WHEN
        MedicalRecord medicalRecordByFirstNameAndLastName =
                medicalRecordService.getMedicalRecordByFirstNameAndLastName("John", "Boyd");
        // THEN
        verify(medicalRecordDao, times(1)).getMedicalRecordByFirstNameAndLastName(anyString(), anyString());
        assertThat(medicalRecordByFirstNameAndLastName).isEqualTo(medicalRecords.get(0));
        assertThat(medicalRecordByFirstNameAndLastName.getFirstName()).isEqualTo("John");
        assertThat(medicalRecordByFirstNameAndLastName.getLastName()).isEqualTo("Boyd");
    }

    @Test
    void updateMedicalRecord() {
        // GIVEN
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecords.get(0));
        given(medicalRecordDao.updateMedicalRecord(any(MedicalRecord.class))).willReturn(true);
        // WHEN
        boolean medicalRecord = medicalRecordService.updateMedicalRecord(new MedicalRecord("Piotr",
                "Dlugosz",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
        // THEN
        verify(medicalRecordDao, times(1)).getMedicalRecordByFirstNameAndLastName(anyString(), anyString());
        verify(medicalRecordDao, times(1)).updateMedicalRecord(any(MedicalRecord.class));

        assertThat(medicalRecord).isEqualTo(true);
    }

    @Test
    void updateMedicalRecord_DontExist() {
        // GIVEN
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(null);

        // WHEN
        boolean medicalRecord = medicalRecordService.updateMedicalRecord(new MedicalRecord("Piotr",
                "Dlugosz",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
        // THEN
        verify(medicalRecordDao, times(1)).getMedicalRecordByFirstNameAndLastName(anyString(), anyString());

        assertThat(medicalRecord).isEqualTo(false);
    }

    @Test
    void deleteMedicalRecord() {
        // GIVEN
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(medicalRecords.get(0));
        given(medicalRecordDao.deleteMedicalRecord(any(MedicalRecord.class))).willReturn(true);
        // WHEN
        boolean medicalRecord = medicalRecordService.deleteMedicalRecord(new MedicalRecord("Piotr",
                "Dlugosz",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
        // THEN
        verify(medicalRecordDao, times(1)).getMedicalRecordByFirstNameAndLastName(anyString(), anyString());
        verify(medicalRecordDao, times(1)).deleteMedicalRecord(any(MedicalRecord.class));

        assertThat(medicalRecord).isEqualTo(true);
    }

    @Test
    void deleteMedicalRecord_DontExist() {
        // GIVEN
        given(medicalRecordDao.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).willReturn(null);
        // WHEN
        boolean medicalRecord = medicalRecordService.deleteMedicalRecord(new MedicalRecord("Piotr",
                "Dlugosz",
                "03/06/1989", new ArrayList<>(), new ArrayList<>()));
        // THEN
        verify(medicalRecordDao, times(1)).getMedicalRecordByFirstNameAndLastName(anyString(), anyString());

        assertThat(medicalRecord).isEqualTo(false);
    }
}