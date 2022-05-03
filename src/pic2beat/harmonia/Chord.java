package pic2beat.harmonia;
import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jm.JMC;
import jm.music.data.Note;
import pic2beat.melodia.MelodIA;


public class Chord implements JMC, Serializable{

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

//	public Chord(int note0, int note1, int note2, int note3) {
//		Chord c = new Chord(Note.getName(note0)+ChordType.MAJ.getName());
//		if (toNum(Maj.notes[0])== note0 && toNum(Maj.notes[1])== note1 && toNum(Maj.notes[2])== note2 && toNum(Maj.notes[3])== note3) {
//			this.name=Maj.name;
//			this.notes =Maj.notes;
//			this.type =Maj.type;
//		} else {
//			Chord min = new Chord(toNote(note0)+"min");
//			if (toNum(min.notes[0])== note0 && toNum(min.notes[1])== note1 && toNum(min.notes[2])== note2 && toNum(min.notes[3])== note3) {
//				this.name=min.name;
//				this.notes =min.notes;
//				this.type =min.type;
//			} else {
//				Chord Maj7 = new Chord(toNote(note0)+"Maj7");
//				if (toNum(Maj7.notes[0])== note0 && toNum(Maj7.notes[1])== note1 && toNum(Maj7.notes[2])== note2 && toNum(Maj7.notes[3])== note3) {
//					this.name=Maj7.name;
//					this.notes =Maj7.notes;
//					this.type =Maj7.type;
//				} else {
//					Chord min7 = new Chord(toNote(note0)+"min7");
//					if (toNum(min7.notes[0])== note0 && toNum(min7.notes[1])== note1 && toNum(min7.notes[2])== note2 && toNum(min7.notes[3])== note3) {
//						this.name=min7.name;
//						this.notes =min7.notes;
//						this.type =min7.type;
//					} else {
//						Chord d7 = new Chord(toNote(note0)+"7");
//						if (toNum(d7.notes[0])== note0 && toNum(d7.notes[1])== note1 && toNum(d7.notes[2])== note2 && toNum(d7.notes[3])== note3) {
//							this.name=d7.name;
//							this.notes =d7.notes;
//							this.type =d7.type;
//						}
//					}
//				}
//			}
//		}
//	}

	public String toString() {
		String rep = "";
		for(int i = 0; i < notes.length; i++) {
			rep += notes[i]+ " ";
		}
		return rep;
	}

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

	public int interval(int note, int interval) {
		if (interval != 11||interval != 10) {
			return (note + interval) % 12;
		}else 
			return note + interval;
	}

	public int interval(String note, int interval) {
		return (toNum(note) + interval) % 12;
	}

	public Chord interval (Chord c, int dist) {		// NOT IMPLEMENTED !!
		return null;
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
