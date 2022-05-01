package pic2beat.song.generators;

import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.harmonia.Chord;
import pic2beat.harmonia.HarmonIA;
import pic2beat.song.InstrumentRole;
import pic2beat.song.SongGenerator;
import pic2beat.utils.Scales;

import java.util.List;

public class BasicGenerator implements SongGenerator {

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
        return HarmonIA.generateProgression(0, Scales.MAJOR_SCALE, 4,  4);
    }

    @Override
    public Phrase generateBass(List<Chord> chords) {
        final Phrase p = new Phrase();

//        p.addNote(new Note(JMC.DS1, 4));
//        p.addNote(new Note(JMC.D1, 4));
//        p.addNote(new Note(JMC.G1, 4));
//        p.addNote(new Note(JMC.C1, 4));

        return p;
    }

    @Override
    public Phrase generateInstrument(InstrumentRole role, List<Chord> chords) {
        final Phrase p = new Phrase();

//        if(role == InstrumentRole.THIRDS) {
//            for(Chord c : chords) {
//                //p.addNote(new Note(c.Notes[0], c.duree));
//            }
//        } else if(role == InstrumentRole.FIFTHS) {
//            p.addNote(new Note(JMC.AS4, 4, JMC.P));
//            p.addNote(new Note(JMC.GS4, 4, JMC.P));
//            p.addNote(new Note(JMC.D5, 4, JMC.P));
//            p.addNote(new Note(JMC.G4, 4, JMC.P));
//        }

        return p;
    }
}
