package pic2beat.song;

import jm.music.data.Phrase;
import pic2beat.harmonia.Chord;

import java.util.List;
import java.util.ListResourceBundle;

public interface SongGenerator {

    Phrase generateDrums();

    List<Chord> generateChords();

    Phrase generateBass(List<Chord> chords);

    Phrase generateInstrument(InstrumentRole role, List<Chord> chords);
}
