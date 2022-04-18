package pic2beat.Harmonie;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jm.JMC;
import pic2beat.melodia.MelodIA;


public class Chord implements JMC {

	public String Nom;
	public String Type;
	public Chord Previous;
	public String[] Notes = new String[4]; 
	public double[] Proba;	
	public int duree ; //en nombre de temps de 1 à 4
	private static ArrayList<String> NotesToInt = new ArrayList<>(List.of("A ", "A#", "B ", "C ", "C#", "D ", "D#", "E ", "F ", "F#", "G ", "G#")); 
	private static ArrayList<String> TypeToInt = new ArrayList<>(List.of("Maj", "min", "Maj7", "min7", "7"));




	public Chord (String nom) {
		Nom = nom;
		Notes[0]= Nom.substring(0, 2); //fondamentale
		Type=Nom.substring(2); // Maj, min, Maj7, min7
		switch (Type) {

		case "Maj" :
			Notes[1] = this.toNote(this.interval(Notes[0], 4));// NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+4, 12));
			Notes[2] = this.toNote(this.interval(Notes[1], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+3, 12));
			break;
		case "min" :
			Notes[1] = this.toNote(this.interval(Notes[0], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+3, 12));
			Notes[2] = this.toNote(this.interval(Notes[1], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+4, 12));
			break;
		case "Maj7" :
			Notes[1] = this.toNote(this.interval(Notes[0], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+4, 12));
			Notes[2] = this.toNote(this.interval(Notes[1], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+3, 12));
			Notes[3] = this.toNote(this.interval(Notes[2], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[2])+4, 12));
			break;
		case "min7" :
			Notes[1] = this.toNote(this.interval(Notes[0], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+3, 12));
			Notes[2] = this.toNote(this.interval(Notes[1], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+4, 12));
			Notes[3] = this.toNote(this.interval(Notes[2], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[2])+3, 12));
			break;
		case "7" :
			Notes[1] = this.toNote(this.interval(Notes[0], 4));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[0])+4, 12));
			Notes[2] = this.toNote(this.interval(Notes[1], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[1])+3, 12));
			Notes[3] = this.toNote(this.interval(Notes[2], 3));//NotesToInt.get(modulo(NotesToInt.indexOf(Notes[2])+4, 12));
			break;
		}
	}
	public Chord (int note, int type) {
		this("" + NotesToInt.get(note) + TypeToInt.get(type));
	}
	public Chord (int note0, int note1, int note2, int note3) {
		Chord Maj = new Chord(toNote(note0)+"Maj");
		if (toNum(Maj.Notes[0])== note0 && toNum(Maj.Notes[1])== note1 && toNum(Maj.Notes[2])== note2 && toNum(Maj.Notes[3])== note3) {
			this.Nom=Maj.Nom;
			this.Notes=Maj.Notes;
			this.Type=Maj.Type;
		} else {
			Chord min = new Chord(toNote(note0)+"min");
			if (toNum(min.Notes[0])== note0 && toNum(min.Notes[1])== note1 && toNum(min.Notes[2])== note2 && toNum(min.Notes[3])== note3) {
				this.Nom=min.Nom;
				this.Notes=min.Notes;
				this.Type=min.Type;
			} else {
				Chord Maj7 = new Chord(toNote(note0)+"Maj7");
				if (toNum(Maj7.Notes[0])== note0 && toNum(Maj7.Notes[1])== note1 && toNum(Maj7.Notes[2])== note2 && toNum(Maj7.Notes[3])== note3) {
					this.Nom=Maj7.Nom;
					this.Notes=Maj7.Notes;
					this.Type=Maj7.Type;
				} else {
					Chord min7 = new Chord(toNote(note0)+"min7");
					if (toNum(min7.Notes[0])== note0 && toNum(min7.Notes[1])== note1 && toNum(min7.Notes[2])== note2 && toNum(min7.Notes[3])== note3) {
						this.Nom=min7.Nom;
						this.Notes=min7.Notes;
						this.Type=min7.Type;
					} else {
						Chord d7 = new Chord(toNote(note0)+"7");
						if (toNum(d7.Notes[0])== note0 && toNum(d7.Notes[1])== note1 && toNum(d7.Notes[2])== note2 && toNum(d7.Notes[3])== note3) {
							this.Nom=d7.Nom;
							this.Notes=d7.Notes;
							this.Type=d7.Type;
						} 
					} 
				} 
			} 
		} 
	}
	public int modulo (int num, int modulo) {
		while (num>=modulo) {
			num-=modulo;
		}
		return num;
	}
	public String toString() {
		String rep = new String();
		for (int i =0; i< Notes.length; i++) {
			rep += Notes[i]+ "  "; 
		}
		return rep;

	}
	public String toNote(int num) {
		return NotesToInt.get(num);
	}
	public int toNum(String note) {
		return NotesToInt.indexOf(note);
	}
	public int interval(int note, int interval) {
		return modulo(note + interval, 12);
	}
	public int interval(String note, int interval) {
		return modulo(toNum(note) + interval, 12);
	}
	public Chord interval (Chord c, int dist) {		// NOT IMPLEMENTED !!
		return null;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Chord ))
			return false;
		return (this.Nom.equals(((Chord)obj).Nom) );
		
	}
	@Override
	public int hashCode() {		
		return this.Nom.hashCode();
	}
	
	public int[] getNotes() {
		int[] notes = new int[4];
		for(int i = 0; i < this.Notes.length; i++) {
			if(this.Notes[i] != null) {
				String formatted = this.Notes[i].replace("#", "S").trim() + "3";
				notes[i] = MelodIA.get().getNote(formatted);
			}
		}
		
		return notes;
	}
	
}
