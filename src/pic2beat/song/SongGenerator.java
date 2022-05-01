package pic2beat.song;

import java.util.List;

import jm.music.data.Phrase;
import pic2beat.harmonia.HarmonicPart;

public interface SongGenerator {

    Phrase generateDrums();

    List<HarmonicPart> generateChords();

    Phrase generateBass(List<HarmonicPart> chords);

    Phrase generateInstrument(InstrumentRole role, List<HarmonicPart> chords);
}
