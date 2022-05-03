package pic2beat;

import jm.JMC;
import jm.music.data.CPhrase;
import pic2beat.harmonia.Chord;
import pic2beat.harmonia.HarmonIA;
import pic2beat.song.InstrumentRole;
import pic2beat.song.Song;
import pic2beat.ui.ComposerFrame;
import pic2beat.utils.Scales;

public class Main implements JMC {

	public static ComposerFrame frame1;

	private static final AppProperties properties = AppProperties.get();
	private static final AppConfig config = AppConfig.get();
	
	public static Song song = new Song("En bas là"); 

    public static void main(String[] args) {
        System.out.println("Hello world! (suce pute)");
        System.out.println(properties.getProperty(AppProperties.Property.APP_NAME) + " " + properties.getProperty(AppProperties.Property.VERSION));
        System.out.println(config.getParam(AppConfig.Param.TEMPO) + " " + config.getParam(AppConfig.Param.TIME_SIGNATURE));
        //config.setParam(AppConfig.Param.TEMPO, "90").save();

        System.out.println(Chord.fromRoman("viidim7", 0, Scales.MAJOR_SCALE));

        CPhrase cp = new CPhrase();
        for(Chord c : HarmonIA.generateProgression(0, MAJOR_SCALE, 12, 4)) {
            cp.addChord(c.getNotes(), c.length);
        }

        // Melodia tests
        //LinkedList<Chord> progression = HarmonIA.generateProgression("HAPPY", 4, 4);
        //System.out.println(progression.size());

//        Song song = new Song("press F to pay respect")
//                        .setLead(VIBRAPHONE)
//                        .setChords(PIANO)
//                        .setDrums(PIANO) // TODO DrumKit
//                        .setBass(BASS)
//                        .addInstrument("Alto", InstrumentRole.THIRDS, VIOLA)
//                        .addInstrument("Violin", InstrumentRole.FIFTHS, VIOLIN);
//        song.generate(BasicGenerator.class);
//        Score score = song.toScore();
//        View.show(score);


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
        song = new Song("press F to pay respect")
              .setLead(VIBRAPHONE)
              .setChords(PIANO)
              .setDrums(PIANO) // TODO DrumKit
              .setBass(BASS)
              .addInstrument("Alto", InstrumentRole.THIRDS, VIOLA)
              .addInstrument("Violin", InstrumentRole.FIFTHS, VIOLIN);        
        ComposerFrame frame = new ComposerFrame();
        frame.newProject();
        frame.setVisible(true);
	}

}
