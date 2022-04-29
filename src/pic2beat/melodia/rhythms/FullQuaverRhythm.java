package pic2beat.melodia.rhythms;

import jm.JMC;
import pic2beat.melodia.Rhythm;

import java.util.ArrayList;
import java.util.List;

public class FullQuaverRhythm extends Rhythm {

    private static final String NAME = "Quaver";
    private static final List<Double> DURATIONS;

    static {
        DURATIONS = new ArrayList<>();
        DURATIONS.add(JMC.Q);
        DURATIONS.add(JMC.Q);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<Double> getDurations() {
        return DURATIONS;
    }
}
