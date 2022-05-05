package pic2beat;

import jm.JMC;
import pic2beat.song.InstrumentRole;
import pic2beat.song.Song;
import pic2beat.song.Song.SongPartType;
import pic2beat.song.SongGenerator;
import pic2beat.song.generators.*;
import pic2beat.ui.ComposerFrame;

public class Main implements JMC {
	
	public static Song song;

	/**
	 * Enables logging in the terminal.
	 * To do so, add 'true' argument in your IDE launch options or write 'java -jar Pic2Beat.jar <basic | rock> true'
	 */
	public static boolean DEBUG;

	/**
	 * Used to choose the generator since it is not implemented in the GUI yet.
	 * Use {@code BasicGenerator.class} or {@code RockGenerator.class}
	 * Write 'java -jar Pic2Beat <basic | rock>' to choose a generator when starting
	 */
	public static Class<? extends SongGenerator> generator = BasicGenerator.class;

    public static void main(String[] args) {
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("basic")) {
				generator = BasicGenerator.class;
			} else if(args[0].equalsIgnoreCase("rock")) {
				generator = RockGenerator.class;
			}
		}

    	if(args.length > 1) {
    		DEBUG = Boolean.parseBoolean(args[1]);
		}

    	if(DEBUG) {
			System.out.println("Starting program...");
		}

		song = new Song("Your song")
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
