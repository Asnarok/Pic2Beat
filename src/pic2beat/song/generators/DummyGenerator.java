package pic2beat.song.generators;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.Harmonie.Chord;
import pic2beat.song.InstrumentRole;
import pic2beat.song.SongGenerator;

import java.util.ArrayList;
import java.util.List;

public class DummyGenerator implements SongGenerator {

    @Override
    public Phrase generateDrums() {
        final Phrase p = new Phrase();

        for(int i = 0; i < 4; i++) {
            p.addNote(new Note(36, 0.5));
            p.addNote(new Note(42, 0.5));
            p.addNote(new Note(42, 0.5));
            p.addNote(new Note(42, 0.5));
            p.addNote(new Note(38, 0.5));
            p.addNote(new Note(42, 0.5));
            p.addNote(new Note(42, 0.5));
            p.addNote(new Note(42, 0.5));
        }

        return p;
    }

    @Override
    public List<Chord> generateChords() {
        final List<Chord> l = new ArrayList<>();

        Chord c1 = new Chord("D#", Chord.ChordType.MAJ7);
        c1.length = 4;
        Chord c2 = new Chord("D", Chord.ChordType.DIM7);
        //c2.notes[2] = "G#";
        c2.length = 4;
        Chord c3 = new Chord("G", Chord.ChordType.DOM7);
        c3.length = 4;
        Chord c4 = new Chord("C", Chord.ChordType.MIN);
        c4.length = 4;

        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);

        return l;
    }

    @Override
    public Phrase generateBass(List<Chord> chords) {
        final Phrase p = new Phrase();

        p.addNote(new Note(JMC.DS1, 4));
        p.addNote(new Note(JMC.D1, 4));
        p.addNote(new Note(JMC.G1, 4));
        p.addNote(new Note(JMC.C1, 4));

        return p;
    }

    @Override
    public Phrase generateInstrument(InstrumentRole role, List<Chord> chords) {
        final Phrase p = new Phrase();

        if(role == InstrumentRole.THIRDS) {
            p.addNote(new Note(JMC.G4, 4, JMC.P));
            p.addNote(new Note(JMC.F4, 4, JMC.P));
            p.addNote(new Note(JMC.B4, 4, JMC.P));
            p.addNote(new Note(JMC.DS4, 4, JMC.P));
        } else if(role == InstrumentRole.FIFTHS) {
            p.addNote(new Note(JMC.AS4, 4, JMC.P));
            p.addNote(new Note(JMC.GS4, 4, JMC.P));
            p.addNote(new Note(JMC.D5, 4, JMC.P));
            p.addNote(new Note(JMC.G4, 4, JMC.P));
        }

        return p;
    }
}
