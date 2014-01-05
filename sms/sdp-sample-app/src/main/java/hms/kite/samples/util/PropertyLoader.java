package hms.kite.samples.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: kavi
 * Date: 8/20/12
 * Time: 8:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyLoader {

    private final static Logger LOGGER = Logger.getLogger(PropertyLoader.class.getName());

    private final static String PROPERTY_NAME="simple-client.properties";

    private static PropertyLoader instance;

    private Properties properties = new Properties();

    private PropertyLoader(){
        loadProperties();
    }

    public synchronized static PropertyLoader getInstance(){
        if(instance==null){
            instance=new PropertyLoader();
        }
        return instance;
    }

    private void loadProperties(){

        String path = System.getProperty("user.dir");

        String[] workingDir = path.split("stand-alone");

        try {
            InputStream in = new FileInputStream(workingDir[0]+"stand-alone/conf/simple-client.properties");
            properties.load(in);
            in.close();
        } catch (Exception e) {
            LOGGER.info(PROPERTY_NAME+" unable to load.");
            throw new IllegalStateException(e.toString());
        }
    }

    public String getText(String key){
        final String value=properties.getProperty(key);
        if(value==null||value.isEmpty())
            return null;
//            throw new MissingResourceException("Expected value null or empty", PROPERTY_NAME,key);
        else
            return value;
    }
}
