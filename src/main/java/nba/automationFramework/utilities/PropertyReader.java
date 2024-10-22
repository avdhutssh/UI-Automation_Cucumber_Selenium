package nba.automationFramework.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final Logger log = LogManager.getLogger(PropertyReader.class); // Log4j logger initialization
    public static boolean attachFile;
    private static volatile PropertyReader propInstance;

    private PropertyReader() {
    }

    public static PropertyReader getInstance() {
        if (propInstance == null) {
            synchronized (PropertyReader.class) {
                if (propInstance == null) {
                    propInstance = new PropertyReader();
                }
            }
        }
        return propInstance;
    }
    
    public String getProperty(String propertyName) {
        Properties prop = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                log.error("Property file not found in the classpath");
                return null;
            }
            prop.load(inputStream);
            String value = prop.getProperty(propertyName);
            if (value != null) {
                log.info("Property '{}' found with value: {}", propertyName, value);
            } else {
                log.warn("Property '{}' not found in the config file", propertyName);
            }
            return value;

        } catch (Exception e) {
            log.error("Exception occurred while reading property: " + propertyName, e);  // Log the exception with details
        }
        return null;
    }

}
