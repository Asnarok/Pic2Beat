package pic2beat;

import java.awt.EventQueue;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;


public class Main implements JMC{
	
	public static Frame frame1;

    private static AppProperties properties = AppProperties.get();
    private static AppConfig config = AppConfig.get();

    public static void main(String[] args){
        System.out.println("Hello world! (suce pute)");
        System.out.println(properties.getProperty(AppProperties.Property.APP_NAME) + " " + properties.getProperty(AppProperties.Property.VERSION));
        System.out.println(config.getParam(AppConfig.Param.TEMPO) + " " + config.getParam(AppConfig.Param.TIME_SIGNATURE));
        config.setParam(AppConfig.Param.TEMPO, "90").save();
        
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame1 = new Frame();
					frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        
    }

}
