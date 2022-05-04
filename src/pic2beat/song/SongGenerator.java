package pic2beat.song;

import java.util.List;

import jm.music.data.Phrase;
import pic2beat.harmonia.Chord;

public interface SongGenerator {

    Phrase generateDrums();

    List<Chord> generateChords();

    Phrase generateBass();

    Phrase generateInstrument(InstrumentRole role);

    void setSongPart(SongPart part);
}
