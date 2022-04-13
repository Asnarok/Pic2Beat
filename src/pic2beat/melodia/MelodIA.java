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

public class MelodIA implements JMC, pic2beat.utils.Scales {

	private static final MelodIA AI = new MelodIA();
	

	private MelodIA() {

	}

	public static MelodIA get() {
		return AI;
	}

	public int getNote(String s) {
		try {
			Field f = Pitches.class.getDeclaredField(s);
			//System.out.println(f.getName());
			return f.getInt(null);

		} catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int[] getScale(String s) {
		try {
			Field f = pic2beat.utils.Scales.class.getDeclaredField(s);
			System.out.println(f.getName());
			return (int[])f.get(null);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}

		return new int[7];
	}

	public String getNoteName(int n) {
		return Note.getName(n);
	}

	public Phrase phrase(final int[] currentChord, double chordLength) {

		Phrase p = new Phrase();

		// d�cision gamme ( avec config )
		// -> soit on reste dans une gamme pr�def (tona du morceau)
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
			// ajouter l'integral � la somme
			// sachant que l'int�grale se fait � partir d'une fonction compos�e de n lois
			// normales centr�es sur chaque note de l'accord, et que sigma augmente au cours
			// du remplissement de phr
			// if prob < currentRepartition return note
			probas[i] = computeProba(phr, currentChord, scale[i], 0d);
			
			if (prob < probas[i]) {
				Note toAdd = new Note(scale[i] + tonality + C3, 0.25);
				if (phr.getNoteArray().length > 1) { // on va checker 2x en arri�re donc il faut que le tableau soit
														// d�ja assez grand
					if (phr.getNote(phr.getNoteArray().length - 1).samePitch(toAdd)) {
						if (phr.getNote(phr.getNoteArray().length - 2).samePitch(toAdd)
								|| phr.getNote(phr.getNoteArray().length - 1).getDuration() > 0.5) {
							return computeNextNote(phr, currentChord, chordLength, tonality, scale); // les deux derni�res notes sont
																					// identiques
																					// � celle choisie, c'est
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

	private static final int INTEGRAL_RESOLUTION = 10000;

	public double computeProba(Phrase p, int[] chord, int note, double width) {
		double proba = 0;

		for (int j : chord) {
			double sigma = 0.1+p.getBeatLength(); // TODO diviser par longueur accord
			System.out.println("sigma : " + sigma);
			final Function<Double, Double> gaussian = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI))
					* Math.exp(-0.5 * Math.pow((x + 1 - j % 12) / sigma, 2));
			proba += MathUtils.integrate(gaussian, -24d, note, INTEGRAL_RESOLUTION);
		}

		proba /= chord.length;

		return proba;
	}
	
	public static int[] getTriad(int root, boolean isMajor) {
		int third = isMajor ? 4 : 3;
		return new int[] {root, root + third, root + 7};
	}
}
