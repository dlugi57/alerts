package com.safetynet.alerts.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * Convert date formats to age
 */
public class AgeCalculator {

    /**
     * COnvers local date to age
     * @param birthDate local date
     * @return years
     */
    public static int calculateAge(LocalDate birthDate) {

        LocalDate currentDate = LocalDate.now();
        //if ((birthDate != null) && (currentDate != null)) {
        if (birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

}

