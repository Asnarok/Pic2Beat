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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pic2beat.song.generators.BasicGenerator;
import pic2beat.Main;
import pic2beat.song.SongPart;
import pic2beat.ui.scoredisplay.ScorePane;
import pic2beat.ui.timeline.ChorusPanel;
import pic2beat.ui.timeline.IntroPanel;
import pic2beat.ui.timeline.SongPartPanel;
import pic2beat.ui.timeline.VersePanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ComposerFrame extends JFrame {

	public static final int IDLE = 0, INTRO = 1, VERSE = 2, CHORUS = 3;
	public static int adding = IDLE;

	private JPanel contentPane;
	private JTextField bpmTextField;
	private JPanel timeLinePanel;

	public static Component addingPanel;
	public static int addingIndex = 0;
	public static int partCount = 0;

	public static SongPartPanel lastHovered;
	public static SongPartPanel lastFocused;

	public static List<SongPart> songPattern = new LinkedList<>();

	public static List<String> bassInstruments;
	public static List<String> compingInstruments;
	public static List<String> drumInstruments;
	public static List<String> leadInstruments;
	public static boolean initialized = false;

	public static void initInstruments() {
		bassInstruments = loadJson("bass_instruments.json");
		compingInstruments = loadJson("comping_instruments.json");
		drumInstruments = loadJson("drum_instruments.json");
		leadInstruments = loadJson("lead_instruments.json");
	}

	private static ArrayList<String> loadJson(String fileName) {
		Gson gson = new Gson();
		String loaded = "";
		try {
			loaded = Files.readString(Path.of(fileName));

		} catch (IOException e) {
			e.printStackTrace();
		}

		java.lang.reflect.Type stringType = new TypeToken<ArrayList<String>>() {
		}.getType();

		ArrayList<String> instruments = gson.fromJson(loaded, stringType);

		return instruments;
	}

	public ScorePane scorePane;
	private JTextField titleTextField;

	/**
	 * Create the frame.
	 */
	public ComposerFrame() {
		if (!initialized)
			initInstruments();

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

		JMenuItem saveMenuItem = new JMenuItem("Enregistrer");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(saveMenuItem);

		JMenuItem exportMenuItem = new JMenuItem("Exporter...");
		exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(exportMenuItem);

		JMenu projectMenu = new JMenu("Projet");
		menuBar.add(projectMenu);

		JMenuItem showScoreMenuItem = new JMenuItem("Afficher la partition");
		projectMenu.add(showScoreMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel leftPanel = new JPanel();
		contentPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));

		Box verticalBox = Box.createVerticalBox();
		leftPanel.add(verticalBox);
		verticalBox.setPreferredSize(new Dimension(250, 0));
		verticalBox.setMaximumSize(new Dimension(250, 0));

		JPanel settingsPanel = new JPanel();
		verticalBox.add(settingsPanel);
		settingsPanel.setMaximumSize(new Dimension(245, 1000));
		settingsPanel.setPreferredSize(new Dimension(245, 303));
		settingsPanel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 3, true),
				"Param\u00E8tres g\u00E9n\u00E9raux", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		settingsPanel.setLayout(new BorderLayout(0, 0));

		JPanel northSettingsPanel = new JPanel();
		northSettingsPanel.setPreferredSize(new Dimension(160, 160));
		settingsPanel.add(northSettingsPanel, BorderLayout.NORTH);
		northSettingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		JLabel titleLabel = new JLabel("Titre : ");
		northSettingsPanel.add(titleLabel);

		titleTextField = new JTextField();
		northSettingsPanel.add(titleTextField);
		titleTextField.setColumns(16);

		JLabel lblNewLabel = new JLabel("Tempo : ");
		northSettingsPanel.add(lblNewLabel);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		bpmTextField = new JTextField();
		bpmTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		bpmTextField.setPreferredSize(new Dimension(50, 20));
		bpmTextField.setMaximumSize(new Dimension(50, 50));
		bpmTextField.setText("120");
		northSettingsPanel.add(bpmTextField);
		bpmTextField.setColumns(3);

		JLabel bpm = new JLabel("bpm");
		northSettingsPanel.add(bpm);

		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bpmTextField.setText(slider.getValue() + "");
			}
		});
		northSettingsPanel.add(slider);
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

		JLabel lblTonalit = new JLabel("Tonalit\u00E9 :");
		northSettingsPanel.add(lblTonalit);
		lblTonalit.setOpaque(true);
		lblTonalit.setHorizontalAlignment(SwingConstants.CENTER);

		JComboBox<String> comboBox = new JComboBox<>();
		northSettingsPanel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" }));

		JScrollPane instrumentsPane = new JScrollPane();
		instrumentsPane.setViewportBorder(
				new TitledBorder(null, "Instruments", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settingsPanel.add(instrumentsPane, BorderLayout.CENTER);

		JPanel instrumentsPanel = new JPanel();
		instrumentsPane.setViewportView(instrumentsPanel);
		instrumentsPanel.setLayout(new BoxLayout(instrumentsPanel, BoxLayout.Y_AXIS));

		JPanel bassInstrumentPanel = new JPanel();
		bassInstrumentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_bassInstrumentPanel = (FlowLayout) bassInstrumentPanel.getLayout();
		fl_bassInstrumentPanel.setAlignment(FlowLayout.LEADING);
		bassInstrumentPanel.setMaximumSize(new Dimension(32767, 60));
		bassInstrumentPanel.setPreferredSize(new Dimension(10, 70));
		instrumentsPanel.add(bassInstrumentPanel);

		JLabel bassLabel = new JLabel("Basse : ");
		bassInstrumentPanel.add(bassLabel);

		JComboBox bassComboBox = new JComboBox(bassInstruments.toArray());
		bassInstrumentPanel.add(bassComboBox);

		JPanel compingInstrumentPanel = new JPanel();
		compingInstrumentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_compingInstrumentPanel = (FlowLayout) compingInstrumentPanel.getLayout();
		fl_compingInstrumentPanel.setAlignment(FlowLayout.LEFT);
		compingInstrumentPanel.setPreferredSize(new Dimension(10, 70));
		compingInstrumentPanel.setMaximumSize(new Dimension(32767, 60));
		instrumentsPanel.add(compingInstrumentPanel);

		JLabel compingLabel = new JLabel("Accompagnement : ");
		compingInstrumentPanel.add(compingLabel);

		JComboBox compingComboBox = new JComboBox(compingInstruments.toArray());
		compingInstrumentPanel.add(compingComboBox);

		JPanel leadInstrumentPanel = new JPanel();
		leadInstrumentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_leadInstrumentPanel = (FlowLayout) leadInstrumentPanel.getLayout();
		fl_leadInstrumentPanel.setAlignment(FlowLayout.LEFT);
		leadInstrumentPanel.setPreferredSize(new Dimension(10, 70));
		leadInstrumentPanel.setMaximumSize(new Dimension(32767, 60));
		instrumentsPanel.add(leadInstrumentPanel);

		JLabel leadLabel = new JLabel("Lead : ");
		leadInstrumentPanel.add(leadLabel);

		JComboBox leadCombobox = new JComboBox(leadInstruments.toArray());
		leadInstrumentPanel.add(leadCombobox);

		JPanel leadInstrumentPanel_1 = new JPanel();
		leadInstrumentPanel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout flowLayout_1 = (FlowLayout) leadInstrumentPanel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		leadInstrumentPanel_1.setPreferredSize(new Dimension(10, 70));
		leadInstrumentPanel_1.setMaximumSize(new Dimension(32767, 60));
		instrumentsPanel.add(leadInstrumentPanel_1);

		JLabel drumLabel = new JLabel("Batterie :");
		leadInstrumentPanel_1.add(drumLabel);

		JComboBox drumsComboBox = new JComboBox(drumInstruments.toArray());
		leadInstrumentPanel_1.add(drumsComboBox);

		JPanel generateButtonContainer = new JPanel();
		verticalBox.add(generateButtonContainer);
		generateButtonContainer.setPreferredSize(new Dimension(10, 30));
		generateButtonContainer.setMinimumSize(new Dimension(10, 0));
		generateButtonContainer.setMaximumSize(new Dimension(32767, 44));
		generateButtonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton generate = new JButton("G\u00E9n\u00E9rer");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.song.setStruct(getStructure());
				Main.song.generate(BasicGenerator.class);
			}
		});
		generateButtonContainer.add(generate);

		JSeparator separator_1 = new JSeparator();
		verticalBox.add(separator_1);
		separator_1.setMaximumSize(new Dimension(32767, 2));

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setMinimumSize(new Dimension(2, 0));
		leftPanel.add(separator, BorderLayout.EAST);

		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		JPanel southPanel = new JPanel();
		southPanel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 3, true), "Time line",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		centerPanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setMaximumSize(new Dimension(32767, 214));
		southPanel.setPreferredSize(new Dimension(588, 180));
		southPanel.setMinimumSize(new Dimension(588, 214));
		southPanel.setLayout(new BorderLayout(0, 0));

		JPanel timeLineContainer = new JPanel();
		southPanel.add(timeLineContainer, BorderLayout.CENTER);

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
		timeLineContainer.setLayout(new BorderLayout(0, 0));

		JPanel timeLineToolbar = new JPanel();
		timeLineContainer.add(timeLineToolbar, BorderLayout.NORTH);
		FlowLayout fl_timeLineToolbar = (FlowLayout) timeLineToolbar.getLayout();
		fl_timeLineToolbar.setAlignment(FlowLayout.LEADING);

		JToolBar toolBar = new JToolBar();
		timeLineToolbar.add(toolBar);

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
		selectionMode.setIcon(new ImageIcon(ComposerFrame.class.getResource("/pic2beat/res/img/cursor.png")));
		toolBar.add(selectionMode);

		deleteButton.setMargin(new Insets(0, 0, 0, 0));
		deleteButton.setIcon(new ImageIcon(ComposerFrame.class.getResource("/pic2beat/res/img/delete.png")));
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

		toolbarGroup.add(chorusButton);
		toolbarGroup.add(introButton);
		toolbarGroup.add(verseButton);
		toolbarGroup.add(selectionMode);

		timeLinePanel = new JPanel();
		timeLineContainer.add(timeLinePanel);

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
								scorePane.show(Main.song.getSongParts().get(focusIndex));
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
		timeLinePanel.setLayout(new GridLayout(1, 0, 5, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Param\u00E8tres de partie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setPreferredSize(new Dimension(200, 10));
		southPanel.add(panel, BorderLayout.EAST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_1.setMaximumSize(new Dimension(30000, 50));
		panel.add(panel_1);

		JLabel barNumberLabel = new JLabel("Nombre de mesures :");
		panel_1.add(barNumberLabel);

		JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			}
		});
		spinner.setModel(new SpinnerNumberModel(new Integer(4), new Integer(1), null, new Integer(1)));
		panel_1.add(spinner);

		JPanel scorePaneContainer = new JPanel();
		centerPanel.add(scorePaneContainer, BorderLayout.CENTER);
		scorePaneContainer.setLayout(new BorderLayout(0, 0));

		scorePane = new ScorePane();
		scorePaneContainer.add(scorePane, BorderLayout.CENTER);
	}

	public List<SongPart.SongPartType> getStructure() {
		List<SongPart.SongPartType> structure = new ArrayList<>();
		for (Component c : timeLinePanel.getComponents()) {
			if (c instanceof SongPartPanel) {
				SongPartPanel s = (SongPartPanel) c;
				structure.add(s.getStructType());
			}
		}

		return structure;
	}

	public void newProject() {

	}

	public static int[] getSongStructure() {
		return null;
	}
}
