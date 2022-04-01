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

public class MelodIA implements JMC {

	private static final MelodIA AI = new MelodIA();

	private MelodIA() {

	}

	public static MelodIA get() {
		return AI;
	}

	public int getNote(String s) {
		try {
			Field f = Pitches.class.getDeclaredField(s);
			System.out.println(f.getName());
			return f.getInt(null);

		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public String getNoteName(int n) {
		return Note.getName(n);
	}

	public Phrase phrase(final int[] currentChord) {

		Phrase p = new Phrase();

		// décision gamme ( avec config )
		// -> soit on reste dans une gamme prédef (tona du morceau)
		// -> soit on choisit un mode compatible

		if (!AppConfig.get().getParam(Param.TONALITY).equals("MODAL")) {
			String tona = AppConfig.get().getParam(Param.TONALITY);

			int tonality = getNote(tona + "0");

			System.out.println(tonality);

			for (int i = 0; i < 8; i++) {
				p.addNote(computeNextNote(p, currentChord));
			}
			return p;

		} else {

		}

		return p;
	}

	// TODO
	private Note computeNextNote(Phrase phr, final int[] currentChord) {

		final double prob = Math.random();
		final double[] probas = new double[7];
		for (int i = 0; i < MAJOR_SCALE.length; i++) {
			// ajouter l'integral à la somme
			// sachant que l'intégrale se fait à partir d'une fonction composée de n lois
			// normales centrées sur chaque note de l'accord, et que sigma augmente au cours
			// du remplissement de phr
			// if prob < currentRepartition return note
			probas[i] = computeProba(phr, currentChord, MAJOR_SCALE[i], 0d);

			if (prob < probas[i]) {
				Note toAdd = new Note(MAJOR_SCALE[i] + C4, Q);
				if (phr.getNoteArray().length > 1) {
					if (phr.getNote(phr.getNoteArray().length - 1).samePitch(toAdd)
							&& phr.getNote(phr.getNoteArray().length - 2).samePitch(toAdd)) {
						return computeNextNote(phr, currentChord);
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
		return new Note(C8, Q);
	}

	private static final int INTEGRAL_RESOLUTION = 1000;

	public double computeProba(Phrase p, int[] chord, int note, double width) {
		double proba = 0;

		for (int j : chord) {
			final double sigma = 1;
			final Function<Double, Double> gaussian = (x) -> 1 / (sigma * Math.sqrt(2 * Math.PI))
					* Math.exp(-0.5 * Math.pow((x + 1 - j % 12) / sigma, 2));
			proba += MathUtils.integrate(gaussian, -24d, note, INTEGRAL_RESOLUTION);
		}

		proba /= chord.length;

		return proba;
	}
}
