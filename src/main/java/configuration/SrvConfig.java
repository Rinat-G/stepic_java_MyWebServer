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
    private final String commentSting = "#";
    private final String delimiter = "=";


    private SrvConfig() {
        properties = new HashMap<>();
        readProperties();
    }

    public static SrvConfig getInstance()  {
        if (instance == null) {
            instance = new SrvConfig();
        }
        return instance;
    }

    private void readProperties()  {

        try (BufferedReader reader = new BufferedReader(new FileReader(confFileName))){
            while (reader.ready()) {
                String readedLine = reader.readLine();
                parseString(readedLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    private void parseString(String string) {
        if (!string.startsWith(commentSting)) {
            String[] props = string.trim().split(delimiter);
            properties.put(props[0], props[1]);
        }
    }

    public String getProperty(String propertyName){
        String propertyValue = properties.get(propertyName);
        if(propertyValue == null){
            throw new RuntimeException("Property "+ propertyName + " not found in configuration file.");
        }
        return propertyValue;

    }

    public String getHostPort(){
        return getProperty("hostname") + ":" + getProperty("port");
    }

    public String getHttpHostPort(){
        return new StringBuilder()
                .append(getProperty("proto"))
                .append("://")
                .append(getProperty("hostname"))
                .append(":")
                .append(getProperty("port"))
                .toString();
    }



}
