package com.safetynet.alerts;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlertsApplication {

    // Pour le log4j2
    private static final Logger logger = LogManager
            .getLogger(AlertsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
        logger.trace(".................................................................................");
        logger.debug(".................................................................................");
        logger.info(".................................................................................");
        logger.warn(".................................................................................");
        logger.error(".................................................................................");
    }

    // TODO: 09/09/2020 something about list instead of address and station in station model
    // TODO: 09/09/2020 how to create clone of object ?
    // TODO: create class AlertAppConfiguration at the same lever with @configuration adnotation onluy and put this bean inside
}







