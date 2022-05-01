package pic2beat.ui.scoredisplay;

import javax.swing.JScrollPane;

import jm.music.data.Part;

@SuppressWarnings("serial")
public class NoteScrollPane extends JScrollPane{

	private Part score;
	private NotePanel panel;
	
	public NoteScrollPane() {
		this.score = new Part();
		panel = new NotePanel();
		this.setViewportView(panel);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	}
	
	public void show(Part p) {
		panel.show(p);
		this.revalidate();
	}


}
