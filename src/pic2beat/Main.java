package pic2beat;

public class Main {

    private static AppProperties properties = AppProperties.get();
    private static AppConfig config = AppConfig.get();

    public static void main(String[] args) {
        System.out.println("Hello world! (suce pute)");
        System.out.println(properties.getProperty(AppProperties.Property.APP_NAME) + " " + properties.getProperty(AppProperties.Property.VERSION));
        System.out.println(config.getParam(AppConfig.Param.TEMPO) + " " + config.getParam(AppConfig.Param.TIME_SIGNATURE));
        config.setParam(AppConfig.Param.TEMPO, "90").save();
    }

}
