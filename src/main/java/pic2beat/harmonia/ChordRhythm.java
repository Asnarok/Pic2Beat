package pic2beat.harmonia;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import pic2beat.harmonia.rhythms.*;

/*
 * At this state, could be an enum
 */
public abstract class ChordRhythm {

    /**
     * Contains all classes that inherits from <code>ChordRhythm</code>
     */
    private static final Class<? extends ChordRhythm>[] SUB_CLASSES = new Class[]{WholeChordRhythm.class, TwoHalvesChordRhythm.class, TQOHChordRhythm.class, OHTQChordRhythm.class, SyncopeChordRhythm.class, ChaipaChordRhythm.class};

    /**
     * @return a random <code>ChordRhythm</code> object
     */
    public static ChordRhythm randomRhythm() {
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

        return new TwoHalvesChordRhythm();
    }
    
    public abstract String getName();
    
    public abstract List<Double> getDurations();
}
