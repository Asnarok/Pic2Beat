package pic2beat.song.generators;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.Harmonie.Chord;
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

        Chord c1 = new Chord("D#Maj7");
        c1.duree = 4;
        Chord c2 = new Chord("D min7");
        c2.Notes[2] = "G#";
        c2.duree = 4;
        Chord c3 = new Chord("G 7");
        c3.duree = 4;
        Chord c4 = new Chord("C min");
        c4.duree = 4;

        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);

        return l;
    }

    @Override
    public Phrase generateBass() {
        final Phrase p = new Phrase();

        p.addNote(new Note(JMC.DS1, 4));
        p.addNote(new Note(JMC.D1, 4));
        p.addNote(new Note(JMC.G1, 4));
        p.addNote(new Note(JMC.C1, 4));

        return p;
    }
}
