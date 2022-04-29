package pic2beat.melodia.rhythms;

import jm.JMC;
import jm.music.data.Note;
import pic2beat.melodia.Rhythm;

import java.util.ArrayList;
import java.util.List;

public class TripletRhythm extends Rhythm {

    private static final String NAME = "Triplet";
    private static final List<Double> DURATIONS;

    static {
        DURATIONS = new ArrayList<>();
        DURATIONS.add(JMC.QT);
        DURATIONS.add(JMC.QT);
        DURATIONS.add(JMC.QT);
    }

    @Override
    public List<Double> getDurations() {
        return DURATIONS;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
