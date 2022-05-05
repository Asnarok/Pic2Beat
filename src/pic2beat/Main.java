package pic2beat;

import jm.JMC;
import pic2beat.song.InstrumentRole;
import pic2beat.song.Song;
import pic2beat.song.Song.SongPartType;
import pic2beat.ui.ComposerFrame;

public class Main implements JMC {
	
	public static Song song; 

    public static void main(String[] args) {
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
	}

}
