package pic2beat.harmonia;

import pic2beat.utils.Scale;

public class HarmonicPart {
	
	private Chord chord;
	private Scale scale;

	public HarmonicPart(Chord c, Scale s) {
		setChord(c);
		setScale(s);
	}

	public Chord getChord() {
		return chord;
	}

	public void setChord(Chord chord) {
		this.chord = chord;
	}

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		this.scale = scale;
	}

}
