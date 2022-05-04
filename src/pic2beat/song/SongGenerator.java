package pic2beat.song;

import java.util.List;

import jm.music.data.Phrase;
import pic2beat.harmonia.Chord;

/**
 * Custom way to generate a <code>SongPart</code>
 */
public interface SongGenerator {

    /**
     * Generates the drums
     * @return drums phrase as <code>Phrase</code> object
     */
    Phrase generateDrums();

    /**
     * Generates the chords
     * @return chords progression as a list
     */
    List<Chord> generateChords();

    /**
     * Generates the bass
     * @return bass phrase as <code>Phrase</code> object
     */
    Phrase generateBass();

    /**
     * Generates all 'secondary' instrument parts
     * @param role role of the instrument to generate the phrase
     * @return 'secondary' instrument phrase as <code>Phrase</code> object
     */
    Phrase generateInstrument(InstrumentRole role);

    /**
     * Needed to access the <code>SongPart</code> object that requested the generation
     * @param part <code>SongPart</code> that started the generation
     */
    void setSongPart(SongPart part);
}
