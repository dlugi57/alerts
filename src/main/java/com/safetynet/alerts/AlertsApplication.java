package com.safetynet.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlertsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

/*    @Bean
    CommandLineRunner init(DataService dataService) {
        return (args) -> {
            dataService.init();
        };
    }*/

    // TODO: create class AlertAppConfiguration at the same lever with @configuration adnotation onluy and put this bean inside
}







