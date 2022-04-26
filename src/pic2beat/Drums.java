package pic2beat;
import jm.JMC;
import jm.util.*;
import jm.music.data.*;
import jm.music.tools.Mod;

public class Drums implements JMC {
	public static void main(String[] args){
		Score pattern1 = new Score("Kit1"); 
		Part drums = new Part("Drums", 0, 9); // 9 = MIDI channel 10
		Phrase phrBD = new Phrase(0.0);
		Phrase phrSD = new Phrase(0.0);
		Phrase phrHH = new Phrase(0.0); 
		
		int nbMesures=4;
		int tempo=120;
		
		// make bass drum
		for(int i=0;i<4;i++){
			Note note = new Note(36, C);
			phrBD.addNote(note);
			Note rest = new Note(REST, C); //rest
			phrBD.addNote(rest);
		}
		
		// make snare drum
		for(int i=0;i<4;i++){
			Note rest = new Note(REST, C); //rest
			phrSD.addNote(rest);
			Note note = new Note(38, C);
			phrSD.addNote(note);
		}
		
		// make hats
		for(int i=0;i<15;i++){
			Note note = new Note(42, Q);
			phrHH.addNote(note);
		}
		Note note = new Note(46, Q); // open hi hat
		phrHH.addNote(note);
		
		// loop the drum pattern for nbMesures bars
		Mod.repeat(phrBD, nbMesures);
		Mod.repeat(phrSD, nbMesures);
		Mod.repeat(phrHH, nbMesures);
		
		// add phrases to the instrument (part)
		drums.addPhrase(phrBD);
		drums.addPhrase(phrSD);
		drums.addPhrase(phrHH);
		
		// add the drum part to a score.
		pattern1.addPart(drums);
		
		pattern1.setTempo(tempo);
		Play.midi(pattern1);
		
}
}
