package pic2beat.song;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jm.JMC;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import pic2beat.Main;
import pic2beat.utils.Scales;

/**
 * Represents the song
 */
public class Song implements Serializable {

	private static final long serialVersionUID = 1588682771531217718L;

	private String title;

	private List<SongPart> parts;
	private HashMap<Part, InstrumentRole> instruments;

	private List<SongPartType> structure;

	private SongPart chorus;

	private int tempo = 80;

	private int tonality;

	private int[] scale = Scales.MAJOR_SCALE;

	public Song(String title) {
		this.title = title;

		this.parts = new ArrayList<>();
		this.instruments = new HashMap<>();
		this.structure = new ArrayList<>();
		this.setDrums(JMC.DRUM);
	}

	/**
	 * Starts the song generation using the given generator
	 * @param genClazz generator to use for song generation
	 */
	public void generate(Class<? extends SongGenerator> genClazz) {
		if(Main.DEBUG) {
			System.out.println("Generating song...");
		}

		SongGenerator generator;
		try {
			generator = genClazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
			return;
		}

		chorus = null;

		for (SongPartType t : structure) { // Il faut que t'édit direct la list de SongPart avec l'ui comme
									       // ça quand tu les initialises tu passes length direct
			if (t == SongPartType.CHORUS) {
				if (chorus == null) {
					SongPart sp = new SongPart(this, t, 4);
					sp.generate(generator);
					parts.add(sp);
					chorus = sp;
				} else {
					parts.add(chorus);
				}
			} else {
				SongPart sp = new SongPart(this, t, 4);
				sp.generate(generator);
				parts.add(sp);
			}
		}

		if(Main.DEBUG) {
			System.out.println("Song generated.");
		}
	}

	public void addSongPart(SongPart p) {
		this.parts.add(p);
	}

	public Song setLead(int instrument) {
		instruments.remove(getPartWithName("Lead"));
		instruments.put(new Part("Lead", instrument, 0), InstrumentRole.LEAD);
		return this;
	}

	public Part getLead() {
		return getPartWithName("Lead");
	}

	public Song setChords(int instrument) {
		instruments.remove(getPartWithName("Chords"));
		instruments.put(new Part("Chords", instrument, 1), InstrumentRole.CHORDS);
		return this;
	}

	public Part getChords() {
		return getPartWithName("Chords");
	}

	public Song setBass(int instrument) {
		instruments.remove(getPartWithName("Bass"));
		instruments.put(new Part("Bass", instrument, 2), InstrumentRole.BASS);
		return this;
	}
	

	public Part getBass() {
		return getPartWithName("Bass");
	}

	public Song setDrums(int instrument) { // TODO DrumKit
		instruments.remove(getPartWithName("Drums"));
		instruments.put(new Part("Drums", instrument, 9), InstrumentRole.DRUMS);
		return this;
	}

	public Part getDrums() {
		return getPartWithName("Drums");
	}

	private int instCount = 0;

	/**
	 * Adds any other instrument which can have a 'secondary' role
	 * @param name name of the instrument/part
	 * @param role role of the instrument
	 * @param instrument Jmusic instrument index
	 * @return the current object
	 */
	public Song addInstrument(String name, InstrumentRole role, int instrument) {
		if (name.equals("Drums") || name.equals("Bass") || name.equals("Chords") || name.equals("Lead"))
			throw new IllegalArgumentException("Instrument name cannot be " + name + ".");

		final Part toAdd = new Part(name, instrument, instCount + 3);

		instruments.remove(getPartWithName(name));
		instruments.put(toAdd, role);

		instCount++;
		return this;
	}

	/**
	 * Removes a 'secondary' instrument
	 * @param name name of the instrument to remove
	 */
	public void removeInstrument(String name) {
		if (name.equals("Drums") || name.equals("Bass") || name.equals("Chords") || name.equals("Lead"))
			throw new IllegalArgumentException("Cannot remove " + name + ". It is a primary instrument.");

		instruments.remove(getPartWithName(name));
		instCount--;
	}

	/**
	 * @return a map that associates all 'secondary' instruments with their role
	 */
	public Map<Part, InstrumentRole> getInstrumentsWithRole() {
		return instruments.entrySet().stream()
				.filter(e -> !(e.getKey().getTitle().equals("Lead") || e.getKey().getTitle().equals("Chords")
						|| e.getKey().getTitle().equals("Drums") || e.getKey().getTitle().equals("Bass")))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	/**
	 * Finalizes song generation and transforms it into a <code>Score</code> object
	 * @return a Jmusic <code>Score</code> object which represents the song
	 */
	public Score toScore() {
		if(Main.DEBUG) {
			System.out.println("Generating score...");
		}

		final Score score = new Score(this.title);

		for (Part p : instruments.keySet()) {
			p.empty();
			score.addPart(p);
		}

		for (SongPart sp : parts) {
			for (Map.Entry<Part, Object> entry : sp.getPhrases().entrySet()) {
				if (entry.getValue() instanceof Phrase) {
					entry.getKey().appendPhrase((Phrase) entry.getValue());
				} else if (entry.getValue() instanceof CPhrase) {
					CPhrase cp = ((CPhrase) entry.getValue()).copy();
					cp.setAppend(true);
					entry.getKey().addCPhrase(cp);
				}
			}
		}

		score.setTempo(tempo);

		if(Main.DEBUG) {
			System.out.println("Score generated.");
		}

		return score;
	}

	public Song setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getTitle() {
		return this.title;
	}

	private Part getPartWithName(String name) {
		for (Part p : instruments.keySet()) {
			if (p.getTitle().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public List<SongPartType> getStructure() {
		return structure;
	}

	public Song addToStruct(SongPartType type) {
		this.structure.add(type);
		return this;
	}

	public Song setStruct(List<SongPartType> struct) {
		this.structure = struct;
		return this;
	}

	public void removeFromStruct(int index) {
		this.structure.remove(index);
	}

	public Song setTempo(int tempo) {
		this.tempo = tempo;
		return this;

	}

	/**
	 * Represents the type of a subpart of the song
	 */
	public enum SongPartType implements Serializable {
		INTRO("Intro"), VERSE("Couplet"), CHORUS("Refrain");
		
		private String label;
		
		SongPartType(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}

	public int getTempo() {
		return this.tempo;
	}

	public List<SongPart> getSongParts() {
		return parts;
	}

	public int getTonality() {
		return tonality;
	}

	public void setTonality(int tonality) {
		this.tonality = tonality;
	}

	public int[] getScale() {
		return scale;
	}

	public void setScale(int[] scale) {
		this.scale = scale;
	}

	public void clearSongParts() {
		parts.clear();
	}

}
