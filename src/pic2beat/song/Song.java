package pic2beat.song;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Song {

    private String title;

    private final List<SongPart> parts;
    private final List<Part> instruments;

    public Song(String title) {
        this.title = title;

        this.parts = new ArrayList<>();
        this.instruments = new ArrayList<>();
    }

    public void generate(Class<? extends SongGenerator> genClazz) {
        SongGenerator generator;
        try {
            generator = genClazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }

        // INTRO
        SongPart sp = new SongPart(this);
        sp.generate(generator);
        parts.add(sp);

        // CHORUS
        final SongPart chorus = new SongPart(this);
        chorus.generate(generator);
        parts.add(chorus);

        // VERSE
        sp = new SongPart(this);
        sp.generate(generator);
        parts.add(sp);

        // CHORUS
        sp = new SongPart(this);
        sp.generate(generator);
        parts.add(chorus);
    }

    public Song setLead(int instrument) {
        instruments.remove(getPartWithName("Lead"));
        instruments.add(new Part("Lead", instrument, 0));
        return this;
    }

    public Part getLead() {
        return getPartWithName("Lead");
    }

    public Song setChords(int instrument) {
        instruments.remove(getPartWithName("Chords"));
        instruments.add(new Part("Chords", instrument, 1));
        return this;
    }

    public Part getChords() {
        return getPartWithName("Chords");
    }

    public Song setBass(int instrument) {
        instruments.remove(getPartWithName("Bass"));
        instruments.add(new Part("Bass", instrument, 2));
        return this;
    }

    public Part getBass() {
        return getPartWithName("Bass");
    }

    public Song setDrums(int instrument) { // TODO DrumKit
        instruments.remove(getPartWithName("Drums"));
        instruments.add(new Part("Drums", instrument, 9));
        return this;
    }

    public Part getDrums() {
        return getPartWithName("Drums");
    }

    public Score toScore() {
        final Score score = new Score(this.title);

        for(Part p : instruments) {
            score.addPart(p);
        }

        for(SongPart sp : parts) {
            //System.out.println(sp);
            for(Map.Entry<Part, Object> entry : sp.getPhrases().entrySet()) {
                //System.out.println(entry);
                if(entry.getValue() instanceof Phrase)
                    entry.getKey().appendPhrase((Phrase)entry.getValue());
                else if(entry.getValue() instanceof CPhrase) {
                    CPhrase cp = ((CPhrase) entry.getValue()).copy();
                    cp.setAppend(true);
                    entry.getKey().addCPhrase(cp);
                }
            }
        }

        return score;
    }

    private Part getPartWithName(String name) {
        for(Part p : instruments) {
            if(p.getTitle().equals(name)) {
                return p;
            }
        }
        return null;
    }

}
