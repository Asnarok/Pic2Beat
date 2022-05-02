package pic2beat.harmonia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import pic2beat.utils.JsonChordParser;

public class HarmonIA {
	public static LinkedList<Chord> progression = new LinkedList<>();
	public static Chord tonality;
	public ArrayList<Chord> Degres = new ArrayList<>();
	public Map<Chord, Map<Chord, Map<Chord, Integer>>> probaMatrix_0 = new HashMap<>(); // abscisse = accord passé,
	// ordonnée = présent, Zaxis = choix & pondé
	public static int carrure; // Nombre de temps par mesure
	public static String mood;
	public static int length;
	public static int tona;
	public static int[] scale;
	public static Map<Chord, Map<Chord, Integer>> matrix;

	private static final boolean VERBOSE = true;

	public static LinkedList<Chord> generateProgression(int tona, int[] scale, int length, int carrure) {

		if (VERBOSE) {
			System.out.println("--Generating chord progression--");
			System.out.println("-carrure : " + carrure);
			System.out.println("-length : " + length);
		}

		HarmonIA.carrure = carrure;
		HarmonIA.length = length;
		HarmonIA.tona = tona;
		HarmonIA.scale = scale;

		Chord.ChordType typ = Chord.ChordType.typeFromIntervals(scale[2], scale[4]);

		// initialisation de la tonalité
		tonality = new Chord(tona, typ);
		progression.add(tonality);
		progression.getLast().length = carrure;
		// System.out.println(Progression);
		matrix = initProbaMatrix();

		for (int mes = 1; mes < length; mes++) {

			progression.add(computeNext());
			// System.out.println(Progression);

		}
		// System.out.println("Progression générée : ");
		if (VERBOSE) {
			for (Chord c : progression) {
				if(c != null)System.out.print(c.name + " " + c.length + ", ");
			}
		}
		return progression;
	}

	public static Chord computeNext() {
		LinkedHashMap<Chord, Integer> potentialChords = null;

		for(Entry<Chord, Map<Chord, Integer>> entry : matrix.entrySet()) {
			if(entry.getKey().notes[0].equals(progression.getLast().notes[0])) { // TODO at least check if they are major/minor
				potentialChords = new LinkedHashMap<>(entry.getValue());
			}
		}

		if(potentialChords == null) {
			return null;
		}

		final int random = (int) (Math.random() * 100);

		final Iterator<Entry<Chord, Integer>> it = potentialChords.entrySet().stream().sorted(Map.Entry.comparingByValue()).iterator();

		int sum = 0;
		while(it.hasNext()) {
			final Entry<Chord, Integer> entry = it.next();
			sum += entry.getValue();
			if(random < sum) {
				entry.getKey().length = 4; // TODO
				return entry.getKey();
			}
		}

		return null;

//		Chord ch = null;
//
//		int sum = 0; // somme des probabilités associées aux accords potentiels
//		for (Entry<Chord, Integer> chord : matrix.get(progression.getLast()).entrySet()) {
//			sum += chord.getValue();
//		}
//		int var = (int) (Math.random() * sum);
//		ArrayList<Integer> temp = new ArrayList<>(matrix.get(progression.getLast()).values());
//		temp.sort(null);
//		int treshold = 0;
//		int i = 0;
//
//		do {
//			treshold += temp.get(i);
//			i++;
//
//		} while (i < temp.size() && var > treshold);
//
//		for (Entry<Chord, Integer> c : matrix.get(progression.getLast()).entrySet()) {
//			if (c.getValue() == temp.get(i - 1)) {
//				setChordLength(c.getKey());
//				ch = c.getKey();
//				// Progression.add(set.getKey());
//				// System.out.println(Progression);
//				break;
//			}
//		}
//		return ch;

	}

