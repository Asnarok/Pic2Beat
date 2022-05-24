package pic2beat.ui.scoredisplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class NoteLabelPanel extends JPanel {

	
	public NoteLabelPanel() {
	}
	
	
	private int lowPitch, highPitch;
	
	public void setup(int lowPitch, int highPitch) {
		this.lowPitch = lowPitch;
		this.highPitch = highPitch;
	}
	
	private int scrollPx;
	
	public void scroll(int scrollPx) {
		this.scrollPx = scrollPx;
		this.repaint();
	}

	private static final String[] NOTES = {"A", "A", "B", "C", "C", "D", "D", "E", "F", "F", "G", "G"};
	private static final String[] SUFFIXES = {"", "#", "", "", "#", "", "#", "", "", "#", "", "#"};
	private static final Font noteFont = new Font("Arial", 12, 12);
	private static final Font suffixeFont = new Font("Arial", 7, 7);
	
	public void paintComponent(Graphics gr) {
		
		Graphics2D g = (Graphics2D) gr; 
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int yOffset = scrollPx%NotePanel.NOTE_HEIGHT;
		
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.black);
		
		for(int y = this.getHeight()+yOffset-NotePanel.NOTE_HEIGHT; y > -NotePanel.NOTE_HEIGHT; y-=NotePanel.NOTE_HEIGHT) {

			g.setFont(noteFont);

			FontMetrics fm = g.getFontMetrics();
			//int currentNoteIndex = (NotePanel.currentHeight+scrollPx-y/NotePanel.NOTE_HEIGHT+1)%12+lowPitch;
			
			int currentNoteIndex = ((this.getHeight()-y+scrollPx)/NotePanel.NOTE_HEIGHT+lowPitch+1)%12;
			if(currentNoteIndex < 0)currentNoteIndex+=12;
			
			int octave = ((this.getHeight()+scrollPx-y)/(NotePanel.NOTE_HEIGHT)+lowPitch+1)/12-1;
			g.drawLine(0, y-2, this.getWidth(), y-2);
			String toDraw = NOTES[currentNoteIndex];
			//g.drawString(toDraw, this.getWidth()/2-fm.stringWidth(toDraw)/2, y+fm.getHeight()/2);
			g.drawString(toDraw,  5, y+fm.getHeight()/2+2);
			g.setFont(suffixeFont);
			fm = g.getFontMetrics();
			int noteXOffset = fm.stringWidth(toDraw)+5;
			g.drawString(SUFFIXES[currentNoteIndex], noteXOffset+4, y+5);
			g.drawString(octave+"", noteXOffset+4, y+12);
		}
		
	}

}
