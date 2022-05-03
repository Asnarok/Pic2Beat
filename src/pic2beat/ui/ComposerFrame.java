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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jm.JMC;
import jm.constants.Instruments;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;
import pic2beat.Main;
import pic2beat.song.InstrumentRole;
import pic2beat.song.Song;
import pic2beat.song.Song.SongPartType;
import pic2beat.song.SongPart;
import pic2beat.song.generators.BasicGenerator;
import pic2beat.ui.scoredisplay.ScorePane;
import pic2beat.ui.timeline.ChorusPanel;
import pic2beat.ui.timeline.IntroPanel;
import pic2beat.ui.timeline.SongPartPanel;
import pic2beat.ui.timeline.VersePanel;
import pic2beat.utils.FileUtils;

public class ComposerFrame extends JFrame implements JMC {

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
	public static int selectedIndex;

	public static List<SongPart> songPattern = new LinkedList<>();

	public static final HashMap<Integer, String> BASS_INSTRUMENTS = new HashMap<>();
	public static final HashMap<Integer, String> COMPING_INSTRUMENTS = new HashMap<>();
	public static final HashMap<Integer, String> LEAD_INSTRUMENTS = new HashMap<>();
	public static boolean initialized = false;

	public static final String[] NOTES_LABELS = new String[] { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#",
			"G", "G#" };

