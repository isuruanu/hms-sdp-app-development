package hms.kite.app.util;

import com.google.common.base.Optional;
import hms.kite.app.sms.MoMessage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties applicationProperties = new Properties();
    private static final Properties messageProperties = new Properties();
    static {
        try {
            applicationProperties.load(new FileReader(ConfigUtil.class.getClassLoader().getResource("application.properties").getFile()));
            messageProperties.load(new FileReader(ConfigUtil.class.getClassLoader().getResource("messages.properties").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getApplicationConfig(String key) {
        return applicationProperties.getProperty(key);
    }

    public static Optional<String> getDisplayMessage(String key) {
        return Optional.fromNullable(messageProperties.getProperty(key));
    }
}

