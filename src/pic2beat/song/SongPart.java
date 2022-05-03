package pic2beat.song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import pic2beat.harmonia.Chord;
import pic2beat.melodia.MelodIA;
import pic2beat.song.Song.SongPartType;

public class SongPart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8070701508088677417L;
	private final Song song;
	private final HashMap<Part, Object> phrases;

	private final List<Chord> chords;

	private SongPartType structType;
	private int length = 4;

	public SongPart(final Song song, SongPartType structType) {
		this.song = song;
		this.phrases = new HashMap<>();
		this.chords = new ArrayList<>();
		this.structType = structType;
	}

	public void generate(SongGenerator generator) {
		Part p = song.getChords();
		if (p != null) {
			final List<Chord> chords = generator.generateChords();
			this.chords.clear();
			this.chords.addAll(chords);
			final CPhrase cp = new CPhrase();
			for (Chord c : chords) {
				cp.addChord(c.getNotes(), c.length);
			}
			phrases.put(p, cp);

			p = song.getLead();
			if (p != null) {
				final Phrase lead = new Phrase();
				for (Chord c : chords) {
					lead.addNoteList(MelodIA.get().phrase(c.getNotes(), c.length).getNoteArray());
				}
				phrases.put(p, lead);
			}
		}

		p = song.getBass();
		if (p != null) {

			phrases.put(p, generator.generateBass(this.chords));
		}

		p = song.getDrums();
		if (p != null) {
			phrases.put(p, generator.generateDrums());

		}

		for (Map.Entry<Part, InstrumentRole> entry : song.getInstrumentsWithRole().entrySet()) {
			phrases.put(entry.getKey(), generator.generateInstrument(entry.getValue(), this.chords));
		}
	}

	public HashMap<Part, Object> getPhrases() {
		return phrases;
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public List<Chord> getChords() {
		return chords;
	}

}
