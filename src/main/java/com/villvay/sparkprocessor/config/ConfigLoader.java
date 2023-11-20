package com.villvay.sparkprocessor.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public static ValidationConfig loadConfig() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("src/main/resources/validation_config.json"), ValidationConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
