package com.safetynet.alerts.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
@ExtendWith(SpringExtension.class)
// TODO: 07/10/2020 how to test this one  
public class AgeCalculatorTest {

    @Test
    public void testCalculateAge_Success() {
        // setup
        LocalDate birthDate = LocalDate.of(1961, 5, 17);
        // exercise
        int actual = AgeCalculator.calculateAge(birthDate);
        // assert
        Assert.assertEquals(59, actual);
    }

    @Test
    public void testCalculateAge_Null() {

        // exercise
        int actual = AgeCalculator.calculateAge(null);
        // assert
        Assert.assertEquals(0, actual);
    }
}