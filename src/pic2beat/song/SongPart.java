package pic2beat.song;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import pic2beat.Harmonie.Chord;
import pic2beat.melodia.MelodIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongPart {

    private final Song song;
    private final HashMap<Part, Object> phrases;

    private final List<Chord> chords;

    public SongPart(final Song song) {
        this.song = song;
        this.phrases = new HashMap<>();
        this.chords = new ArrayList<>();
    }

    public void generate(SongGenerator generator) {
        Part p = song.getChords();
        if(p != null) {
            final List<Chord> chords = generator.generateChords();
            this.chords.clear();
            this.chords.addAll(chords);
            final CPhrase cp = new CPhrase();
            for(Chord c : chords) {
                cp.addChord(c.getNotes(), c.duree);
            }
            phrases.put(p, cp);

            p = song.getLead();
            if(p != null) {
                final Phrase lead = new Phrase();
                for(Chord c : chords) {
                    lead.addNoteList(MelodIA.get().phrase(c.getNotes(), c.duree).getNoteArray());
                }
                phrases.put(p, lead);
            }
        }

        p = song.getBass();
        if(p != null)
            phrases.put(p, generator.generateBass());

        p = song.getDrums();
        if(p != null)
            phrases.put(p, generator.generateDrums());

        for(Map.Entry<Part, InstrumentRole> entry : song.getInstrumentsWithRole().entrySet()) {
            phrases.put(entry.getKey(), generator.generateInstrument(entry.getValue()));
        }
    }

    public HashMap<Part, Object> getPhrases() {
        return phrases;
    }

}
