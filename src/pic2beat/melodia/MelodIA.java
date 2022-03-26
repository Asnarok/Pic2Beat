package pic2beat.melodia;

import java.lang.reflect.Field;
import java.util.Random;

import jm.JMC;
import jm.constants.Pitches;
import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.AppConfig;
import pic2beat.AppConfig.Param;

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
	
	
	public Phrase phrase(final int[] currentChord/*, Chord next*/) {
		final int[] nextChord = {G3, B3, D4, F4};

		Phrase p = new Phrase();
		
		//décision gamme ( avec config )
		// -> soit on reste dans une gamme prédef (tona du morceau)
		// -> soit on choisit un mode compatible
		
		if(!AppConfig.get().getParam(Param.TONALITY).equals("MODAL")) {
			String tona = AppConfig.get().getParam(Param.TONALITY);
			
			int tonality = getNote(tona+"0");
			
			System.out.println(tonality);

			Random rand = new Random();

			for(int i = 0; i < 8; i++) {
				p.addNote(computeNextNote(p, currentChord));
			}
			return p;

		}else {
			
		}
		
		
		return p;
	}

	// TODO
	private Note computeNextNote(Phrase phr, final int[] currentChord) {
		final int[] nextChord = {G3, B3, D4, F4};

		final Random rand = new Random();

		if(phr.getNoteList().isEmpty()) {
			int prob = rand.nextInt(100);
			if(prob < 60) {
				return new Note(currentChord[0] + 12 * 2, Q);
			} else if (prob < 80) {
				return new Note(currentChord[1] + 12 * 2, Q);
			} else {
				return new Note(currentChord[2] + 12 * 2, Q);
			}
		} else {
			int temp = (int)(Math.random()*25 + 60);
			for (int k : MAJOR_SCALE) {
				if (temp % 12 == k) {
					return new Note(temp, Q);
				}
			}
			// si la note fait pas partie de la gamme, on recommence
			return computeNextNote(phr, currentChord);
		}
	}
}
