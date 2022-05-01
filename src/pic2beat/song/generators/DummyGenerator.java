package pic2beat.song.generators;

import java.util.ArrayList;
import java.util.List;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.harmonia.Chord;
import pic2beat.harmonia.HarmonicPart;
import pic2beat.song.InstrumentRole;
import pic2beat.song.SongGenerator;
import pic2beat.utils.Scale;

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
    public List<HarmonicPart> generateChords() {
        final List<HarmonicPart> l = new ArrayList<>();

        Scale s = new Scale(JMC.DS0, 0);
        Chord c1 = new Chord("D#", Chord.ChordType.MAJ7);
        c1.length = 4;
        Chord c2 = new Chord("D", Chord.ChordType.DIM7);
        //c2.notes[2] = "G#";
        c2.length = 4;
        Chord c3 = new Chord("G", Chord.ChordType.DOM7);
        c3.length = 4;
        Chord c4 = new Chord("C", Chord.ChordType.MIN);
        c4.length = 4;

        l.add(new HarmonicPart(c1, s));
        l.add(new HarmonicPart(c2, s));
        l.add(new HarmonicPart(c3, s));
        l.add(new HarmonicPart(c4, s));

        return l;
    }

    @Override
    public Phrase generateBass(List<HarmonicPart> chords) {
        final Phrase p = new Phrase();

        p.addNote(new Note(JMC.DS1, 4));
        p.addNote(new Note(JMC.D1, 4));
        p.addNote(new Note(JMC.G1, 4));
        p.addNote(new Note(JMC.C1, 4));

        return p;
    }

    @Override
    public Phrase generateInstrument(InstrumentRole role, List<HarmonicPart> chords) {
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
