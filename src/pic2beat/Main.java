package pic2beat;

import jm.JMC;
import pic2beat.song.InstrumentRole;
import pic2beat.song.Song;
import pic2beat.song.Song.SongPartType;
import pic2beat.ui.ComposerFrame;

public class Main implements JMC {
	
	public static Song song;

	/**
	 * Enables logging in the terminal.
	 * To do so, add 'true' argument in your IDE launch options or write 'java -jar Pic2Beat.jar true'
	 */
	public static boolean DEBUG;

    public static void main(String[] args) {
    	if(args.length > 0) {
    		DEBUG = Boolean.parseBoolean(args[0]);
		}

    	if(DEBUG) {
			System.out.println("Starting program...");
		}

        System.out.println("Hello world! (suce pute)");

		song = new Song("press F to pay respect")
                .setLead(VIBRAPHONE)
                .setChords(PIANO)
                .setDrums(PIANO) // TODO DrumKit
                .setBass(BASS)
                .addInstrument("Alto", InstrumentRole.THIRDS, VIOLA)
                .addInstrument("Violin", InstrumentRole.FIFTHS, VIOLIN)
				.addToStruct(SongPartType.INTRO)
				.addToStruct(SongPartType.VERSE)
				.addToStruct(SongPartType.CHORUS)
				.addToStruct(SongPartType.CHORUS)
				.setTempo(120);
        ComposerFrame frame = new ComposerFrame();
        frame.newProject();
        frame.setVisible(true);

        if(DEBUG) {
			System.out.println("Program started.");
		}
	}

}
