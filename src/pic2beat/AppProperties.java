package pic2beat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton representing the file 'properties.app'
 */
public class AppProperties {

    private static final AppProperties INSTANCE = new AppProperties();
    private static final String FILE_NAME = "properties.app";

    private final java.util.Properties properties;

    public static AppProperties get() {
        return INSTANCE;
    }

    private AppProperties() {
        final Properties defaults = new Properties();
        defaults.setProperty(Property.APP_NAME.getName(), "Pic2Beat");
        defaults.setProperty(Property.VERSION.getName(), "1.0");

        properties = new Properties(defaults);
        try(FileInputStream fis = new FileInputStream(FILE_NAME)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(Property prop) {
        return properties.getProperty(prop.getName());
    }

    public enum Property {
        APP_NAME("APP_NAME"),
        VERSION("VERSION");

        private final String name;

        Property(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
