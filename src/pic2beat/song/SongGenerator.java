package pic2beat.song;

import java.util.List;

import jm.music.data.Phrase;
import pic2beat.harmonia.Chord;

public interface SongGenerator {

    Phrase generateDrums();

    List<Chord> generateChords(int length);

    Phrase generateBass(List<Chord> chords);

    Phrase generateInstrument(InstrumentRole role, List<Chord> chords);
}
