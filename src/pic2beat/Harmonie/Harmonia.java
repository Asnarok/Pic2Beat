package pic2beat.Harmonie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class Harmonia {
	public static  LinkedList<Chord> Progression = new LinkedList<>();
	public static  Chord Tonalite;
	public  ArrayList<Chord> Degres = new ArrayList<>();
	public Map<Chord, Map<Chord, Map<Chord, Integer>>> MatriceProba0 = new HashMap(); // abscisse = accord passé,
																		// ordonnée = présent, Zaxis = choix & pondé
	public static int Carrure;  //Nombre de temps par mesure
	public static String Mood;
	public static int Length;
	public static Map<Chord, Map<Chord, Integer>> Matrix;
	
	public static LinkedList<Chord> generateProgression(String mood, int length, int carrure) {

		Carrure = carrure;
		Mood = mood;
		Length = length; 
		
		switch (Mood) {
		//initialisation de la tonalité
		case "HAPPY" :
			int fon = 0; //entre 0 et 4 // TODO prendre en compte la tona passee dans la config
			int typ = 0; // entre 0 et 4, 0 = Maj

			Tonalite = new Chord(fon, typ);
			Progression.add(Tonalite);
			Progression.getLast().duree = Carrure;
			//System.out.println(Progression);
			Matrix = InitMatProba();


			for (int mes =1; mes< Length; mes++) {
				
				Progression.add(ComputeNext());
				//System.out.println(Progression);
				

			}
			//System.out.println("Progression générée : ");
//			for(Chord c : Progression) {
//				System.out.print(c.Nom+" " + c.duree +", ");
//			}
			return Progression;

			

		}
		return Progression;
	}
	public static Chord ComputeNext() {

		Chord ch = null;

		int sum = 0; //somme des probabilités associées aux accords potentiels
		for(Entry<Chord, Integer> chord : Matrix.get(Progression.getLast()).entrySet() ) {
			sum+= chord.getValue();
		}
		int var = (int)(Math.random()*sum );
		ArrayList<Integer> temp = new ArrayList<>(Matrix.get(Progression.getLast()).values());
		temp.sort(null);
		int treshold =0;
		int i=0;

		do {
			treshold += temp.get(i);
			i++;
			
		}while ( i< temp.size() && var> treshold);

		for (Entry<Chord, Integer> c : Matrix.get(Progression.getLast()).entrySet() ) {
			if (c.getValue()==temp.get(i-1)) {
				setDuree (c.getKey());
				ch = c.getKey();
				//Progression.add(set.getKey());		
				//System.out.println(Progression);
				break;
			}
		}
		return ch; 


	}

	public static Map<Chord, Map<Chord, Integer>> InitMatProba() {
		Map<Chord, Map<Chord, Integer>> MatriceProba = new HashMap(); // abscisse = accord présent, ordonnée = choix possibles et pondérations


		int numTon = Tonalite.toNum(Tonalite.Nom.substring(0, 2));
		Map<Chord, Integer> deg1 = new HashMap<>();
		deg1.put(new Chord(Tonalite.interval(numTon,0), 0), 5); 				   //I	// 5% de chance de rester sur le même accord
		deg1.put(new Chord(Tonalite.interval(numTon,7), 4), 10);//V7 Données récupérées sur https://www.hooktheory.com/trends#node=1&key=A
		deg1.put(new Chord(Tonalite.interval(numTon,7), 0), 34);
		deg1.put(new Chord(Tonalite.interval(numTon,5), 0), 28);//IV	// Et simplifiées en ne gardant que les choix les plus probables
		deg1.put(new Chord(Tonalite.interval(numTon,9), 1), 15);//vi	// modulés pour atteindre 100%.
		deg1.put(new Chord(Tonalite.interval(numTon,2), 1), 8); //ii
		MatriceProba.put(new Chord(Tonalite.interval(numTon,0), 0), deg1 );
		
		Map<Chord, Integer> deg2 = new HashMap<>();
		deg2.put(new Chord(Tonalite.interval(numTon,0), 0), 13);	//I
		deg2.put(new Chord(Tonalite.interval(numTon,2), 1), 1);		//ii
		deg2.put(new Chord(Tonalite.interval(numTon,4), 1), 15);	//iii
		deg2.put(new Chord(Tonalite.interval(numTon,5), 0), 17); 	//IV
		deg2.put(new Chord(Tonalite.interval(numTon,7), 0), 8);		//V
		deg2.put(new Chord(Tonalite.interval(numTon,7), 4), 3);		//V7
		deg2.put(new Chord(Tonalite.interval(numTon,9), 1), 25);	//vi
		MatriceProba.put(new Chord(Tonalite.interval(numTon,2), 1), deg2 );
		
		Map<Chord, Integer> deg3 = new HashMap<>();
		deg3.put(new Chord(Tonalite.interval(numTon,0), 0), 11);	//I
		deg3.put(new Chord(Tonalite.interval(numTon,2), 1), 4);		//ii
		deg3.put(new Chord(Tonalite.interval(numTon,4), 1), 2);		//iii
		deg3.put(new Chord(Tonalite.interval(numTon,5), 0), 52); 	//IV
		deg3.put(new Chord(Tonalite.interval(numTon,7), 0), 10);	//V
		deg3.put(new Chord(Tonalite.interval(numTon,7), 4), 15);	//V7
		deg3.put(new Chord(Tonalite.interval(numTon,9), 1), 6);		//vi
		MatriceProba.put(new Chord(Tonalite.interval(numTon,4), 1), deg3 );
		
		Map<Chord, Integer> deg4 = new HashMap<>();
		deg4.put(new Chord(Tonalite.interval(numTon,0), 0), 32);	//I
		deg4.put(new Chord(Tonalite.interval(numTon,2), 1), 6);		//ii
		deg4.put(new Chord(Tonalite.interval(numTon,4), 1), 4);		//iii
		deg4.put(new Chord(Tonalite.interval(numTon,5), 0), 1); 	//IV
		deg4.put(new Chord(Tonalite.interval(numTon,7), 0), 26);	//V
		deg4.put(new Chord(Tonalite.interval(numTon,7), 4), 5);		//V7
		deg4.put(new Chord(Tonalite.interval(numTon,9), 1), 13);	//vi
		MatriceProba.put(new Chord(Tonalite.interval(numTon,5), 0), deg4 );

		Map<Chord, Integer> deg5 = new HashMap<>();
		deg5.put(new Chord(Tonalite.interval(numTon,0), 0), 14);	//I
		deg5.put(new Chord(Tonalite.interval(numTon,2), 1), 11);		//ii
		deg5.put(new Chord(Tonalite.interval(numTon,4), 1), 2);		//iii
		deg5.put(new Chord(Tonalite.interval(numTon,5), 0), 26); 	//IV
		deg5.put(new Chord(Tonalite.interval(numTon,7), 0), 3);	//V
		deg5.put(new Chord(Tonalite.interval(numTon,7), 4), 3);		//V7
		deg5.put(new Chord(Tonalite.interval(numTon,9), 1), 36);	//vi
		MatriceProba.put(new Chord(Tonalite.interval(numTon,7), 0), deg5 );

		Map<Chord, Integer> deg6 = new HashMap<>();
		deg6.put(new Chord(Tonalite.interval(numTon,0), 0), 11);	//I
		deg6.put(new Chord(Tonalite.interval(numTon,2), 1), 11);		//ii
		deg6.put(new Chord(Tonalite.interval(numTon,4), 1), 8);		//iii
		deg6.put(new Chord(Tonalite.interval(numTon,5), 0), 32); 	//IV
		deg6.put(new Chord(Tonalite.interval(numTon,7), 0), 23);	//V
		deg6.put(new Chord(Tonalite.interval(numTon,7), 4), 2);		//V7
		deg6.put(new Chord(Tonalite.interval(numTon,9), 1), 1);	//vi
		MatriceProba.put(new Chord(Tonalite.interval(numTon,9), 1), deg6 );

		
		
		Map<Chord, Integer> dom7 =new HashMap<>();
		dom7.put(new Chord(Tonalite.interval(numTon,0), 0), 100);
		MatriceProba.put(new Chord(Tonalite.interval(numTon,7), 4), dom7);



		return MatriceProba;
	}

	public static void setDuree (Chord chord) {
		int nbTemps = 0;
		for (Chord c : Progression) {
			nbTemps +=c.duree;
		}
		int reste = nbTemps % Carrure;
		reste = (reste == 0 ? 0 : 4-reste);
//		System.out.println("Proccessing chord : "+chord.Nom + " DUREE : "+chord.duree);
//		System.out.println(Progression.getLast().Nom+ " duree : "+ Progression.getLast().duree);
//		System.out.println("duree totale : " + nbTemps + " reste : "+ reste);
		
		switch (reste) {
		
		case 0 :
			int r =(int)(Math.random()*100);
			if (r<2) {
				chord.duree = 3;
			} else if(r<5){
				chord.duree = 1;
			} else if (r<20) {
				chord.duree =2;
			} else {
				chord.duree = 4;}
			break;
		
		case 1 :
			int r1 =(int)(Math.random()*100);
			if (r1<5) {
				chord.duree = 2;
			} else {
				chord.duree = 1;}
			break;
		
		case 2 :
			int r2 =(int)(Math.random()*100);
			if (r2<70) {
				chord.duree = 2;
			} else {
				chord.duree = 1;}
			break;
		
		case 3:
			int r3 =(int)(Math.random()*100);
			if (r3<70) {
				chord.duree = 1;
			} else if(r3<85){
				chord.duree = 2;
			} else {
				chord.duree = 3;}
			break;
			
		}
	}
}
