package pic2beat.ui.scoredisplay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;

import jm.music.data.CPhrase;
import jm.music.data.Part;

public class ScorePane extends JPanel implements AdjustmentListener{

	/**
	 * Create the panel.
	 */

	private NoteLabelPanel noteLabelPanel;
	private NoteScrollPane noteScrollPane;
	
	public ScorePane() {
		setLayout(new BorderLayout(0, 0));
		noteLabelPanel = new NoteLabelPanel();
		noteLabelPanel.setPreferredSize(new Dimension(30, 10));
		add(noteLabelPanel, BorderLayout.WEST);
		
		noteScrollPane = new NoteScrollPane();
		add(noteScrollPane, BorderLayout.CENTER);
		
		noteScrollPane.getVerticalScrollBar().addAdjustmentListener(this);
		noteScrollPane.getVerticalScrollBar().setUnitIncrement(NotePanel.NOTE_HEIGHT);;
		

	}

	public void show(Part p) {
		noteScrollPane.show(p);
		noteLabelPanel.setup(p.getLowestPitch(), p.getHighestPitch());
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		int bottomPos = (int) (noteScrollPane.getViewport().getComponent(0).getHeight()-(noteScrollPane.getViewport().getViewPosition().getY()+noteScrollPane.getViewportBorderBounds().getHeight()));

		noteLabelPanel.scroll(bottomPos);
		//System.out.println(noteScrollPane.getViewport().getViewPosition().getY());
	}

}