	public static Map<Chord, Map<Chord, Integer>> initProbaMatrix() {
		final Map<String, Map<String, Integer>> jsonMap = JsonChordParser.MAJOR;

		final Map<Chord, Map<Chord, Integer>> toReturn = new HashMap<>();

		for(Entry<String, Map<String, Integer>> entry1 : jsonMap.entrySet()) {
			final Map<Chord, Integer> inner = new HashMap<>();
			for(Entry<String, Integer> entry2 : entry1.getValue().entrySet()) {
				inner.put(Chord.fromRoman(entry2.getKey(), tona, scale), entry2.getValue());
			}
			toReturn.put(Chord.fromRoman(entry1.getKey(), tona, scale), inner);
		}

		return toReturn;

//		Map<Chord, Map<Chord, Integer>> probaMatrix = new HashMap<>(); // abscisse = accord présent, ordonnée = choix
//																		// possibles et pondérations
//		Map<Chord, Integer> deg1 = new HashMap<>();
//		deg1.put(new Chord(tonality), 5); // I // 5% de chance de rester sur le même accord
//		deg1.put(new Chord(tonality.interval(tona, 7), Chord.ChordType.DOM7), 10);// V7 Données récupérées sur
//																	// https://www.hooktheory.com/trends#node=1&key=A
//		deg1.put(new Chord(tonality.interval(tona, 7), 0), 34);
//		deg1.put(new Chord(tonality.interval(tona, 5), 0), 28);// IV // Et simplifiées en ne gardant que les choix les
//																	// plus probables
//		deg1.put(new Chord(tonality.interval(tona, 9), 1), 15);// vi // modulés pour atteindre 100%.
//		deg1.put(new Chord(tonality.interval(tona, 2), 1), 8); // ii
//		probaMatrix.put(new Chord(tonality.interval(tona, 0), 0), deg1);
//
//		Map<Chord, Integer> deg2 = new HashMap<>();
//		deg2.put(new Chord(tonality.interval(tona, 0), 0), 13); // I
//		deg2.put(new Chord(tonality.interval(tona, 2), 1), 1); // ii
//		deg2.put(new Chord(tonality.interval(tona, 4), 1), 15); // iii
//		deg2.put(new Chord(tonality.interval(tona, 5), 0), 17); // IV
//		deg2.put(new Chord(tonality.interval(tona, 7), 0), 8); // V
//		deg2.put(new Chord(tonality.interval(tona, 7), 4), 3); // V7
//		deg2.put(new Chord(tonality.interval(tona, 9), 1), 25); // vi
//		probaMatrix.put(new Chord(tonality.interval(tona, 2), 1), deg2);
//
//		Map<Chord, Integer> deg3 = new HashMap<>();
//		deg3.put(new Chord(tonality.interval(tona, 0), 0), 11); // I
//		deg3.put(new Chord(tonality.interval(tona, 2), 1), 4); // ii
//		deg3.put(new Chord(tonality.interval(tona, 4), 1), 2); // iii
//		deg3.put(new Chord(tonality.interval(tona, 5), 0), 52); // IV
//		deg3.put(new Chord(tonality.interval(tona, 7), 0), 10); // V
//		deg3.put(new Chord(tonality.interval(tona, 7), 4), 15); // V7
//		deg3.put(new Chord(tonality.interval(tona, 9), 1), 6); // vi
//		probaMatrix.put(new Chord(tonality.interval(tona, 4), 1), deg3);
//
//		Map<Chord, Integer> deg4 = new HashMap<>();
//		deg4.put(new Chord(tonality.interval(tona, 0), 0), 32); // I
//		deg4.put(new Chord(tonality.interval(tona, 2), 1), 6); // ii
//		deg4.put(new Chord(tonality.interval(tona, 4), 1), 4); // iii
//		deg4.put(new Chord(tonality.interval(tona, 5), 0), 1); // IV
//		deg4.put(new Chord(tonality.interval(tona, 7), 0), 26); // V
//		deg4.put(new Chord(tonality.interval(tona, 7), 4), 5); // V7
//		deg4.put(new Chord(tonality.interval(tona, 9), 1), 13); // vi
//		probaMatrix.put(new Chord(tonality.interval(tona, 5), 0), deg4);
//
//		Map<Chord, Integer> deg5 = new HashMap<>();
//		deg5.put(new Chord(tonality.interval(tona, 0), 0), 14); // I
//		deg5.put(new Chord(tonality.interval(tona, 2), 1), 11); // ii
//		deg5.put(new Chord(tonality.interval(tona, 4), 1), 2); // iii
//		deg5.put(new Chord(tonality.interval(tona, 5), 0), 26); // IV
//		deg5.put(new Chord(tonality.interval(tona, 7), 0), 3); // V
//		deg5.put(new Chord(tonality.interval(tona, 7), 4), 3); // V7
//		deg5.put(new Chord(tonality.interval(tona, 9), 1), 36); // vi
//		probaMatrix.put(new Chord(tonality.interval(tona, 7), 0), deg5);
//
//		Map<Chord, Integer> deg6 = new HashMap<>();
//		deg6.put(new Chord(tonality.interval(tona, 0), 0), 11); // I
//		deg6.put(new Chord(tonality.interval(tona, 2), 1), 11); // ii
//		deg6.put(new Chord(tonality.interval(tona, 4), 1), 8); // iii
//		deg6.put(new Chord(tonality.interval(tona, 5), 0), 32); // IV
//		deg6.put(new Chord(tonality.interval(tona, 7), 0), 23); // V
//		deg6.put(new Chord(tonality.interval(tona, 7), 4), 2); // V7
//		deg6.put(new Chord(tonality.interval(tona, 9), 1), 1); // vi
//		probaMatrix.put(new Chord(tonality.interval(tona, 9), 1), deg6);
//
//		Map<Chord, Integer> dom7 = new HashMap<>();
//		dom7.put(new Chord(tonality.interval(tona, 0), 0), 100);
//		probaMatrix.put(new Chord(tonality.interval(tona, 7), 4), dom7);
//
//		return probaMatrix;
	}

	public static void setChordLength(Chord chord) {
		int nbTemps = 0;
		for (Chord c : progression) {
			nbTemps += c.length;
		}
		int reste = nbTemps % carrure;
		reste = (reste == 0 ? 0 : 4 - reste);
//		System.out.println("Proccessing chord : "+chord.Nom + " DUREE : "+chord.duree);
//		System.out.println(Progression.getLast().Nom+ " duree : "+ Progression.getLast().duree);
//		System.out.println("duree totale : " + nbTemps + " reste : "+ reste);

		switch (reste) {

		case 0:
			int r = (int) (Math.random() * 100);
			if (r < 2) {
				chord.length = 3;
			} else if (r < 5) {
				chord.length = 1;
			} else if (r < 20) {
				chord.length = 2;
			} else {
				chord.length = 4;
			}
			break;

		case 1:
			int r1 = (int) (Math.random() * 100);
			if (r1 < 5) {
				chord.length = 2;
			} else {
				chord.length = 1;
			}
			break;

		case 2:
			int r2 = (int) (Math.random() * 100);
			if (r2 < 70) {
				chord.length = 2;
			} else {
				chord.length = 1;
			}
			break;

		case 3:
			int r3 = (int) (Math.random() * 100);
			if (r3 < 70) {
				chord.length = 1;
			} else if (r3 < 85) {
				chord.length = 2;
			} else {
				chord.length = 3;
			}
			break;

		}
	}
}
