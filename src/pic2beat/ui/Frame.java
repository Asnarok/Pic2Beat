package pic2beat.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jm.music.data.Part;
import pic2beat.song.SongPart;
import pic2beat.ui.scoredisplay.NoteScrollPane;
import pic2beat.ui.timeline.ChorusPanel;
import pic2beat.ui.timeline.IntroPanel;
import pic2beat.ui.timeline.SongPartPanel;
import pic2beat.ui.timeline.VersePanel;
import pic2beat.ui.scoredisplay.NoteLabelPanel;
import pic2beat.ui.scoredisplay.ScorePane;

public class Frame extends JFrame {

	public static final int IDLE = 0, INTRO = 1, VERSE = 2, CHORUS = 3;
	public static int adding = IDLE;

	private JPanel contentPane;
	private JTextField bpmTextField;

	public static Component addingPanel;
	public static int addingIndex = 0;
	public static int partCount = 0;

	public static SongPartPanel lastHovered;
	public static SongPartPanel lastFocused;

	public static List<SongPart> songPattern = new LinkedList<>();

	public ScorePane scorePane;
	
	/**
	 * Create the frame.
	 */
	public Frame() {

		ButtonGroup toolbarGroup = new ButtonGroup();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 986, 634);
		this.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("Fichier");
		menuBar.add(fileMenu);

