package pic2beat.harmonia.rhythms;

import java.util.ArrayList;
import java.util.List;
import pic2beat.harmonia.ChordRhythm;

public class WholeChordRhythm extends ChordRhythm{
	private static final String NAME = "Whole";
	private static final List<Double> DURATIONS;

	static {
		DURATIONS = new ArrayList<>();
		DURATIONS.add(4.0);
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
