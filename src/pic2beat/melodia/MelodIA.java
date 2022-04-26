package pic2beat.melodia;

import java.lang.reflect.Field;
import java.util.function.Function;

import jm.JMC;
import jm.constants.Pitches;
import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.AppConfig;
import pic2beat.AppConfig.Param;
import pic2beat.utils.MathUtils;

/**
 * Singleton for melody generation over a specified chord
 */
public class MelodIA implements JMC, pic2beat.utils.Scales {

	private static final MelodIA AI = new MelodIA();

	private static final boolean VERBOSE = false;

	private MelodIA() {

	}

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
			// System.out.println(f.getName());
			return f.getInt(null);

		} catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * @param s name of the scale
	 * @return list of intervals corresponding to the scale
	 */
	public int[] getScale(String s) {
		try {
			Field f = pic2beat.utils.Scales.class.getDeclaredField(s);
			// System.out.println(f.getName());
			return (int[]) f.get(null);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}

		return new int[7];
	}

	public String getNoteName(int n) {
		return Note.getName(n);
	}

	/**
	 * @param currentChord chord on which to generate the melody
	 * @param chordLength  <code>currentChord</code> length
	 * @return <code>Phrase</code> object containing the generated melody
	 */
	public Phrase phrase(final int[] currentChord, double chordLength) {

		Phrase p = new Phrase();

		// décision gamme ( avec config )
		// -> soit on reste dans une gamme prédef (tona du morceau)
		// -> soit on choisit un mode compatible

		if (!AppConfig.get().getParam(Param.TONALITY).equals("MODAL")) {
			String tona = AppConfig.get().getParam(Param.TONALITY);
			String scaleS = AppConfig.get().getParam(Param.SCALE);

			int tonality = getNote(tona + "0");
			int[] scale = getScale(scaleS);

			// System.out.println(tonality);

			while (p.getBeatLength() < chordLength) {
				p.addNote(computeNextNote(p, currentChord, chordLength, tonality, scale));
			}
			return p;

		} else {

		}

		return p;
	}

	// TODO
	private Note computeNextNote(Phrase phr, final int[] currentChord, double chordLength, int tonality, int[] scale) {
		final double prob = Math.random();
		final double[] probas = new double[7];
		for (int i = 0; i < scale.length; i++) {
			// ajouter l'integral à la somme
			// sachant que l'intégrale se fait à partir d'une fonction composée de n lois
			// normales centrées sur chaque note de l'accord, et que sigma augmente au cours
			// du remplissement de phr
			// if prob < currentRepartition return note
			probas[i] = computeProba(phr, currentChord, scale[i], 0d, chordLength);

			if (prob < probas[i]) {
				Note toAdd = new Note(scale[i] + tonality + C3, 0.25);
				if (phr.getNoteArray().length > 1) { // on va checker 2x en arrière donc il faut que le tableau soit
														// déja assez grand
					if (phr.getNote(phr.getNoteArray().length - 1).samePitch(toAdd)) {
						if (phr.getNote(phr.getNoteArray().length - 2).samePitch(toAdd)
								|| phr.getNote(phr.getNoteArray().length - 1).getDuration() > 0.5) {
							return computeNextNote(phr, currentChord, chordLength, tonality, scale); // les deux
																										// dernières
																										// notes sont
							// identiques
							// à celle choisie, c'est
							// insatisfaisant, donc on
						} else {
							phr.removeLastNote();
							return new Note(toAdd.getPitch(), 0.5);
						}
					}
				}
				return toAdd;
			}
		}

//		if (phr.getNoteList().isEmpty()) {
//
//
//
//		} else {
//			int temp = (int) (Math.random() * 25 + 60);
//			for (int k : MAJOR_SCALE) {
//				if (temp % 12 == k) {
//					return new Note(temp, Q);
//				}
//			}
//			// si la note fait pas partie de la gamme, on recommence
//			return computeNextNote(phr, currentChord);
//		}
		return new Note(Note.REST, 0.25);
	}

	private final int INTEGRAL_RESOLUTION = 10000;

	public double computeProba(Phrase p, int[] chord, int note, double width, double chordLength) {
		double proba = 0;
		if (VERBOSE) {
			System.out.println("--Melody probability computing--");
		}
		for (int j : chord) {

			if (VERBOSE)
				System.out.println("-length : " + p.getBeatLength() + "/" + chordLength);
			double sigma = 0.01 + (p.getBeatLength()) / (chordLength) * 4; // from 0.01 to ~3.5 TODO diviser par
																			// longueur accord
			if (VERBOSE)
				System.out.println("-sigma : " + sigma);
			final Function<Double, Double> gaussian = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI))
					* Math.exp(-0.5 * Math.pow((x + 1 - j % 12) / sigma, 2));
			proba += MathUtils.integrate(gaussian, -24d, note, INTEGRAL_RESOLUTION);

		}
		if (VERBOSE)
			System.out.println("-reached proba : " + proba);
		proba /= chord.length;

		return proba;
	}

	public static int[] getTriad(int root, boolean isMajor) {
		int third = isMajor ? 4 : 3;
		return new int[] { root, root + third, root + 7 };
	}
}
