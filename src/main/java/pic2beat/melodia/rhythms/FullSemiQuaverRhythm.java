package pic2beat.melodia.rhythms;

import jm.JMC;
import pic2beat.melodia.Rhythm;

import java.util.ArrayList;
import java.util.List;

public class FullSemiQuaverRhythm extends Rhythm {

    private static final String NAME = "SemiQuaver";
    private static final List<Double> DURATIONS;

    static {
        DURATIONS = new ArrayList<>();
        DURATIONS.add(JMC.SQ);
        DURATIONS.add(JMC.SQ);
        DURATIONS.add(JMC.SQ);
        DURATIONS.add(JMC.SQ);
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
