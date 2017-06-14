package configuration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbt-gimaletdinov1-rr on 14.06.2017.
 */
public class SrvConfig {
    private Map<String, String> properties;
    private static SrvConfig instance;
    private final String confFileName = "./config.properties";

    private SrvConfig() throws IOException{
        properties = new HashMap<>();
        readProperties();
    }

    public static SrvConfig getInstance() throws IOException {
        if (instance == null) {
            instance = new SrvConfig();
        }
        return instance;
    }

    private void readProperties() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(confFileName));
        while (reader.ready()) {
            String readedLine = reader.readLine();
            parseString(readedLine);
        }
    }

    private void parseString(String string) {
        if (!string.startsWith("#")) {
            String[] props = string.trim().split("=");
            properties.put(props[0], props[1]);
        }
    }

    public String getProperty(String propertyName) throws Exception {
        String propertyValue = properties.get(propertyName);
        if(propertyValue == null){
            throw new RuntimeException("Property "+ propertyName + " not found in configuration file.");
        }
        return propertyValue;

    }


}
