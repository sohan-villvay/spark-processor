package com.villvay.sparkprocessor.util;

import com.villvay.sparkprocessor.SparkApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
        public static Properties loadProperties() {
            Properties properties = new Properties();
            InputStream input = null;

            try {
                input = SparkApplication.class.getClassLoader().getResourceAsStream("application.properties");
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return properties;
        }
}
