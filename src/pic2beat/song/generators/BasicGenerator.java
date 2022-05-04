package pic2beat.song.generators;

import java.util.List;

import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.harmonia.Chord;
import pic2beat.harmonia.HarmonIA;
import pic2beat.song.InstrumentRole;
import pic2beat.song.SongGenerator;
import pic2beat.song.SongPart;

/**
 * Basic generator
 */
public class BasicGenerator implements SongGenerator {

    private SongPart part;

    @Override
    public Phrase generateDrums() {
        final Phrase p = new Phrase();
        for(int i = 0; i < part.getLength(); i++) {
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
        return HarmonIA.generateProgression(part.getSong().getTonality(), part.getSong().getScale(), part.getLength(), 4);
    }

    @Override
    public Phrase generateBass() {
        final Phrase p = new Phrase();

        for(Chord c : part.getChords()) {
        	int i = (int)(Math.random()*c.getNotes().length);
        	p.add(new Note(c.getNotes()[0]%12+24, c.length));
        }

        return p;
    }

    @Override
    public Phrase generateInstrument(InstrumentRole role) {
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

    @Override
    public void setSongPart(SongPart part) {
        this.part = part;
    }
}
