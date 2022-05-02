package pic2beat.song;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class Song {

	private String title;

	private final List<SongPart> parts;
	private final HashMap<Part, InstrumentRole> instruments;

	public static final int INTRO = 0, VERSE = 1, CHORUS = 2;

	private List<SongPartType> structure;

	private SongPart chorus;

	public Song(String title) {
		this.title = title;

		this.parts = new ArrayList<>();
		this.instruments = new HashMap<>();
		this.structure = new ArrayList<SongPartType>();
	}

	/**
	 * Self generation
	 * 
	 * @param genClazz
	 */
	public void generate(Class<? extends SongGenerator> genClazz) {
		SongGenerator generator;
		try {
			generator = genClazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
			return;
		}

		/*
		 * // INTRO SongPart sp = new SongPart(this); sp.generate(generator);
		 * parts.add(sp);
		 * 
		 * // CHORUS final SongPart chorus = new SongPart(this);
		 * chorus.generate(generator); parts.add(chorus);
		 * 
		 * // VERSE sp = new SongPart(this); sp.generate(generator); parts.add(sp);
		 * 
		 * // CHORUS sp = new SongPart(this); sp.generate(generator); parts.add(chorus);
		 */

		for (SongPartType t : structure) {
			if (t == SongPartType.CHORUS) {
				if (chorus == null) {
					SongPart sp = new SongPart(this, t);
					sp.generate(generator);
					parts.add(sp);
					chorus = sp;
				}else {
					parts.add(chorus);
				}
			}else {
				SongPart sp = new SongPart(this, t);
				sp.generate(generator);
				parts.add(sp);
				
			}
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

	public Song addInstrument(String name, InstrumentRole role, int instrument) {
		if (name.equals("Drums") || name.equals("Bass") || name.equals("Chords") || name.equals("Lead"))
			throw new IllegalArgumentException("Instrument name cannot be " + name + ".");

		final Part toAdd = new Part(name, instrument, instCount + 3);

		instruments.remove(getPartWithName(name));
		instruments.put(toAdd, role);

		instCount++;
		return this;
	}

	public void removeInstrument(String name) {
		instruments.remove(getPartWithName(name));
		instCount--;
	}

	public Map<Part, InstrumentRole> getInstrumentsWithRole() {
		return instruments.entrySet().stream()
				.filter(e -> !(e.getKey().getTitle().equals("Lead") || e.getKey().getTitle().equals("Chords")
						|| e.getKey().getTitle().equals("Drums") || e.getKey().getTitle().equals("Bass")))
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()));
	}

	public Score toScore() {
		final Score score = new Score(this.title);

		for (Part p : instruments.keySet()) {
			score.addPart(p);
		}

		for (SongPart sp : parts) {
			// System.out.println(sp);
			for (Map.Entry<Part, Object> entry : sp.getPhrases().entrySet()) {
				// System.out.println(entry);
				if (entry.getValue() instanceof Phrase)
					entry.getKey().appendPhrase((Phrase) entry.getValue());
				else if (entry.getValue() instanceof CPhrase) {
					CPhrase cp = ((CPhrase) entry.getValue()).copy();
					cp.setAppend(true);
					entry.getKey().addCPhrase(cp);
				}
			}
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

	public void addToStruct(SongPartType part) {
		this.structure.add(part);
	}

	public void setStruct(List<SongPartType> struct) {
		this.structure = struct;
	}

	public void removeFromStruct(int index) {
		this.structure.remove(index);
	}

	public enum SongPartType {
		INTRO, VERSE, CHORUS
	}
	
	public List<SongPart> getSongParts() {
		return parts;
	}

}
