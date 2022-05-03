package pic2beat.ui.scoredisplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import pic2beat.harmonia.Chord;

public class ChordsPanel extends JPanel {

	private List<Chord> chords;
	private HashMap<String, Color> chordsColors;

	public ChordsPanel() {
		chords = new ArrayList<>();
		chordsColors = new HashMap<>();
	}

	public void show(List<Chord> chords) {
		this.chords = chords;
		chordsColors = new HashMap<>();
		for (int i = 0; i < chords.size(); i++) {
			if (!chordsColors.containsKey(chords.get(i).name)) {
				float h = (float) (Math.random());
				float s = (float) (Math.random() * 0.5 + 0.2);
				float b = (float) (Math.random() * 0.2 + 0.7);
				Color c = Color.getHSBColor(h, s, b);
				chordsColors.put(chords.get(i).name, c);
			}
		}
		this.repaint();
	}

	private static final Font noteFont = new Font("Arial", 12, 12);

	private int scrollPx;

	public void scroll(int px) {
		this.scrollPx = px;
		this.repaint();
	}

	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setFont(noteFont);
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		FontMetrics fm = g.getFontMetrics();

		int xOffset = -scrollPx+30;
		double x = xOffset;
		int i = 0;
		while (x < this.getWidth() && i < chords.size()) {
			Chord c = chords.get(i);

			double width = NotePanel.CROTCHET_WIDTH * c.length;
			if (x >= -width) {
				g.setColor(chordsColors.get(c.name));
				g.fillRect((int) x, 0, (int) width, this.getHeight());
				g.setColor(Color.black);
				String toDraw = c.name;
				g.drawString(toDraw, (int) (x + width / 2 - fm.stringWidth(toDraw) / 2), this.getHeight() / 2);
			}
			x += width;
			i++;
		}
	}

}
