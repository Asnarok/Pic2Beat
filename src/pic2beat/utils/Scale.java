package pic2beat.utils;

public class Scale implements Scales{
	
	private static final String[] NOTES = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
	
	private int note, mode;

	public Scale(int note, int type) {
		this.note = note%12;
		this.mode = type;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public int[] getNotes() {
		int[] notes = new int[7];
		for(int i = 0; i < notes.length; i++) {
			notes[i] = note+GREGORIAN_INTERVALS[mode][i];
		}
		
		return notes;
	}
	
	public String toString() {
		return NOTES[note]+" "+GREGORIAN_MODES_LABELS[mode] + " scale";
	}

}
