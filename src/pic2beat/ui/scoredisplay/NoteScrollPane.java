package pic2beat.ui.scoredisplay;

import javax.swing.JScrollPane;

import jm.music.data.Part;
import pic2beat.song.SongPart;

@SuppressWarnings("serial")
public class NoteScrollPane extends JScrollPane{

	private NotePanel panel;
	
	public NoteScrollPane() {
		panel = new NotePanel();
		this.setViewportView(panel);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	}
	
	public void show(SongPart p) {
		panel.show(p);
		this.revalidate();
	}


}
