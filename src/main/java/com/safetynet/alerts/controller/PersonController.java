package com.safetynet.alerts.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @GetMapping(value = "Test")
    public String test() {
        return "Ty chuju jebany";
    }

}
