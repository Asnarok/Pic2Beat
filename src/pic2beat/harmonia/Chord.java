package pic2beat.harmonia;
import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jm.JMC;
import jm.music.data.Note;
import pic2beat.melodia.MelodIA;

public class Chord implements JMC, Serializable{

	private static final long serialVersionUID = 501011515832370098L;
	public String name;
	public ChordType type;
	public String[] notes = new String[4];
	public double[] proba;
	public int length; //en nombre de temps de 1 à 4

	public Chord(String name, ChordType type) {
		this.name = name + type.getName();
		notes[0] = name; //fondamentale
		this.type = type; // Maj, min, Maj7, min7
		switch (type) {
			case MAJ:
				notes[1] = Note.getName(this.interval(notes[0], 4));// NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+4, 12));
				notes[2] = Note.getName(this.interval(notes[1], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+3, 12));
				break;
			case MIN:
				notes[1] = Note.getName(this.interval(notes[0], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+3, 12));
				notes[2] = Note.getName(this.interval(notes[1], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+4, 12));
				break;
			case MAJ7:
				notes[1] = Note.getName(this.interval(notes[0], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+4, 12));
				notes[2] = Note.getName(this.interval(notes[1], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+3, 12));
				notes[3] = Note.getName(this.interval(notes[2], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[2])+4, 12));
				break;
			case MIN7:
				notes[1] = Note.getName(this.interval(notes[0], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+3, 12));
				notes[2] = Note.getName(this.interval(notes[1], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+4, 12));
				notes[3] = Note.getName(this.interval(notes[2], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[2])+3, 12));
				break;
			case DOM7:
				notes[1] = Note.getName(this.interval(notes[0], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+4, 12));
				notes[2] = Note.getName(this.interval(notes[1], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+3, 12));
				notes[3] = Note.getName(this.interval(notes[2], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[2])+4, 12));
				break;
			case DIM7:
				notes[1] = Note.getName(this.interval(notes[0], 3));
				notes[2] = Note.getName(this.interval(notes[1], 3));
				notes[3] = Note.getName(this.interval(notes[2], 4));
				break;
		}
	}

	public Chord(int note, ChordType type) {
		this(Note.getName(note), type);
	}

	public Chord(Chord chord) {
		this(chord.notes[0], chord.type);
	}

	/**
	 * @param expression jazz notation of the chord, in roman numbers
	 * @param tona tonality in which we want our chord to be
	 * @param scale scale in which we want our chord to be
	 * @return a <code>Chord</code> object corresponding to jazz notation in the correct key
	 */
	public static Chord fromRoman(String expression, int tona, int[] scale) {
		final Pattern pattern = Pattern.compile("([iIvV]{1,3})(Maj|dim)?(7?)");
		final Matcher matcher = pattern.matcher(expression);
		if(matcher.find()) {
			final String degree = matcher.group(1);
			final String type = matcher.group(2);
			final boolean seventh = !matcher.group(3).equals("");
			final int root = romanToNote(degree, tona, scale);

			if(degree.toLowerCase().equals(degree)) {
				if(type == null) {
					if(seventh) {
						return new Chord(root, ChordType.MIN7);
					} else {
						return new Chord(root, ChordType.MIN);
					}
				} else if(type.equals("dim")) {
					if(seventh) {
						return new Chord(root, ChordType.DIM7);
					}
				}
			} else if(degree.toUpperCase().equalsIgnoreCase(degree)){
				if(type == null) {
					if(seventh) {
						return new Chord(root, ChordType.DOM7);
					} else {
						return new Chord(root, ChordType.MAJ);
					}
				} else if(type.equals("Maj")) {
					if(seventh) {
						return new Chord(root, ChordType.MAJ7);
					}
				}
			}
		}

		throw new IllegalArgumentException("'" + expression + "' is not a valid chord expression.");
	}

	@Override
	public String toString() {
		String rep = "";
		for (String note : notes) {
			rep += note + " ";
		}
		return rep;
	}

	/**
	 * @param note name of the note whose index is wanted
	 * @return the index of the note (Jmusic notation)
	 */
	public int toNum(String note) {
		return switch (note) {
			case "C" -> 0;
			case "C#" -> 1;
			case "D" -> 2;
			case "Eb" -> 3;
			case "E" -> 4;
			case "F" -> 5;
			case "F#" -> 6;
			case "G" -> 7;
			case "Ab" -> 8;
			case "A" -> 9;
			case "Bb" -> 10;
			case "B" -> 11;
			default -> -1;
		};
	}

	/**
	 * @param note the first note of the interval
	 * @param interval the interval between the two notes
	 * @return the second note of the interval
	 */
	public int interval(String note, int interval) {
		return (toNum(note) + interval) % 12;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Chord))
			return false;
		return (this.name.equals(((Chord)obj).name));
		
	}

	@Override
	public int hashCode() {		
		return this.name.hashCode();
	}

	/**
	 * @return an array containing the notes of the chord as indexes rather than <code>String</code>s
	 */
	public int[] getNotes() {
		int[] notes = new int[4];
		for(int i = 0; i < this.notes.length; i++) {
			if(this.notes[i] != null) {
				String formatted = this.notes[i].replace("#", "S").replace("b", "F").trim() + "3";
				notes[i] = MelodIA.get().getNote(formatted);
			}
		}
		
		return notes;
	}

	/**
	 * Retrieves a note index from relative roman notation and a key
	 * @param roman the roman notation (relative)
	 * @param tona the tonality we are in
	 * @param scale the scale we use
	 * @return the note index corresponding to the roman notation in the right key
	 */
	private static int romanToNote(String roman, int tona, int[] scale) {
		if(roman.equalsIgnoreCase("i")) {
			return tona;
		} else if(roman.equalsIgnoreCase("ii")) {
			return tona + scale[1];
		} else if(roman.equalsIgnoreCase("iii")) {
			return tona + scale[2];
		} else if(roman.equalsIgnoreCase("iv")) {
			return tona + scale[3];
		} else if(roman.equalsIgnoreCase("v")) {
			return tona + scale[4];
		} else if(roman.equalsIgnoreCase("vi")) {
			return tona + scale[5];
		} else if(roman.equalsIgnoreCase("vii")) {
			return tona + scale[6];
		}

		throw new IllegalArgumentException("'" + roman + "' is not a valid roman number (I-VII)");
	}

	/**
	 * Represents different types of chord
	 */
	public enum ChordType {

		MAJ("Maj", new int[]{4, 7}),
		MIN("min", new int[]{3, 7}),
		MAJ7("Maj7", new int[]{4, 7, 11}),
		MIN7("min7", new int[]{3, 7, 10}),
		DOM7("7", new int[]{4, 7, 10}),
		DIM7("dim7", new int[]{3, 6, 10});

		private final String name;
		private final int[] intervals;

		ChordType(String name, int[] intervals) {
			this.name = name;
			this.intervals = intervals;
		}

		public String getName() {
			return this.name;
		}

		public int[] getIntervals() {
			return this.intervals;
		}

		/**
		 * @param intervals array containing the intervals between the root note and the others
		 * @return the <code>ChordType</code> matching the intervals
		 */
		public static ChordType typeFromIntervals(int... intervals) {
			for(ChordType t : values()) {
				if(Arrays.equals(t.getIntervals(), intervals)) {
					return t;
				}
			}

			return null;
		}
	}
}