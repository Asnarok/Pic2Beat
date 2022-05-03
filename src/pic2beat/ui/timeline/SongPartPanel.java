package pic2beat.ui.timeline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

import javax.swing.JPanel;

import pic2beat.song.SongPart;

public class SongPartPanel extends JPanel {

	public static final HashMap<String, Class> songPartPanels = new HashMap<>();

	private boolean focused = false;
	private String label;
	private Color background;
	private Color selectedColor;
	private Color hoverColor;
	private SongPart.SongPartType structType;

	public SongPartPanel(String label, String HTMLColorCode, SongPart.SongPartType structType) {
		this.label = label;
		this.background = new Color(Integer.parseInt(HTMLColorCode, 16));
		this.structType = structType;

		float[] hsv = new float[3];
		Color.RGBtoHSB(background.getRed(), background.getGreen(), background.getBlue(), hsv);
		hsv[2] = (float) Math.max(0, hsv[2] - 0.1);
		selectedColor = Color.getHSBColor(hsv[0], hsv[1], hsv[2]);
		hsv[2] = (float) Math.min(1, hsv[2] + 0.2);
		hsv[1] = (float) Math.min(1, hsv[1] - 0.1);
		hoverColor = Color.getHSBColor(hsv[0], hsv[1], hsv[2]);

		toFillWith = background;

		initPanels();
	}

	public void initPanels() {
		songPartPanels.put("Intro", IntroPanel.class);
		songPartPanels.put("Verse", VersePanel.class);
		songPartPanels.put("Chorus", ChorusPanel.class);
	}

	private Font drawFont = new Font("Arial", 15, 15);

	private Color borderColor = new Color(170, 170, 170);
	private int lineWidth = 2;

	private Color toFillWith;

	@Override
	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(toFillWith);
		g.fillRoundRect(lineWidth / 2, lineWidth / 2, this.getWidth() - lineWidth, this.getHeight() - lineWidth, 5, 5);
		g.setColor(Color.black);
		g.setFont(drawFont);
		FontMetrics fm = g.getFontMetrics();
		int yDraw = (int) (this.getHeight() / 2);
		int xDraw = (int) (this.getWidth() / 2 - fm.stringWidth(label) / 2.0);
		AffineTransform t = g.getTransform();
		if (fm.stringWidth(label) + 20 >= this.getWidth()) {
			g.rotate(-Math.PI / 2, this.getWidth() / 2, this.getHeight() / 2);
			yDraw = (int) (this.getHeight() / 2 + fm.getHeight() / 4);
			xDraw = (int) (this.getWidth() / 2 - fm.stringWidth(label) / 2.0);
		}
		g.drawString(label, xDraw, yDraw);
		g.setTransform(t);
		g.setColor(borderColor);
		g.setStroke(new BasicStroke(lineWidth));
		g.drawRoundRect(lineWidth / 2, lineWidth / 2, this.getWidth() - lineWidth, this.getHeight() - lineWidth, 5, 5);
	}

	public void hover() {
		if (!focused) {
			toFillWith = hoverColor;
			System.out.println("oddd");
		}
			
	}

	public void unHover() {
		if (!focused)
			toFillWith = background;
	}

	public void focus() {
		focused = true;
		toFillWith = selectedColor;
	}

	public void unfocus() {
		focused = false;
		toFillWith = background;
	}
	
	public SongPart.SongPartType getStructType() {
		return structType;
	}

}
