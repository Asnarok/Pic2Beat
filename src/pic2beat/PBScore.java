package pic2beat; 

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;
import jm.JMC;

public class PBScore implements JMC {
	private Score myScore;
	private int tempo;
	private int nbMesures;
	private int tona;
	//override
	public PBScore() {
		myScore = new Score();
	}
	public PBScore(int tona, int nbMesures){ // génération partition

		this.tempo= 100; //initialisation
		this.nbMesures= nbMesures;
		this.tona= tona;
		
		this.myScore=new Score(new Part(new Phrase(new Note(C4, MINIM))));	//génération de la partition ici
	}
	
	public void playMusic() {
		myScore.setTempo(tempo);
		Play.midi(myScore);

	}
	public void scoreMusic(int x, int y) {
		myScore.setTempo(tempo);
		View.notate(myScore,x,y);

	}
	public void saveMidi() {
		myScore.setTempo(tempo);
		Write.midi(myScore);
	}
	//setters
	public void setTempo(int newTempo) {
		this.tempo=newTempo;
	}
}

