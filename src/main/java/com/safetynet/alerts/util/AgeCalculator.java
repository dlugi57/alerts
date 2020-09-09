package com.safetynet.alerts.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {

    public static int calculateAge(LocalDate birthDate) {

        LocalDate currentDate = LocalDate.now();

        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

}

//test

/*
public class AgeCalculatorTest {

    @Test
    public void testCalculateAge_Success() {
        // setup
        LocalDate birthDate = LocalDate.of(1961, 5, 17);
        // exercise
        int actual = AgeCalculator.calculateAge(birthDate, LocalDate.of(2016, 7, 12));
        // assert
        Assert.assertEquals(55, actual);
    }
}

 */
