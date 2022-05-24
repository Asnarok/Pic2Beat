package pic2beat.harmonia.rhythms;

import java.util.ArrayList;
import java.util.List;
import pic2beat.harmonia.ChordRhythm;

public class TwoHalvesChordRhythm extends ChordRhythm{
	private static final String NAME = "Two Halves";
	private static final List<Double> DURATIONS;

	static {
		DURATIONS = new ArrayList<>();
		DURATIONS.add(2.0);
		DURATIONS.add(2.0);
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
