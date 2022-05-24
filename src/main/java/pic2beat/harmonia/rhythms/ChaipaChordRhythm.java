package pic2beat.harmonia.rhythms;

import java.util.ArrayList;
import java.util.List;
import pic2beat.harmonia.ChordRhythm;

public class ChaipaChordRhythm extends ChordRhythm{
	private static final String NAME = "Truc Chelou";
	private static final List<Double> DURATIONS;

	static {
		DURATIONS = new ArrayList<>();
		DURATIONS.add(1.5);
		DURATIONS.add(0.5);
		DURATIONS.add(1.5);
		DURATIONS.add(0.5);
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
