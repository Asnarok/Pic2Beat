package pic2beat.harmonia;

import jm.music.data.Note;
import pic2beat.Main;
import pic2beat.utils.JsonChordParser;
import pic2beat.utils.Scales;

import java.util.*;
import java.util.Map.Entry;

public class HarmonIA {
	public static LinkedList<Chord> progression = new LinkedList<>();
	public static Chord tonality;
	public static int carrure; // Nombre de temps par mesure
	public static String mood;
	public static int length;
	public static int tona;
	public static int[] scale;
	public static Map<Chord, Map<Chord, Integer>> matrix;

	/**
	 * Generates a chord progression using statistics
	 * @param tona tonality to use for chords
	 * @param scale scale to use for chords
	 * @param length length of the progression, in number of chords
	 * @param carrure length of a bar (= length of a chord)
	 * @return a list of <code>Chord</code> objects representing the progression
	 */
	public static LinkedList<Chord> generateProgression(int tona, int[] scale, int length, int carrure) {
		if (Main.DEBUG) {
			System.out.println("\t\t\t- Generating chord progression");
		}

		HarmonIA.carrure = carrure;
		HarmonIA.length = length;
		HarmonIA.tona = tona;
		HarmonIA.scale = scale;

		Chord.ChordType typ = Chord.ChordType.typeFromIntervals(scale[2], scale[4]);

		progression.clear();

		// initialisation de la tonalité
		tonality = new Chord(tona, typ);
		progression.add(tonality);
		if (Main.DEBUG) {
			System.out.println("\t\t\t\tadded " + tonality.name);
		}
		progression.getLast().length = carrure;
		matrix = initProbaMatrix();

		for (int mes = 1; mes < length; mes++) {
			final Chord c = computeNext();
			if(c != null) {
				progression.add(c);
				if(Main.DEBUG) System.out.println("\t\t\t\tadded "+c.name);
			} else {
				if(Main.DEBUG) System.out.println("\t\t\t\trecomputing one chord");
				mes--;
			}

		}

		if(Main.DEBUG) {
			System.out.println("\t\t\t- Chord progression generated.");
		}
		return progression;
	}

	/**
	 * Generates the next chord using the last one and the statistics matrix
	 * @return the chord generated
	 */
	public static Chord computeNext() {
		LinkedHashMap<Chord, Integer> potentialChords = null;
		
		for(Entry<Chord, Map<Chord, Integer>> entry : matrix.entrySet()) {
			if(entry.getKey().notes[0].equals(progression.getLast().notes[0])) { // TODO at least check if they are major/minor
				potentialChords = new LinkedHashMap<>(entry.getValue());
			}
		}

		if (potentialChords == null) {
			return null;
		}

		final int random = (int) (Math.random() * 100);

		final Iterator<Entry<Chord, Integer>> it = potentialChords.entrySet().stream()
				.sorted(Map.Entry.comparingByValue()).iterator();

		int sum = 0;
		while (it.hasNext()) {
			final Entry<Chord, Integer> entry = it.next();
			sum += entry.getValue();
			if (random < sum) {
				entry.getKey().length = carrure;
				return entry.getKey();
			}
		}

		return null;
	}

	/**
	 * Initializes the matrix containing statistics on chord progressions.
	 * It transforms the matrix of <code>String</code>s into a matrix of <code>Chord</code>s
	 * @return the statistics matrix
	 */
	public static Map<Chord, Map<Chord, Integer>> initProbaMatrix() {
		final Map<String, Map<String, Integer>> jsonMap = (scale.equals(Scales.MAJOR_SCALE) ? JsonChordParser.MAJOR : JsonChordParser.MINOR);

		final Map<Chord, Map<Chord, Integer>> toReturn = new HashMap<>();

		for (Entry<String, Map<String, Integer>> entry1 : jsonMap.entrySet()) {
			final Map<Chord, Integer> inner = new HashMap<>();
			for (Entry<String, Integer> entry2 : entry1.getValue().entrySet()) {
				inner.put(Chord.fromRoman(entry2.getKey(), tona, scale), entry2.getValue());
			}
			toReturn.put(Chord.fromRoman(entry1.getKey(), tona, scale), inner);
		}

		return toReturn;
	}
}