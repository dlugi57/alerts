package com.safetynet.alerts.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
}