package pic2beat.song;

import jm.music.data.Phrase;
import pic2beat.Harmonie.Chord;

import java.util.List;

public interface SongGenerator {

    Phrase generateDrums();

    List<Chord> generateChords();

    Phrase generateBass();

    Phrase generateInstrument(InstrumentRole role);
}
