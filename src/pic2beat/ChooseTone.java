package pic2beat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class ChooseTone extends JDialog {
	private boolean random;
	private int tona; //de 0 à 12, commence au A qui correspond à 0
	private JCheckBox RandomCB;
	private JCheckBox MyToneCB;
	private JComboBox comboBox;
	private boolean generate;
	JButton cancelButton; 
	String message;
	private PBScore myPBScore=null;
	private int nbMesu;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */
	public ChooseTone() {
		setSize(new Dimension(450, 300));
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		ButtonGroup checkBoxGroup = new ButtonGroup();
		contentPanel.setLayout(null);
		{
			RandomCB = new JCheckBox("Random Tone");
			RandomCB.setBounds(46, 31, 135, 30);
			RandomCB.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			contentPanel.add(RandomCB);
			checkBoxGroup.add(RandomCB);
		}
		{
			MyToneCB = new JCheckBox("Use the tone below");
			MyToneCB.setBounds(46, 63, 179, 30);
			MyToneCB.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			contentPanel.add(MyToneCB);
			checkBoxGroup.add(MyToneCB);
			
		}
		{
			comboBox = new JComboBox();
			comboBox.setBounds(83, 99, 64, 28);
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"}));
			comboBox.setFont(new Font("Yu Gothic", Font.BOLD, 13));
			contentPanel.add(comboBox);
		}
		
		JSlider NbMes = new JSlider();
		NbMes.setMajorTickSpacing(10);
		NbMes.setForeground(Color.BLACK);
		NbMes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		NbMes.setPaintTicks(true);
		NbMes.setPaintLabels(true);
		NbMes.setName("Number of measures");
		NbMes.setMinorTickSpacing(2);
		NbMes.setFont(new Font("SansSerif", Font.PLAIN, 12));
		NbMes.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		NbMes.setBounds(42, 161, 200, 50);
		contentPanel.add(NbMes);
		
		JLabel NbMesures = new JLabel("Number of measures");
		NbMesures.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		NbMesures.setBounds(66, 137, 146, 22);
		contentPanel.add(NbMesures);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						setVisible(false); 
						//Sauvegarde des informations de dialogue
						if (RandomCB.isSelected()) {
							random = true;
							generate=true;
							//message="Random Tone selected";
							tona = (int)Math.random()*13;  // choix d'une tonalité aléatoire
							nbMesu= NbMes.getValue();
						}
						else if (MyToneCB.isSelected()) {
							random = false;
							generate=true;
							tona= comboBox.getSelectedIndex();
							nbMesu= NbMes.getValue();
							//message = "Tone selected: "+comboBox.getSelectedItem();
						}else {
							//message= "Nothing selected";
							generate=false;
						}
						if (generate) {
							myPBScore=new PBScore(tona, nbMesu);
						}
							
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						//message= "Nothing selected";
						generate=false;
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
		}
		
	}
	//Getters
	public boolean getIfGeneration() {
		return generate;
	}
	public boolean getRandom() {
		return random;
	}
	public int getTone() {
		return tona;
	}
	public String getMessage() {
		return message;
	}
	public PBScore getPBScore() {
		return myPBScore;
	}
	
}
