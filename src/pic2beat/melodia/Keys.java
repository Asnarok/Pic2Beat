package pic2beat.melodia;

import java.util.LinkedList;
import java.util.List;

import pic2beat.harmonia.Chord;
import pic2beat.harmonia.Chord.ChordType;
import pic2beat.utils.Scale;
import pic2beat.utils.Scales;

public class Keys implements Scales{

	

	public static final ChordType[] MAJOR_SCALE_CHORDS = {ChordType.MAJ, ChordType.MIN, ChordType.MIN, ChordType.MAJ, ChordType.MAJ, ChordType.MIN, ChordType.DIM};
	
	public static List<Scale> getCompatibleScales(Chord c, Scale s) throws KeyException {
		
		Chord[] scaleChords = scaleChords(s.getNote(), s.getMode());
		
		int degree = -1;
		for(int i = 0; i < scaleChords.length; i++) {
			if(scaleChords[i].equals(c)) {
				degree = i;
				break;
			}
		}
		
		if(degree == -1) {
			throw new KeyException("Chord "+ c.toString()+" not in "+s.toString());
		}
		
		Chord[] neighborChords = new Chord[7];
		
		int knownChordMode = s.getMode();
		
		neighborChords[knownChordMode] = c;
		
		for(int i = 0; i < 7; i++) {
			if(i != knownChordMode) {
				neighborChords[i] = scaleChord(c.getNotes()[0], i, degree);
			}
		}
		
		List<Scale> compatibleScales = new LinkedList<Scale>();
		
		for(int i = 0; i < neighborChords.length; i++) {
			if(neighborChords[i].equals(c)) {
				compatibleScales.add(new Scale(neighborChords[i].getNotes()[0], i));
			}
		}
		
		return compatibleScales;
	}
	
	
	
	public static Chord[] scaleChords(int note, int mode) {
		int keyNote = note-NATURAL_MAJOR_INTERVALS[mode];
		Chord[] scaleChords = new Chord[7];
		scaleChords[0] = new Chord(keyNote, MAJOR_SCALE_CHORDS[mode]); 
		for(int i = 1; i < 7; i++) {
			scaleChords[i] = new Chord(NATURAL_MAJOR_INTERVALS[modulus(i+mode, 7)]+keyNote, MAJOR_SCALE_CHORDS[modulus(i+mode, 7)]);
		}
		return scaleChords;
	}

	public static Chord scaleChord(int note, int mode, int degree) {
		int keyNote = note-NATURAL_MAJOR_INTERVALS[mode];
		return new Chord(NATURAL_MAJOR_INTERVALS[modulus(degree+mode, 7)]+keyNote, MAJOR_SCALE_CHORDS[modulus(degree+mode, 7)]);
	}
	
	

	public static int modulus(int i, int n) {
		int mod = i%n;
		if(mod < 0)mod+=n;
		return mod;
	}

}
