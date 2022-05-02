package pic2beat.ui.scoredisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

public class NotePanel extends JPanel implements JMC {

	private Part part;
	
	private double scale = 1.0;
	
	public static final int BAR_WIDTH = 200; //largueur par défaut d'une mesure affichée
	public static final int NOTE_HEIGHT = 15;
	public static int currentHeight = 0;

	public NotePanel() {
		show(new Part());
	}
	

	public void updateScale() {
	}
	
	private int low, high, height;
	
	public void show(Part part) {
		this.part = part;
		
		high = part.getHighestPitch();
		low = part.getLowestPitch();

		int bars = (int) (part.getEndTime()/4);
		int width = (BAR_WIDTH*bars);
		height = NOTE_HEIGHT * (high-low);
		
		this.setPreferredSize(new Dimension(width, height));
		
		this.repaint();
		
	}
	
	private static final Color noteColor = Color.orange;
	private static final Color backgroundColor = new Color(170, 170, 170);
	
	
	
	public void paintComponent(Graphics g) {
		currentHeight = this.getHeight();
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		int currentX = 0;
		g.setColor(Color.black);
		for(int y = 0; y < height; y+=NOTE_HEIGHT) {
			
			g.drawLine(0, y, this.getWidth(), y);
		}
		
		for(Phrase p : part.getPhraseArray()) {
			for(Note n : p.getNoteArray()) {
				g.setColor(noteColor);
				int width = (int) (BAR_WIDTH/(n.getDuration()*4));
				int y = this.getHeight()-(n.getPitch()-low+1)*NOTE_HEIGHT;
				g.fillRect(currentX, y, width, NOTE_HEIGHT);
				g.setColor(new Color(190, 190, 190));
				g.drawRect(currentX, y, width, NOTE_HEIGHT);
				currentX+=width;
			}
		}
	}

}
