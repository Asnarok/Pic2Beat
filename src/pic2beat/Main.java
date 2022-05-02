package pic2beat;

import jm.JMC;
import jm.music.data.Score;
import jm.util.View;
import pic2beat.harmonia.Chord;
import pic2beat.song.InstrumentRole;
import pic2beat.song.Song;
import pic2beat.song.generators.BasicGenerator;
import pic2beat.ui.ComposerFrame;
import pic2beat.utils.Scales;

public class Main implements JMC {

	public static ComposerFrame frame1;

	private static final AppProperties properties = AppProperties.get();
	private static final AppConfig config = AppConfig.get();
	
	public static Song song; 

    public static void main(String[] args) {
        System.out.println("Hello world! (suce pute)");
        System.out.println(properties.getProperty(AppProperties.Property.APP_NAME) + " " + properties.getProperty(AppProperties.Property.VERSION));
        System.out.println(config.getParam(AppConfig.Param.TEMPO) + " " + config.getParam(AppConfig.Param.TIME_SIGNATURE));
        //config.setParam(AppConfig.Param.TEMPO, "90").save();

        System.out.println(Chord.fromRoman("viidim7", 0, Scales.MAJOR_SCALE));

//        CPhrase cp = new CPhrase();
//        for(Chord c : HarmonIA.generateProgression(0, MAJOR_SCALE, 12, 4)) {
//            cp.addChord(c.getNotes(), c.length);
//        }

        // Melodia tests
        //LinkedList<Chord> progression = HarmonIA.generateProgression("HAPPY", 4, 4);
        //System.out.println(progression.size());

		// TODO decalage entre toutes les phrases -> les combiner

        song = new Song("press F to pay respect")
                        .setLead(VIBRAPHONE)
                        .setChords(PIANO)
                        .setDrums(PIANO) // TODO DrumKit
                        .setBass(BASS)
                        .addInstrument("Alto", InstrumentRole.THIRDS, VIOLA)
                        .addInstrument("Violin", InstrumentRole.FIFTHS, VIOLIN);

        

//        Chord c1 = new Chord("D#Maj7");
//        c1.duree = 4;
//        Chord c2 = new Chord("D min7");
//        c2.Notes[2] = "G#";
//        c2.duree = 4;
//        Chord c3 = new Chord("G 7");
//        c3.duree = 4;
//        Chord c4 = new Chord("C min");
//        c4.duree = 4;
//
//        LinkedList<Chord> progression = new LinkedList<>(Arrays.asList(c1, c2, c3, c4));
//
//        Phrase timpani = new Phrase();
//        Phrase drumsPhr = new Phrase();
//        for(Chord c : progression) {
//        	cp.addChord(c.getNotes(), c.duree);
//        	drumsPhr.add(new Note(36, 0.5));
//        	drumsPhr.add(new Note(42, 0.5));
//        	drumsPhr.add(new Note(42, 0.5));
//        	drumsPhr.add(new Note(42, 0.5)); // LAZY DRUMS
//        	drumsPhr.add(new Note(38, 0.5));
//            drumsPhr.add(new Note(42, 0.5));
//            drumsPhr.add(new Note(42, 0.5));
//            drumsPhr.add(new Note(42, 0.5));
//        	timpani.addNoteList(MelodIA.get().phrase(c.getNotes(), c.duree).getNoteArray());
//        }
//
//        Score s = new Score("press F to pay respect");
//        Part p = new Part("Lead", JMC.ACOUSTIC_SNARE, 0);
//        Part p2 = new Part("Piano", PIANO, 1);
//        Part drums = new Part("Drums", 0, 9);
//        p.addPhrase(timpani);
//        p2.addCPhrase(cp);
//        drums.addPhrase(drumsPhr);
//        s.addPart(p);
//        s.addPart(p2);
//        s.addPart(drums);
//        View.show(s);

		/*
		 * final double sigma = .1;
		 * 
		 * final Function<Double, Double> gaussian = (x) -> 1 / (sigma * Math.sqrt(2 *
		 * Math.PI)) * Math.exp(-0.5 * Math.pow((x) / sigma, 2)); final Function<Double,
		 * Double> gaussian2 = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI)) *
		 * Math.exp(-0.5 * Math.pow((x - 4) / sigma, 2)); final Function<Double, Double>
		 * gaussian3 = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-0.5 *
		 * Math.pow((x - 7) / sigma, 2));
		 * 
		 * double[] y = new double[12]; for(int i = 0; i < 12; i++) { y[i] =
		 * MathUtils.integrate(gaussian, -24d, i, 1000); y[i] +=
		 * MathUtils.integrate(gaussian2, -24d, i, 1000); y[i] +=
		 * MathUtils.integrate(gaussian3, -24d, i, 1000); y[i] /= 3; for(int j = i - 1;
		 * j >= 0; j--) { y[i] -= y[j]; } }
		 * 
		 * System.out.println(MathUtils.integrate(Math::cos, -1, 1, 100));
		 * 
		 * FileUtils.writeXYToFile("excel.csv", new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8,
		 * 9, 10, 11}, y);
		 * 
		 */    
        ComposerFrame frame = new ComposerFrame();
        frame.setVisible(true);
	}

}
