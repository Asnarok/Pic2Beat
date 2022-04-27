package pic2beat.melodia;

import jm.music.data.Note;
import pic2beat.melodia.rhythms.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Rhythm {

    private static final Class<? extends Rhythm>[] SUB_CLASSES = new Class[]{FullQuaverRhythm.class, FullSemiQuaverRhythm.class, TripletRhythm.class, OQTSQRhythm.class, TSQOQRhythm.class, SyncopetteRhythm.class};

    public static Rhythm randomRhythm() {
        final double random = Math.random();
        final int nbr = SUB_CLASSES.length;
        for(int i = 0; i < nbr; i++) {
            if(random < (i + 1) / (float) nbr) {
                try {
                    return SUB_CLASSES[i].getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

        return new FullQuaverRhythm();
    }

    private final List<Note> notes;

    public Rhythm() {
        this.notes = new ArrayList<>();
    }

    public final Rhythm apply(List<Note> notes) {
        if(notes.size() != getDurations().size()) {
            final String child = this.getClass().getName();
            throw new IllegalArgumentException("A " + child + " must contain " + getDurations().size() + " notes.");
        }

        this.notes.clear();
        this.notes.addAll(notes);

        for(int i = 0; i < getDurations().size(); i++) {
            this.notes.get(i).setRhythmValue(getDurations().get(i), true);
        }

        return this;
    }

    public Rhythm generate() {return this;}

    public final List<Note> asNotes() {
        return notes;
    }

    public abstract String getName();

    public abstract List<Double> getDurations();

    public int getNbrOfNotes() {
        return getDurations().size();
    }

}