	public static void initInstruments() {
		List<String> bass = loadJson("assets/instruments/bass_instruments.json");
		List<String> comping = loadJson("assets/instruments/comping_instruments.json");
		List<String> lead = loadJson("assets/instruments/lead_instruments.json");

		for (String s : bass) {
			try {
				BASS_INSTRUMENTS.put(Instruments.class.getField(s).getInt(null), s);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}

		for (String s : comping) {
			try {
				COMPING_INSTRUMENTS.put(Instruments.class.getField(s).getInt(null), s);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}


		for (String s : lead) {
			try {
				LEAD_INSTRUMENTS.put(Instruments.class.getField(s).getInt(null), s);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}

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
	private JSpinner barNumberSpinner;
	private JPanel partSettingsPanel;
	private JLabel barNumberLabel;

	private JMenuBar menuBar;

	private JMenu fileMenu;
	private JMenuItem newMenu;
	private JMenuItem saveMenuItem;
	private JMenuItem exportMenuItem;

	private JMenu projectMenu;
	private JMenuItem showScoreMenuItem;
	private JMenuItem playMenuItem;
	private JSeparator separator;
	private JPanel panel;
	private JPanel instrumentsPanel;
	private JPanel timeLineContainer;
	private JLabel compingLabel;
	private JPanel compingInstrumentPanel;
	private JToggleButton chorusButton;
	private Box verticalBox;
	private JLabel leadLabel;
	private JPanel leadInstrumentPanel;
	private JLabel bpm;
	private JComboBox compingComboBox;
	private JLabel bassLabel;
	private JPanel generateButtonContainer;
	private JPanel northSettingsPanel;
	private JComboBox bassComboBox;
	private JComboBox leadComboBox;
	private JPanel settingsPanel;
	private JLabel lblNewLabel;
	private JPanel bassInstrumentPanel;
	private JButton deleteButton;
	private JPanel centerPanel;
	private JPanel scorePaneContainer;
	private JToggleButton introButton;
	private JScrollPane instrumentsPane;
	private JSeparator separator_1;
	private JSlider bpmSlider;
	private JToggleButton verseButton;
	private JLabel titleLabel;
	private JComboBox<String> tonalityComboBox;
	private JPanel leftPanel;
	private JPanel timeLineToolbar;
	private JPanel southPanel;
	private JLabel lblTonalit;
	private JToolBar toolBar;
	private JToggleButton selectionMode;
	private JMenuItem openMenuItem;
	private JMenuItem stopMenuItem;

	/**
	 * Create the frame.
	 */
	public ComposerFrame() {
		setTitle("Pic2Beat Composer");

		barNumberSpinner = new JSpinner();
		partSettingsPanel = new JPanel();
		barNumberLabel = new JLabel("Nombre de mesures :");
		barNumberSpinner.setEnabled(false);
		if (!initialized)
			initInstruments();

		ButtonGroup toolbarGroup = new ButtonGroup();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 986, 634);
		this.setLocationRelativeTo(null);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		fileMenu = new JMenu("Fichier");
		menuBar.add(fileMenu);

		newMenu = new JMenuItem("Nouveau...");
		newMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newProject();
			}
		});
		newMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(newMenu);

		saveMenuItem = new JMenuItem("Enregistrer");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.song.setTitle(titleTextField.getText());
				Main.song.setTempo(bpmSlider.getValue());
				Main.song.setTonality(tonalityComboBox.getSelectedIndex());
				JFileChooser jfc = new JFileChooser();
				jfc.setSelectedFile(new File(Main.song.getTitle() + ".song"));
				int i = jfc.showSaveDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					String name = jfc.getSelectedFile().getName();
					if (!name.endsWith(".song"))
						name += ".song";
					File f = new File(jfc.getSelectedFile().getParentFile().getAbsolutePath() + "/" + name);
					FileUtils.saveToFile(f, Main.song);
				}
			}
		});
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(saveMenuItem);

		exportMenuItem = new JMenuItem("Exporter...");
		exportMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedIndex < Main.song.getSongParts().size()) {
					for(Object o : Main.song.getSongParts().get(selectedIndex).getPhrases().values()) {
						if(o instanceof Phrase) {
							Phrase p = (Phrase)o;
							Write.midi(p);
						}
					}
				}
			}
		});

		openMenuItem = new JMenuItem("Ouvrir");
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new FileFilter() {

					@Override
					public String getDescription() {
						return "";
					}

					@Override
					public boolean accept(File e) {
						return e.getName().endsWith(".song");
					}
				});

				int i = jfc.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					Main.song = FileUtils.loadFromFile(jfc.getSelectedFile());
					updateFields();

				}
			}
		});
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(openMenuItem);
		exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(exportMenuItem);

		projectMenu = new JMenu("Projet");
		menuBar.add(projectMenu);

		showScoreMenuItem = new JMenuItem("Afficher la partition");
		showScoreMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Score s = Main.song.toScore();
				View.notate(s);
			}
		});
		projectMenu.add(showScoreMenuItem);

		playMenuItem = new JMenuItem("Jouer la partition");
		playMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Score s = Main.song.toScore();
				Play.midi(s);
			}
		});
		playMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		projectMenu.add(playMenuItem);

		stopMenuItem = new JMenuItem("Arr\u00EAter");
		stopMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Play.stopMidi();
			}
		});
		projectMenu.add(stopMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		leftPanel = new JPanel();
		contentPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));

		verticalBox = Box.createVerticalBox();
		leftPanel.add(verticalBox);
		verticalBox.setPreferredSize(new Dimension(250, 0));
		verticalBox.setMaximumSize(new Dimension(250, 0));

		settingsPanel = new JPanel();
		verticalBox.add(settingsPanel);
		settingsPanel.setMaximumSize(new Dimension(245, 1000));
		settingsPanel.setPreferredSize(new Dimension(245, 303));
		settingsPanel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 3, true),
				"Param\u00E8tres g\u00E9n\u00E9raux", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		settingsPanel.setLayout(new BorderLayout(0, 0));

		northSettingsPanel = new JPanel();
		northSettingsPanel.setPreferredSize(new Dimension(160, 160));
		settingsPanel.add(northSettingsPanel, BorderLayout.NORTH);
		northSettingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		titleLabel = new JLabel("Titre : ");
		northSettingsPanel.add(titleLabel);

		titleTextField = new JTextField();
		northSettingsPanel.add(titleTextField);
		titleTextField.setColumns(16);

		lblNewLabel = new JLabel("Tempo : ");
		northSettingsPanel.add(lblNewLabel);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		bpmTextField = new JTextField();
		bpmTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				bpmSlider.setValue(Integer.parseInt(bpmTextField.getText()));
				Main.song.setTempo(bpmSlider.getValue());
			}
		});
		bpmTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bpmSlider.setValue(Integer.parseInt(bpmTextField.getText()));
					Main.song.setTempo(bpmSlider.getValue());
				}
			}
		});
		bpmTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		bpmTextField.setPreferredSize(new Dimension(50, 20));
		bpmTextField.setMaximumSize(new Dimension(50, 50));
		bpmTextField.setText("80");
		northSettingsPanel.add(bpmTextField);
		bpmTextField.setColumns(3);

		bpm = new JLabel("bpm");
		northSettingsPanel.add(bpm);

		bpmSlider = new JSlider();
		bpmSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bpmTextField.setText(bpmSlider.getValue() + "");
				Main.song.setTempo(bpmSlider.getValue());
			}
		});
		northSettingsPanel.add(bpmSlider);
		bpmSlider.setOpaque(false);
		bpmSlider.setBackground(new Color(220, 220, 220));
		bpmSlider.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		bpmSlider.setValue(80);
		bpmSlider.setMinorTickSpacing(5);
		bpmSlider.setMajorTickSpacing(60);
		bpmSlider.setMaximum(200);
		bpmSlider.setMinimum(20);
		bpmSlider.setToolTipText("");
		bpmSlider.setPaintLabels(true);
		bpmSlider.setPaintTicks(true);

		lblTonalit = new JLabel("Tonalit\u00E9 :");
		northSettingsPanel.add(lblTonalit);
		lblTonalit.setOpaque(true);
		lblTonalit.setHorizontalAlignment(SwingConstants.CENTER);

		tonalityComboBox = new JComboBox<>();
		northSettingsPanel.add(tonalityComboBox);
		tonalityComboBox.setModel(new DefaultComboBoxModel<String>(NOTES_LABELS));

		instrumentsPane = new JScrollPane();
		instrumentsPane.setViewportBorder(
				new TitledBorder(null, "Instruments", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settingsPanel.add(instrumentsPane, BorderLayout.CENTER);

		instrumentsPanel = new JPanel();
		instrumentsPane.setViewportView(instrumentsPanel);
		instrumentsPanel.setLayout(new BoxLayout(instrumentsPanel, BoxLayout.Y_AXIS));

		bassInstrumentPanel = new JPanel();
		bassInstrumentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_bassInstrumentPanel = (FlowLayout) bassInstrumentPanel.getLayout();
		fl_bassInstrumentPanel.setAlignment(FlowLayout.LEADING);
		bassInstrumentPanel.setMaximumSize(new Dimension(32767, 60));
		bassInstrumentPanel.setPreferredSize(new Dimension(10, 70));
		instrumentsPanel.add(bassInstrumentPanel);

		bassLabel = new JLabel("Basse : ");
		bassInstrumentPanel.add(bassLabel);

		bassComboBox = new JComboBox(BASS_INSTRUMENTS.values().toArray());
		bassComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					Main.song.setBass(JMC.class.getField((String) e.getItem()).getInt(null));
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
			}
		});
		bassInstrumentPanel.add(bassComboBox);

		compingInstrumentPanel = new JPanel();
		compingInstrumentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_compingInstrumentPanel = (FlowLayout) compingInstrumentPanel.getLayout();
		fl_compingInstrumentPanel.setAlignment(FlowLayout.LEFT);
		compingInstrumentPanel.setPreferredSize(new Dimension(10, 70));
		compingInstrumentPanel.setMaximumSize(new Dimension(32767, 60));
		instrumentsPanel.add(compingInstrumentPanel);

		compingLabel = new JLabel("Accompagnement : ");
		compingInstrumentPanel.add(compingLabel);

		compingComboBox = new JComboBox(COMPING_INSTRUMENTS.values().toArray());
		compingComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					Main.song.setChords(JMC.class.getField((String) e.getItem()).getInt(null));
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
			}
		});
		compingInstrumentPanel.add(compingComboBox);

		leadInstrumentPanel = new JPanel();
		leadInstrumentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_leadInstrumentPanel = (FlowLayout) leadInstrumentPanel.getLayout();
		fl_leadInstrumentPanel.setAlignment(FlowLayout.LEFT);
		leadInstrumentPanel.setPreferredSize(new Dimension(10, 70));
		leadInstrumentPanel.setMaximumSize(new Dimension(32767, 60));
		instrumentsPanel.add(leadInstrumentPanel);

		leadLabel = new JLabel("Lead : ");
		leadInstrumentPanel.add(leadLabel);

		leadComboBox = new JComboBox(LEAD_INSTRUMENTS.values().toArray());
		leadComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					Main.song.setLead(JMC.class.getField((String) e.getItem()).getInt(null));
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
			}
		});
		leadInstrumentPanel.add(leadComboBox);

		generateButtonContainer = new JPanel();
		verticalBox.add(generateButtonContainer);
		generateButtonContainer.setPreferredSize(new Dimension(10, 30));
		generateButtonContainer.setMinimumSize(new Dimension(10, 0));
		generateButtonContainer.setMaximumSize(new Dimension(32767, 44));
		generateButtonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton generate = new JButton("G\u00E9n\u00E9rer");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.song.clearSongParts();
				List<SongPartType> struct = getStructure();
				if (struct.size() > 0) {
					Main.song.setStruct(struct);

					try {
						Main.song.setBass(
								Instruments.class.getField((String) bassComboBox.getSelectedItem()).getInt(null));
						Main.song.setChords(
								Instruments.class.getField((String) compingComboBox.getSelectedItem()).getInt(null));
						Main.song.setLead(
								Instruments.class.getField((String) leadComboBox.getSelectedItem()).getInt(null));
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Main.song.generate(BasicGenerator.class);
					scorePane.show(Main.song.getSongParts().get(selectedIndex));
				} else {
					JOptionPane.showMessageDialog(newMenu,
							"La structure du morceau ne peut pas être vide. Veuillez la définir.");
				}
			}
		});
		generateButtonContainer.add(generate);

		separator_1 = new JSeparator();
		verticalBox.add(separator_1);
		separator_1.setMaximumSize(new Dimension(32767, 2));

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setMinimumSize(new Dimension(2, 0));
		leftPanel.add(separator, BorderLayout.EAST);

		centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		southPanel = new JPanel();
		southPanel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 3, true), "Time line",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		centerPanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setMaximumSize(new Dimension(32767, 214));
		southPanel.setPreferredSize(new Dimension(588, 180));
		southPanel.setMinimumSize(new Dimension(588, 214));
		southPanel.setLayout(new BorderLayout(0, 0));

		timeLineContainer = new JPanel();
		southPanel.add(timeLineContainer, BorderLayout.CENTER);

		deleteButton = new JButton("");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeLinePanel.remove(lastFocused);
				if (Main.song.getSongParts().size() == partCount) {
					Main.song.getSongParts().remove(selectedIndex);
					Main.song.removeFromStruct(selectedIndex);
				}
				lastFocused = null;
				deleteButton.setEnabled(false);
				timeLinePanel.revalidate();
				timeLinePanel.repaint();
				partCount--;
				if (partCount == 0) {

					partSettingsPanel.setEnabled(false);
					barNumberLabel.setEnabled(false);
					barNumberSpinner.setEnabled(false);
				}
			}
		});
		timeLineContainer.setLayout(new BorderLayout(0, 0));

		timeLineToolbar = new JPanel();
		timeLineContainer.add(timeLineToolbar, BorderLayout.NORTH);
		FlowLayout fl_timeLineToolbar = (FlowLayout) timeLineToolbar.getLayout();
		fl_timeLineToolbar.setAlignment(FlowLayout.LEADING);

		toolBar = new JToolBar();
		timeLineToolbar.add(toolBar);

		introButton = new JToggleButton("Intro");
		introButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					adding = INTRO;
			}
		});

		selectionMode = new JToggleButton("");
		selectionMode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					adding = IDLE;
			}
		});
		selectionMode.setMargin(new Insets(0, 0, 0, 0));
		String imagesFolderPath = System.getProperty("user.dir");
		selectionMode.setIcon(new ImageIcon(imagesFolderPath + "\\assets\\images\\cursor.png"));
		toolBar.add(selectionMode);

		deleteButton.setMargin(new Insets(0, 0, 0, 0));
		deleteButton.setIcon(new ImageIcon(imagesFolderPath + "\\assets\\images\\delete.png"));
		toolBar.add(deleteButton);
		introButton.setActionCommand("");
		toolBar.add(introButton);

		verseButton = new JToggleButton("Couplet");
		verseButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					adding = VERSE;
			}
		});
		toolBar.add(verseButton);

		chorusButton = new JToggleButton("Refrain");
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
							barNumberSpinner.setEnabled(true);
							partSettingsPanel.setEnabled(true);
							barNumberLabel.setEnabled(true);

							if (p != lastFocused) {
								selectedIndex = focusIndex;
								if (Main.song.getSongParts().size() > focusIndex) {
									scorePane.show(Main.song.getSongParts().get(focusIndex));
									barNumberSpinner.setValue(Main.song.getSongParts().get(focusIndex).getLength());
								}

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

		partSettingsPanel.setEnabled(false);
		partSettingsPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Param\u00E8tres de partie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		partSettingsPanel.setPreferredSize(new Dimension(200, 10));
		southPanel.add(partSettingsPanel, BorderLayout.EAST);
		partSettingsPanel.setLayout(new BoxLayout(partSettingsPanel, BoxLayout.Y_AXIS));

		panel = new JPanel();
		FlowLayout fl_panel = (FlowLayout) panel.getLayout();
		fl_panel.setAlignment(FlowLayout.LEFT);
		panel.setMaximumSize(new Dimension(30000, 50));
		partSettingsPanel.add(panel);

		barNumberLabel.setEnabled(false);
		panel.add(barNumberLabel);

		barNumberSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Main.song.getSongParts().get(selectedIndex).setLength((int) barNumberSpinner.getValue());
			}
		});
		barNumberSpinner.setModel(new SpinnerNumberModel(new Integer(4), new Integer(1), null, new Integer(1)));
		panel.add(barNumberSpinner);

		scorePaneContainer = new JPanel();
		centerPanel.add(scorePaneContainer, BorderLayout.CENTER);
		scorePaneContainer.setLayout(new BorderLayout(0, 0));

		scorePane = new ScorePane();
		scorePaneContainer.add(scorePane, BorderLayout.CENTER);
	}

	public List<SongPartType> getStructure() {
		List<SongPartType> structure = new ArrayList<>();
		for (Component c : timeLinePanel.getComponents()) {
			if (c instanceof SongPartPanel) {
				SongPartPanel s = (SongPartPanel) c;
				structure.add(s.getStructType());
			}
		}

		return structure;
	}

	public void newProject() {
		partSettingsPanel.setEnabled(false);
		barNumberLabel.setEnabled(false);
		barNumberSpinner.setEnabled(false);
		partCount = 0;
		bpmSlider.setValue(80);
		bpmTextField.setText(80 + "");
		tonalityComboBox.setSelectedIndex(0);
		timeLinePanel.removeAll();
		timeLinePanel.revalidate();
		deleteButton.setEnabled(false);
		titleTextField.setText("Nouveau morceau");
		Main.song = new Song("Nouveau morceau");
		Main.song.setLead(VIBRAPHONE).setChords(PIANO).setDrums(PIANO) // TODO DrumKit
				.setBass(BASS).addInstrument("Alto", InstrumentRole.THIRDS, VIOLA)
				.addInstrument("Violin", InstrumentRole.FIFTHS, VIOLIN);
	}

	public void updateFields() {
		bpmSlider.setValue(Main.song.getTempo());
		bpmTextField.setText(Main.song.getTempo() + "");
		tonalityComboBox.setSelectedItem(NOTES_LABELS[Main.song.getTonality()]);
		System.out.println(Main.song.getStructure().size());
		for (SongPartType t : Main.song.getStructure()) {
			if (t == SongPartType.INTRO)
				timeLinePanel.add(new IntroPanel());
			else if (t == SongPartType.VERSE)
				timeLinePanel.add(new VersePanel());
			else if (t == SongPartType.CHORUS)
				timeLinePanel.add(new ChorusPanel());
		}

		bassComboBox.setSelectedItem(BASS_INSTRUMENTS.get(Main.song.getBass().getInstrument()));
		compingComboBox.setSelectedItem(COMPING_INSTRUMENTS.get(Main.song.getChords().getInstrument()));
		leadComboBox.setSelectedItem(LEAD_INSTRUMENTS.get(Main.song.getLead().getInstrument()));

		partCount = Main.song.getStructure().size();
		timeLinePanel.revalidate();
		titleTextField.setText(Main.song.getTitle());
	}

}
