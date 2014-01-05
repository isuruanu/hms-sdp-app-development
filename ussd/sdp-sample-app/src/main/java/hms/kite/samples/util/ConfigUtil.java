package hms.kite.samples.util;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileReader(ConfigUtil.class.getClassLoader().getResource("ussd-menu-messages.properties").getFile()));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String getConfig(String key) {
        return properties.getProperty(key);
    }
}
