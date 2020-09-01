package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DataAlert;

import java.io.IOException;

public interface DataService {
    void init() throws IOException;

    DataAlert getDataAlert();
}
