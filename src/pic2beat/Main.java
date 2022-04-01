package pic2beat;

import jm.JMC;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
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

        /*
        final double sigma = .1;

        final Function<Double, Double> gaussian = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-0.5 * Math.pow((x) / sigma, 2));
        final Function<Double, Double> gaussian2 = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-0.5 * Math.pow((x - 4) / sigma, 2));
        final Function<Double, Double> gaussian3 = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-0.5 * Math.pow((x - 7) / sigma, 2));

        double[] y = new double[12];
        for(int i = 0; i < 12; i++) {
            y[i] = MathUtils.integrate(gaussian, -24d, i, 1000);
            y[i] += MathUtils.integrate(gaussian2, -24d, i, 1000);
            y[i] += MathUtils.integrate(gaussian3, -24d, i, 1000);
            y[i] /= 3;
            for(int j = i - 1; j >= 0; j--) {
                y[i] -= y[j];
            }
        }

        System.out.println(MathUtils.integrate(Math::cos, -1, 1, 100));

        FileUtils.writeXYToFile("excel.csv", new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}, y);

         */
    }

}
