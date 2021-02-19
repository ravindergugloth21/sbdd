package automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile {

public  static  Properties prop= new Properties();
    private static final ReadPropertyFile configProperties = new ReadPropertyFile();
    Properties properties;

    public ReadPropertyFile() {
        properties = new Properties();
        InputStream configFile = ReadPropertyFile.class.getClassLoader().getResourceAsStream("config.properties");


        try {
            properties.load(configFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ReadPropertyFile getInstance() {
        return configProperties;
    }

    public Properties getProperties() {
        return properties;
    }

    public Object clone()
            throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
