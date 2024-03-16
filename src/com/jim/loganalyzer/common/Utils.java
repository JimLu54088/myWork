package com.jim.loganalyzer.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    private static final Properties properties = new Properties();

    private static final String propertyFileName = "Properties.properties";

    public static Properties getProperties() {
        if (properties.isEmpty()) {
            InputStream input = null;
            try {
                input = Utils.class.getResourceAsStream(propertyFileName);
                properties.load(input);


            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return properties;
    }

    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }


}
