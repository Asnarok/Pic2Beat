package pic2beat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton representing the file 'config.app'
 */
public class AppConfig {

    private static final AppConfig INSTANCE = new AppConfig();
    private static final String FILE_NAME = "config.app";

    private final java.util.Properties config;

    public static AppConfig get() {
        return INSTANCE;
    }

    private AppConfig() {
        final Properties defaults = new java.util.Properties();
        defaults.put(Param.TIME_SIGNATURE.getName(), "4/4");
        defaults.put(Param.TEMPO.getName(), "120");
        defaults.put(Param.TONALITY.getName(), "MODAL");
        defaults.put(Param.SCALE.getName(), "MAJOR_SCALE");
        // TODO

        config = new Properties(defaults);
        try(FileInputStream fis = new FileInputStream(FILE_NAME)) {
            config.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try(FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            config.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AppConfig setParam(Param param, String value) {
        config.setProperty(param.getName(), value);
        return this;
    }

    public String getParam(Param param) {
        return config.getProperty(param.getName());
    }

    public enum Param {
        TEMPO("TEMPO"),
        TIME_SIGNATURE("TIME_SIGNATURE"),
        TONALITY("TONALITY"),
        SCALE("SCALE");

        private final String name;

        Param(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
