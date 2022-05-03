package pic2beat.ui.scoredisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import pic2beat.song.SongPart;

public class NotePanel extends JPanel implements JMC {

	private SongPart part;

	public static final int BAR_WIDTH = 200; // largueur par défaut d'une mesure affichée
	public static final int NOTE_HEIGHT = 15;
	public static int currentHeight = 0;
	public static final double CROTCHET_WIDTH = BAR_WIDTH / 4.0;

	public NotePanel() {
	}

	public void updateScale() {
	}

	private int low, high, height;

	public void show(SongPart part) {
		this.part = part;

		high = part.getHighestPitch();
		low = part.getLowestPitch();

		int bars = (int) (part.getEndTime() / 4)+1;
		int width = (BAR_WIDTH * bars);
		height = NOTE_HEIGHT * (high - low + 1);
		
		System.out.println(part.getEndTime());

		this.setPreferredSize(new Dimension(width, height));
		this.repaint();

	}

	private static final Color noteColor = Color.orange;
	private static final Color backgroundColor = new Color(180, 180, 180);
	private static final Color gridColor = new Color(160, 160, 160);
	private static final Color[] toChange = { noteColor, new Color(63,168,50), new Color(10, 150, 0),
			new Color(228,132,18), new Color(209,0,0)};

	public void paintComponent(Graphics g) {
		currentHeight = this.getHeight();
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		double currentX = 0;
		g.setColor(Color.black);
		for (int y = 0; y < height; y += NOTE_HEIGHT) {

			g.drawLine(0, y, this.getWidth(), y);
		}

		g.setColor(gridColor);
		for (double x = 0; x < this.getWidth(); x += CROTCHET_WIDTH) {
			g.drawLine((int) x, 0, (int) x, this.getHeight());
		}

		if (part != null) {
			int i = 0;
			for (Object o : part.getPhrases().values()) {
				if (o instanceof Phrase) {
					currentX = 0;
					Phrase p = (Phrase) o;
					for (Note n : p.getNoteArray()) {
						g.setColor(toChange[i]);
						double width = CROTCHET_WIDTH*n.getRhythmValue();
						int y = this.getHeight() - (n.getPitch() - low + 1) * NOTE_HEIGHT;
						g.fillRect((int)currentX, y, (int)width, NOTE_HEIGHT);
						g.drawString(i+"", (int)currentX, (int)y+50);
						g.setColor(new Color(100, 100, 100));
						g.drawRect((int)currentX, y, (int)width, NOTE_HEIGHT);
						currentX += width;
					}
					i++;
				}
			}
		}
	}

}
