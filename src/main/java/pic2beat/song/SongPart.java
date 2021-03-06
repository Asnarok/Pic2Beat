package pic2beat.song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import pic2beat.Main;
import pic2beat.harmonia.Chord;
import pic2beat.harmonia.ChordRhythm;
import pic2beat.melodia.MelodIA;
import pic2beat.song.Song.SongPartType;

/**
 * Represents a subpart of the song
 */
public class SongPart implements Serializable {

	private static final long serialVersionUID = 8070701508088677417L;
	private final Song song;
	private final HashMap<Part, Object> phrases;

	private final List<Chord> chords;
	
	private final SongPartType structType;
	private int length;

	public SongPart(final Song song, SongPartType structType, int length) {
		this.song = song;
		this.phrases = new HashMap<>();
		this.chords = new ArrayList<>();
		this.structType = structType;
		this.length = length;
	}

	/**
	 * Generates phrases for each instrument that last the entire subpart
	 * @param generator the generator to use for the generation
	 */
	public void generate(SongGenerator generator) {
		if(Main.DEBUG) {
			System.out.println("\t- Generating SongPart of type " + structType + "...");
		}

		generator.setSongPart(this);
		
		Part p = song.getChords();
		generator.setSongPart(this);
		if (p != null) {
			final List<Chord> chords = generator.generateChords();
			this.chords.clear();
			this.chords.addAll(chords);
			final CPhrase cp = new CPhrase();

			for (Chord c : chords) {
				ChordRhythm cr = ChordRhythm.randomRhythm();
				for (Double duration : cr.getDurations()) {
					cp.addChord(c.getNotes(), duration);
				}
			}
			phrases.put(p, cp);

			p = song.getLead();
			if (p != null) { 
				final Phrase lead = new Phrase();

				for (Chord c : chords) {
					if(structType != SongPartType.INTRO) {
						if(Main.DEBUG) {
							System.out.println("\t\t- Generating lead...");
						}

						lead.addNoteList(MelodIA.get().phrase(song.getTonality(), song.getScale(), c.getNotes(), c.length).getNoteArray());
					} else {
						lead.addNote(new Note(Note.REST, c.length));
					}

				}
				phrases.put(p, lead);
			}
		}

		p = song.getBass();
		if (p != null) {
			final Phrase bass = generator.generateBass();
			phrases.put(p, bass);
		}

		p = song.getDrums();
		if (p != null) {
			final Phrase drums = generator.generateDrums();
			phrases.put(p, drums);

		}

		for (Map.Entry<Part, InstrumentRole> entry : song.getInstrumentsWithRole().entrySet()) {
			final Phrase instru =  generator.generateInstrument(entry.getValue());
			phrases.put(entry.getKey(), instru);
		}

		if(Main.DEBUG) {
			System.out.println("\t- SongPart generated.");
		}
	}

	public HashMap<Part, Object> getPhrases() {
		return phrases;
	}

	public int getLength() {
		return length;
	}

	public int getHighestPitch() {
		int max = 0;
		for (Object o : phrases.values()) {
			if (o instanceof Phrase) {
				Phrase p = (Phrase) o;
				if (p.getHighestPitch() > max)
					max = p.getHighestPitch();
			}
		}

		return max;
	}

	public int getLowestPitch() {
		int min = 100000;
		for (Object o : phrases.values()) {
			if (o instanceof Phrase) {
				Phrase p = (Phrase) o;
				if (p.getLowestPitch() < min)
					min = p.getLowestPitch();
			}
		}

		return min;
	}

	public double getEndTime() {
		double max = 0;
		for (Object o : phrases.values()) {
			if (o instanceof Phrase) {
				Phrase p = (Phrase) o;
				if (p.getEndTime() > max)
					max = p.getEndTime();
			}
		}

		return max;
	}
	public void setLength (int length) {
		this.length=length;
	}
	
	public List<Chord> getChords() {
		return this.chords;
	}

	public Song getSong() {
		return song;
	}
}
