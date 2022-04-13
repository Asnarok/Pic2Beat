package pic2beat.Harmonie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class MainTest {
	public static LinkedList<Chord> Progression = new LinkedList<>();
	public static Chord Tonalite;
	public static ArrayList<Chord> Degres = new ArrayList<>();
	public Map<Chord, Map<Chord, Map<Chord, Integer>>> MatriceProba0 = new HashMap(); // abscisse = accord passé,
																						// ordonnée = présent, Zaxis =
																						// choix & pondé
	public ArrayList<int[]> a;

	public static void main(String[] args) {

		/*
		 * Degres.add(Tonalite); //Degres.add(new Chord(fon+2, ));
		 * 
		 * break; } /*int a = (int)(Math.random()*12); int b =(int)( Math.random()*4);
		 * Progression.add(new Chord(a, b)); System.out.println(a
		 * +" "+b+"\n"+Progression.get(0).Nom +" "+ Progression.get(0).Type );
		 */

	}

	public static Map<Chord, Map<Chord, Integer>> InitMatProba() {
		Map<Chord, Map<Chord, Integer>> MatriceProba = new HashMap(); // abscisse = accord présent, ordonnée = choix
																		// possibles et pondérations

		int numTon = Tonalite.toNum(Tonalite.Nom.substring(0, 2));
		Map<Chord, Integer> deg1 = new HashMap<>();
		deg1.put(Tonalite, 5); // I // 5% de chance de rester sur le même accord
		deg1.put(new Chord(Tonalite.interval(numTon, 7), 0), 24);// V // Données récupérées sur
																	// https://www.hooktheory.com/trends#node=1&key=A
		deg1.put(new Chord(Tonalite.interval(numTon, 7), 4), 20);
		deg1.put(new Chord(Tonalite.interval(numTon, 5), 0), 28);// IV // Et simplifiées en ne gardant que les choix les
																	// plus probables
		deg1.put(new Chord(Tonalite.interval(numTon, 9), 1), 15);// vi // modulés pour atteindre 100%.
		deg1.put(new Chord(Tonalite.interval(numTon, 2), 1), 8); // ii
		MatriceProba.put(Tonalite, deg1);
		System.out.println(Objects.equals(Tonalite, new Chord(Tonalite.toNum(Tonalite.Notes[0]), 0)));

		Map<Chord, Integer> deg5 = new HashMap<>();
		deg5.put(new Chord(Tonalite.interval(numTon, 7), 0), 2); // V
		deg5.put(new Chord(Tonalite.interval(numTon, 5), 0), 25); // IV
		deg5.put(new Chord(Tonalite.interval(numTon, 9), 1), 34); // vi
		deg5.put(new Chord(Tonalite.interval(numTon, 2), 1), 10); // ii
		deg5.put(new Chord(Tonalite.interval(numTon, 0), 0), 14); // I
		MatriceProba.put(new Chord(Tonalite.interval(numTon, 7), 0), deg5);

		Map<Chord, Integer> deg4 = new HashMap<>();
		deg4.put(new Chord(Tonalite.interval(numTon, 5), 0), 1); // IV
		deg4.put(new Chord(Tonalite.interval(numTon, 7), 0), 30); // V
		deg4.put(new Chord(Tonalite.interval(numTon, 9), 1), 12); // vi
		deg4.put(new Chord(Tonalite.interval(numTon, 2), 1), 5); // ii
		deg4.put(new Chord(Tonalite.interval(numTon, 0), 0), 32); // I
		MatriceProba.put(new Chord(Tonalite.interval(numTon, 5), 0), deg4);

		Map<Chord, Integer> deg6 = new HashMap<>();
		deg6.put(new Chord(Tonalite.interval(numTon, 7), 0), 23); // V
		deg6.put(new Chord(Tonalite.interval(numTon, 5), 0), 32); // IV
		deg6.put(new Chord(Tonalite.interval(numTon, 9), 1), 0); // vi
		deg6.put(new Chord(Tonalite.interval(numTon, 2), 1), 9); // ii
		deg6.put(new Chord(Tonalite.interval(numTon, 0), 0), 11); // I
		deg6.put(new Chord(Tonalite.interval(numTon, 4), 1), 8); // iii
		MatriceProba.put(new Chord(Tonalite.interval(numTon, 9), 0), deg6);

		Map<Chord, Integer> deg2 = new HashMap<>();
		deg2.put(new Chord(Tonalite.interval(numTon, 7), 0), 10); // V
		deg2.put(new Chord(Tonalite.interval(numTon, 5), 0), 17); // IV
		deg2.put(new Chord(Tonalite.interval(numTon, 7), 1), 25); // vi
		deg2.put(new Chord(Tonalite.interval(numTon, 7), 3), 1); // ii7
		deg2.put(new Chord(Tonalite.interval(numTon, 7), 0), 19); // I
		deg2.put(new Chord(Tonalite.interval(numTon, 7), 1), 15); // iii
		MatriceProba.put(new Chord(Tonalite.interval(numTon, 2), 0), deg2);

		Map<Chord, Integer> dom7 = new HashMap<>();
		dom7.put(Tonalite, 100);
		MatriceProba.put(new Chord(Tonalite.interval(numTon, 7), 4), dom7);

		System.out.println(MatriceProba.get(Tonalite));
		return MatriceProba;
	}

	public static LinkedList<Chord> generateProgression(String mood, int length) {

		LinkedList<Chord> progression = new LinkedList<Chord>();

		switch (mood) {
		// initialisation de la tonalité
		case "HAPPY":
			int fon = (int) (Math.random() * 12); // entre 0 et 4
			int typ = 0; // entre 0 et 4, 0 = Maj

			Tonalite = new Chord("A Maj");
			progression.add(Tonalite);
			Map<Chord, Map<Chord, Integer>> Matrix = InitMatProba();

			for (int mes = 1; mes < length; mes++) {
				System.out.println("LOOP starts");
				System.out.println(progression);
				int sum = 0;
				for (Entry<Chord, Integer> chord : Matrix.get(progression.getLast()).entrySet()) {
					sum += chord.getValue();
				}
				System.out.println("sum = " + sum);
				int var = (int) (Math.random() * sum);
				System.out.println("var = " + var);

				ArrayList<Integer> temp = new ArrayList<>(Matrix.get(progression.getLast()).values());
				temp.sort(null);
				System.out.println(temp);
				int treshold = 0;
				int i = 0;

				do {
					treshold += temp.get(i);
					i++;
					System.out.println(i - 1 + " " + treshold);
				} while (i < temp.size() - 1 && var > treshold);

				System.out.println("success");
				for (Entry<Chord, Integer> set : Matrix.get(progression.getLast()).entrySet()) {
					if (set.getValue() == temp.get(i - 1)) {
						progression.add(set.getKey());
						System.out.println("SUCCESS");
						System.out.println(set.getKey());
						break;
					} else
						System.out.println("FAIL");
				}

			}
		}
		return progression;
	}
}
