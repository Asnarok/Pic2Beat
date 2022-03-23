package pic2beat.melodia;

import java.lang.reflect.Field;

import jm.JMC;
import jm.constants.Pitches;
import jm.music.data.Note;
import jm.music.data.Phrase;
import pic2beat.AppConfig;
import pic2beat.AppConfig.Param;

public class MelodIA implements JMC {
	
	private static final MelodIA AI = new MelodIA(); 
	
	private final int[] IONIAN_INTERVALS = {0, 2, 2, 1, 2, 2, 2};
	
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
	
	
	public Phrase phrase(/*Chord current, Chord next*/) {
		
		Phrase p = new Phrase();
		
		//décision gamme ( avec config )
		// -> soit on reste dans une gamme prédef (tona du morceau)
		// -> soit on choisit un mode compatible
		
		if(!AppConfig.get().getParam(Param.TONALITY).equals("MODAL")) {
			String tona = AppConfig.get().getParam(Param.TONALITY);
			
			int tonality = getNote(tona+"0");
			
			System.out.println(AF1+ " "+ GS1);
			
			for(int i : MAJOR_SCALE) {
				int newNote = i+tonality;
				System.out.println(getNoteName(newNote));
			}
			
			
			
		}else {
			
		}
		
		
		return p;
	}
}