		JMenuItem newMenu = new JMenuItem("Nouveau...");
		newMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newProject();
			}
		});
		newMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(newMenu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		contentPane.add(panel_6, BorderLayout.WEST);
		panel_6.setLayout(new BorderLayout(0, 0));

		Box verticalBox = Box.createVerticalBox();
		panel_6.add(verticalBox);
		verticalBox.setPreferredSize(new Dimension(250, 0));
		verticalBox.setMaximumSize(new Dimension(250, 0));

		JPanel panel = new JPanel();
		verticalBox.add(panel);
		panel.setMaximumSize(new Dimension(245, 1000));
		panel.setPreferredSize(new Dimension(245, 303));
		panel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 3, true),
				"Param\u00E8tres g\u00E9n\u00E9raux", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel.setLayout(new GridLayout(3, 1, 0, 10));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

		JLabel lblNewLabel = new JLabel("Tempo : ");
		panel_2.add(lblNewLabel);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		bpmTextField = new JTextField();
		bpmTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		bpmTextField.setPreferredSize(new Dimension(50, 20));
		bpmTextField.setMaximumSize(new Dimension(50, 50));
		bpmTextField.setText("120");
		panel_2.add(bpmTextField);
		bpmTextField.setColumns(3);

		JLabel bpm = new JLabel("bpm");
		panel_2.add(bpm);

		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bpmTextField.setText(slider.getValue() + "");
			}
		});
		panel_2.add(slider);
		slider.setOpaque(false);
		slider.setBackground(new Color(220, 220, 220));
		slider.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		slider.setValue(120);
		slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(60);
		slider.setMaximum(200);
		slider.setMinimum(20);
		slider.setToolTipText("");
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblTonalit = new JLabel("Tonalit\u00E9 :");
		panel_3.add(lblTonalit);
		lblTonalit.setOpaque(true);
		lblTonalit.setHorizontalAlignment(SwingConstants.CENTER);

		JComboBox<String> comboBox = new JComboBox<>();
		panel_3.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" }));

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);

		JPanel panel_5 = new JPanel();
		verticalBox.add(panel_5);
		panel_5.setPreferredSize(new Dimension(10, 60));
		panel_5.setMinimumSize(new Dimension(10, 44));
		panel_5.setMaximumSize(new Dimension(32767, 44));
		panel_5.setLayout(null);

		JButton generate = new JButton("G\u00E9n\u00E9rer");
		generate.setBounds(30, 0, 194, 36);
		panel_5.add(generate);

		JSeparator separator_1 = new JSeparator();
		verticalBox.add(separator_1);
		separator_1.setMaximumSize(new Dimension(32767, 2));

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);

		JPanel panel_4 = new JPanel();
		verticalBox.add(panel_4);
		panel_4.setPreferredSize(new Dimension(10, 80));
		panel_4.setMinimumSize(new Dimension(10, 80));
		panel_4.setMaximumSize(new Dimension(32767, 80));
		panel_4.setLayout(null);

		JButton exportMidi = new JButton("Exporter MIDI...");
		exportMidi.setBounds(29, 0, 196, 35);
		panel_4.add(exportMidi);
		exportMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JButton btnAfficherPartition = new JButton("Afficher partition");
		btnAfficherPartition.setBounds(29, 45, 196, 35);
		panel_4.add(btnAfficherPartition);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_2);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setMinimumSize(new Dimension(2, 0));
		panel_6.add(separator, BorderLayout.EAST);

		JPanel panel_7 = new JPanel();
		contentPane.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 3, true), "Time line",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_7.add(panel_1, BorderLayout.SOUTH);
		panel_1.setMaximumSize(new Dimension(32767, 214));
		panel_1.setPreferredSize(new Dimension(588, 180));
		panel_1.setMinimumSize(new Dimension(588, 214));
		panel_1.setLayout(new BorderLayout(0, 0));
		
		
		
		JPanel timeLinePanel = new JPanel();

		JButton deleteButton = new JButton("");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeLinePanel.remove(lastFocused);
				lastFocused = null;
				deleteButton.setEnabled(false);
				timeLinePanel.revalidate();
				timeLinePanel.repaint();
				partCount--;
			}
		});
		
		timeLinePanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (adding != IDLE) {
					int oldIndex = addingIndex;
					addingIndex = (int) (e.getX() / (timeLinePanel.getWidth() / (double) partCount));
					if (addingIndex != oldIndex) {
						timeLinePanel.remove(oldIndex);
						timeLinePanel.add(addingPanel, addingIndex);

						timeLinePanel.revalidate();
						addingPanel.repaint();
					}
				} else {
					if (partCount > 0) {
						int hoverIndex = (int) (e.getX() / (timeLinePanel.getWidth() / (double) partCount));
						Component c = timeLinePanel.getComponent(hoverIndex);
						if (c instanceof SongPartPanel) {
							SongPartPanel p = (SongPartPanel) c;
							if (p != lastHovered) {
								p.hover();
								p.repaint();
								if (lastHovered != null) {
									lastHovered.unHover();
									lastHovered.repaint();
								}
								lastHovered = p;
							}
						}
					}
				}

			}
		});
		timeLinePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (adding != IDLE) {
					if (adding == INTRO) {
						addingPanel = new IntroPanel();
					} else if (adding == VERSE) {
						addingPanel = new VersePanel();
					} else if (adding == CHORUS) {
						addingPanel = new ChorusPanel();
					}
					partCount++;
					addingIndex = (int) (e.getX() / (timeLinePanel.getWidth() / (double) partCount));
					timeLinePanel.add(addingPanel, addingIndex);
					timeLinePanel.revalidate();
					addingPanel.repaint();
				} else {
					if (partCount > 0) {
						int focusIndex = (int) (e.getX() / (timeLinePanel.getWidth() / (double) partCount));
						Component c = timeLinePanel.getComponent(focusIndex);
						if (c instanceof SongPartPanel) {
							SongPartPanel p = (SongPartPanel) c;
							deleteButton.setEnabled(true);
							if (p != lastFocused) {
								p.focus();
								p.repaint();
								if (lastFocused != null) {
									lastFocused.unfocus();
									lastFocused.repaint();
								}

								lastFocused = p;
							}
						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (adding == INTRO) {
					addingPanel = new IntroPanel();
				} else if (adding == VERSE) {
					addingPanel = new VersePanel();
				} else if (adding == CHORUS) {
					addingPanel = new ChorusPanel();
				}
				if (adding != IDLE) {
					partCount++;
					addingIndex = (int) (e.getX() / (timeLinePanel.getWidth() / (double) partCount));
					timeLinePanel.add(addingPanel, addingIndex);
					timeLinePanel.revalidate();
					addingPanel.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (addingPanel != null) {
					timeLinePanel.remove(addingPanel);
					addingPanel = null;
					timeLinePanel.revalidate();
					timeLinePanel.repaint();
					partCount--;
				}
			}
		});
		timeLinePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.add(timeLinePanel);
		timeLinePanel.setLayout(new GridLayout(1, 0, 5, 0));

		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_8.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_8, BorderLayout.NORTH);

		JToolBar toolBar = new JToolBar();
		panel_8.add(toolBar);

		JToggleButton introButton = new JToggleButton("Intro");
		introButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					adding = INTRO;
			}
		});

		JToggleButton selectionMode = new JToggleButton("");
		selectionMode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					adding = IDLE;
			}
		});
		selectionMode.setMargin(new Insets(0, 0, 0, 0));
		selectionMode.setIcon(new ImageIcon(Frame.class.getResource("/pic2beat/res/img/cursor.png")));
		toolBar.add(selectionMode);
		
		deleteButton.setMargin(new Insets(0, 0, 0, 0));
		deleteButton.setIcon(new ImageIcon(Frame.class.getResource("/pic2beat/res/img/delete.png")));
		toolBar.add(deleteButton);
		introButton.setActionCommand("");
		toolBar.add(introButton);

		JToggleButton verseButton = new JToggleButton("Couplet");
		verseButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					adding = VERSE;
			}
		});
		toolBar.add(verseButton);

		JToggleButton chorusButton = new JToggleButton("Refrain");
		chorusButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					adding = CHORUS;
			}
		});
		toolBar.add(chorusButton);
		btnAfficherPartition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		toolbarGroup.add(chorusButton);
		toolbarGroup.add(introButton);
		toolbarGroup.add(verseButton);
		toolbarGroup.add(selectionMode);
		
		JPanel panel_9 = new JPanel();
		panel_7.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		scorePane = new ScorePane();
		panel_9.add(scorePane, BorderLayout.CENTER);

	}

	public void newProject() {

	}
}
