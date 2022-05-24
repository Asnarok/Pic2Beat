package pic2beat.song.generators;

import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.Main;
import pic2beat.harmonia.Chord;
import pic2beat.harmonia.HarmonIA;
import pic2beat.song.InstrumentRole;
import pic2beat.song.SongGenerator;
import pic2beat.song.SongPart;

import java.util.List;
import java.util.stream.Collectors;

public class RockGenerator implements SongGenerator {

    private SongPart part;

    @Override
    public Phrase generateDrums() {
        if(Main.DEBUG) {
            System.out.println("\t\t- Generating drums...");
        }

        final Phrase p = new Phrase();
        for(int i = 0; i < part.getLength(); i++) {
            p.addNote(new Note(36, 1));
            p.addNote(new Note(42, 1));
            p.addNote(new Note(38, 1));
            p.addNote(new Note(42, 1));
        }

        return p;
    }

    @Override
    public List<Chord> generateChords() {
        if(Main.DEBUG) {
            System.out.println("\t\t-Generating chords...");
        }

        return HarmonIA.generateProgression(part.getSong().getTonality(), part.getSong().getScale(), part.getLength(), 4)
                .stream().map(c -> {
                    Chord replace = new Chord(c.notes[0], Chord.ChordType.POWER_CHORD);
                    replace.length = c.length;
                    return replace;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Phrase generateBass() {
        if(Main.DEBUG) {
            System.out.println("\t\t- Generating bass...");
        }

        final Phrase p = new Phrase();

        for(Chord c : part.getChords()) {
            p.addNote(new Note(c.getNotes()[0], 0.5));
            p.addNote(new Note(c.getNotes()[0], 0.5));
            p.addNote(new Note(c.getNotes()[0], 0.5));
            p.addNote(new Note(c.getNotes()[0], 0.5));
            p.addNote(new Note(c.getNotes()[0], 0.5));
            p.addNote(new Note(c.getNotes()[0], 0.5));
            p.addNote(new Note(c.getNotes()[1], 0.5));
            p.addNote(new Note(c.getNotes()[1], 0.5));
        }

        return p;
    }

    @Override
    public Phrase generateInstrument(InstrumentRole role) {
        return null;
    }

    @Override
    public void setSongPart(SongPart part) {
        this.part = part;
    }
}
