package pic2beat;

import jm.JMC;
import jm.constants.ProgramChanges;
import jm.music.data.*;
import jm.util.View;
import jm.util.Write;
import pic2beat.melodia.MelodIA;

public class Main implements JMC {

    private static AppProperties properties = AppProperties.get();
    private static AppConfig config = AppConfig.get();

    public static void main(String[] args) {
        System.out.println("Hello world! (suce pute)");
        System.out.println(properties.getProperty(AppProperties.Property.APP_NAME) + " " + properties.getProperty(AppProperties.Property.VERSION));
        System.out.println(config.getParam(AppConfig.Param.TEMPO) + " " + config.getParam(AppConfig.Param.TIME_SIGNATURE));
        config.setParam(AppConfig.Param.TEMPO, "90").save();
        
        // Melodia tests
        Phrase phr = MelodIA.get().phrase(new int[]{C3, E3, G3});
        Phrase phr2 = MelodIA.get().phrase(new int[]{G3, B3, D4, F4});
        Score s = new Score("ff");
        Part p = new Part("Piano", PIANO, 0);
        Part p2 = new Part("Piano2", PIANO, 1);
        p.addPhrase(phr);
        p.addPhrase(phr2);
        CPhrase cp = new CPhrase();
        cp.addChord(new int[]{C3, E3, G3}, 4 * C);
        cp.addChord(new int[]{G3, B3, D4, F4}, 4 * C);
        p2.addCPhrase(cp);
        s.addPart(p);
        s.addPart(p2);
        View.show(s);
    }

}
