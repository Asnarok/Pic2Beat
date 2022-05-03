package pic2beat.ui.scoredisplay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;

import pic2beat.song.SongPart;

public class ScorePane extends JPanel implements AdjustmentListener {

	/**
	 * Create the panel.
	 */

	private NoteLabelPanel noteLabelPanel;
	private NoteScrollPane noteScrollPane;
	private ChordsPanel chordsPanel;

	public ScorePane() {
		setLayout(new BorderLayout(0, 0));
		noteLabelPanel = new NoteLabelPanel();
		noteLabelPanel.setPreferredSize(new Dimension(30, 10));
		add(noteLabelPanel, BorderLayout.WEST);

		noteScrollPane = new NoteScrollPane();
		add(noteScrollPane, BorderLayout.CENTER);

		noteScrollPane.getVerticalScrollBar().addAdjustmentListener(this);
		noteScrollPane.getHorizontalScrollBar().addAdjustmentListener(this);

		chordsPanel = new ChordsPanel();
		chordsPanel.setPreferredSize(new Dimension(0, 30));
		add(chordsPanel, BorderLayout.NORTH);
		noteScrollPane.getVerticalScrollBar().setUnitIncrement(NotePanel.NOTE_HEIGHT);
		;

	}

	public void show(SongPart p) {
		noteLabelPanel.setup(p.getLowestPitch(), p.getHighestPitch());
		noteScrollPane.show(p);
		chordsPanel.show(p.getChords());
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource() == noteScrollPane.getVerticalScrollBar()) {
			int bottomPos = (int) (noteScrollPane.getViewport().getComponent(0).getHeight()
					- (noteScrollPane.getViewport().getViewPosition().getY()
							+ noteScrollPane.getViewportBorderBounds().getHeight()));

			noteLabelPanel.scroll(bottomPos);
		}else if(e.getSource() == noteScrollPane.getHorizontalScrollBar()) {
			chordsPanel.scroll((int)noteScrollPane.getViewport().getViewPosition().getX());
		}
		// System.out.println(noteScrollPane.getViewport().getViewPosition().getY());
	}

}
