package pic2beat.Harmonie;

import java.util.LinkedList;

public class TestFile {
	
	public static void main (String[] args) {
		
		LinkedList<Chord> progression = Harmonia.generateProgression("HAPPY", 20, 4);
		Chord Base = new Chord (0, 0);
		Harmonia.Progression.clear();
		Harmonia.Progression.add(new Chord ("E Maj"));
		Harmonia.Tonalite=Base;
		Harmonia.Matrix = Harmonia.InitMatProba();
		System.out.println(Harmonia.Progression.getLast().Nom);
		double I =0;
		double iii=0;
		double ii =0;
		double IV =0;
		double V =0;
		double V7=0;
		double vi=0;
		for (int i =0 ; i<100000; i++) {
			Chord c = Harmonia.ComputeNext();
			System.out.println(c.Nom);
			switch (c.Nom ) {
			case "A Maj": I++;
			break;
			case "B min": ii ++;
			break;
			case "C#min" :iii++;
			break;
			case "D Maj": IV ++;
			break;
			case "E Maj": V++;
			break;
			case "E 7": V7 ++;
			break;
			case "F#min": vi ++;
			break;
			}
		}
		System.out.println("I : " +I/1000 + ", ii : "+ ii/1000 +", iii : " +iii/1000+", IV : "+IV/1000+", V : "+V/1000+", V7 : "+V7/1000+", vi : "+vi/1000);
		System.out.println((I+ii+IV+V+V7+vi));
	}
}
