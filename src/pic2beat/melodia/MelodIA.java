package pic2beat.melodia;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import jm.JMC;
import jm.constants.Pitches;
import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.Main;
import pic2beat.utils.MathUtils;

/**
 * Singleton for melody generation over a specified chord
 */
public class MelodIA implements JMC, pic2beat.utils.Scales {

	private static final MelodIA AI = new MelodIA();

	private MelodIA() {}

	public static MelodIA get() {
		return AI;
	}

	/**
	 * @param s name of the note
	 * @return index of the corresponding note
	 */
	public int getNote(String s) {
		try {
			Field f = Pitches.class.getDeclaredField(s);
			return f.getInt(null);
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Generates a melody that is <code>chordLength</code> long and which we can play over the chord
	 * @param tonality tonality to use for the melody
	 * @param scale scale to use for the melody
	 * @param currentChord chord on which to generate the melody
	 * @param chordLength  <code>currentChord</code> length (in time)
	 * @return <code>Phrase</code> object containing the generated melody
	 */
	public Phrase phrase(int tonality, int[] scale, final int[] currentChord, double chordLength) {
		Phrase p = new Phrase();

		while (p.getBeatLength() < chordLength) {
			System.out.println("oui");
			p.addNoteList(computeNextRhythmic(p, currentChord, chordLength, tonality, scale).toArray(new Note[0]));
		}
		return p;
	}

	/**
	 * Chooses a random rhythm and randomly generates its notes
	 * @param phr <code>Phrase</code> object containing the already generated notes
	 * @param currentChord chord on which to generate the melody
	 * @param chordLength <code>currentChord</code> length (in time)
	 * @param tonality tonality to use for the melody
	 * @param scale scale to use for the melody
	 * @return a list of <code>Note</code> objects that is the generated melody with rhythm
	 */
	private List<Note> computeNextRhythmic(Phrase phr, final int[] currentChord, double chordLength, int tonality, int[] scale) {
		if(Main.DEBUG) {
			System.out.println("\t\t\t- Generating next beat notes...");
		}

		Rhythm r = Rhythm.randomRhythm();

		final List<Note> notes = new ArrayList<>();

		for(int i = 0; i < r.getNbrOfNotes(); i++) {
			notes.add(computeNextNote(phr, currentChord, chordLength, tonality, scale));
		}

		r.apply(notes);

		if(Main.DEBUG) {
			System.out.println("\t\t\t- Beat generated.");
		}

		return r.asNotes();
	}

	/**
	 * Chooses a note whose probability depends on those already generated
	 * @param phr <code>Phrase</code> object containing the already generated notes
	 * @param currentChord chord on which to generate the melody
	 * @param chordLength <code>currentChord</code> length (in time)
	 * @param tonality tonality to use for the melody
	 * @param scale scale to use for the melody
	 * @return a single <code>Note</code> object that is the next note of the melody
	 */
	private Note computeNextNote(Phrase phr, final int[] currentChord, double chordLength, int tonality, int[] scale) {
		final double prob = Math.random();
		final double[] probas = new double[7];
		for (int i = 0; i < scale.length; i++) {
			// ajouter l'integral à la somme
			// sachant que l'intégrale se fait à partir d'une fonction composée de n lois
			// normales centrées sur chaque note de l'accord, et que sigma augmente au cours
			// du remplissement de phr
			// if prob < currentRepartition return note
			probas[i] = calculateProba(phr, currentChord, scale[i], chordLength);

			if (prob < probas[i]) {
				Note toAdd = new Note(scale[i] + tonality + C3, 0.25);

				if(Main.DEBUG) {
					System.out.println("\t\t\t\t\t" + toAdd.getName() + " added");
				}

				return toAdd;
			}
		}

		if(Main.DEBUG) {
			System.out.println("\t\t\t\t\t REST added");
		}

		// actually the probas don't reach 1.0, so in case the random number is just less than 1, we return a rest
		return new Note(Note.REST, 0.25);
	}

	private final int INTEGRAL_RESOLUTION = 10000;

	/**
	 * Calculates the probability of a note being chosen, considering those already generated
	 * @param p <code>Phrase</code> object containing the already generated notes
	 * @param chord chord on which to generate the melody
	 * @param note note for which to calculate the probability
	 * @param chordLength <code>currentChord</code> length (in time)
	 * @return the probability (between 0 and 1) that the note has of being chosen
	 */
	private double calculateProba(Phrase p, int[] chord, int note, double chordLength) {
		double proba = 0;

		// the probability function is the sum of gaussian laws each centered on a note from the chord
		for (int j : chord) {
			double sigma = 0.01 + (p.getBeatLength()) / (chordLength) * 4; // from 0.01 to ~3.5

			final Function<Double, Double> gaussian = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-0.5 * Math.pow((x + 1 - j % 12) / sigma, 2));

			proba += MathUtils.integrate(gaussian, -24d, note, INTEGRAL_RESOLUTION);
		}

		proba /= chord.length;

		return proba;
	}
}
