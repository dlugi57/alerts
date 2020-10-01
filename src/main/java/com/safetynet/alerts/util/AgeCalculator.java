package com.safetynet.alerts.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;

/**
 * Convert date formats to age
 */
public class AgeCalculator {

    static final Logger logger = LogManager
            .getLogger(AgeCalculator.class);

    /**
     * COnvers local date to age
     *
     * @param birthDate local date
     * @return years
     */
    public static int calculateAge(LocalDate birthDate) {

        LocalDate currentDate = LocalDate.now();
        //if ((birthDate != null) && (currentDate != null)) {
        if (birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            logger.error("Method: calculateAge /**/ Message: The date is not available");
            return 0;
        }
    }

}

