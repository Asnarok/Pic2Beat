package pic2beat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.SoftBevelBorder;

import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JSlider;
import java.awt.Cursor;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Frame extends JFrame {

	private JPanel contentPane;
	PBScore myPBScore;
	JTextField message;
	ChooseTone dialog=new ChooseTone();;
	JSlider SliderTempo;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Frame() {
		setSize(new Dimension(1000, 700));
		
		this.setTitle("PicToBeat");
		myPBScore=null;
		//screen dimension
		getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(23, 28, 273, 608);
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(panel_1);
		panel_1.setBackground(new Color(153, 153, 153));
		panel_1.setForeground(new Color(153, 153, 255));
		panel_1.setLayout(null);
		
		JButton GenerateButton = new JButton("Generate Music");
		GenerateButton.setBounds(30, 50, 212, 36);
		panel_1.add(GenerateButton);
		GenerateButton.setToolTipText("");
		GenerateButton.setVerticalAlignment(SwingConstants.BOTTOM);
		GenerateButton.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 12));
		
		JButton PlayButton = new JButton("Play Music");
		PlayButton.setBounds(30, 113, 212, 36);
		PlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPBScore=dialog.getPBScore();
				if (myPBScore==null){
                	message.setText("No Music generated");
                }else {
                myPBScore.setTempo(SliderTempo.getValue());
            	myPBScore.playMusic();
            	message.setText("Music played");
                }
			}
		});
		PlayButton.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 12));
		panel_1.add(PlayButton);
		
		JButton SaveButton = new JButton("Save Midi");
		SaveButton.setBounds(30, 302, 212, 36);
		SaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPBScore=dialog.getPBScore();
				if (myPBScore==null){
                	message.setText("No Music generated");
                }else {
                myPBScore.setTempo(SliderTempo.getValue());
            	myPBScore.saveMidi();
            	message.setText(" ");
                }
			}
		});
		SaveButton.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 12));
		panel_1.add(SaveButton);
		
		message = new JTextField();
		message.setBounds(30, 366, 212, 45);
		message.setEditable(false);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 12));
		panel_1.add(message);
		message.setColumns(10);
		
		SliderTempo = new JSlider();
		SliderTempo.setMaximum(200);
		SliderTempo.setMinimum(20);
		SliderTempo.setBounds(17, 175, 238, 64);
		SliderTempo.setPaintLabels(true);
		SliderTempo.setPaintTicks(true);
		SliderTempo.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		SliderTempo.setBackground(new Color(153, 153, 153));
		SliderTempo.setName("Tempo");
		panel_1.add(SliderTempo);
		SliderTempo.setMinorTickSpacing(2);  
		SliderTempo.setMajorTickSpacing(20);
		
		JLabel Tempo = new JLabel("Tempo");
		Tempo.setBounds(113, 161, 59, 16);
		Tempo.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 12));
		panel_1.add(Tempo);
		
		JButton ViewButton = new JButton("View Score");
		ViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPBScore=dialog.getPBScore();
				if (myPBScore==null){
                	message.setText("No Music generated");
                }else {
                myPBScore.setTempo(SliderTempo.getValue());
            	myPBScore.scoreMusic(400, 50);
            	message.setText("Score displayed");
                }
			}
		});
		ViewButton.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 12));
		ViewButton.setBounds(30, 254, 207, 36);
		panel_1.add(ViewButton);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		lblNewLabel.setBounds(0, 0, 980, 663);
		lblNewLabel.setIcon(new ImageIcon("res\\pictures\\astro.jpg"));
		contentPane.add(lblNewLabel);
		GenerateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//ouvre la fenêtre de dialogue pour le choix de la tonalité
				try {
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);	
					
				} catch (Exception eo) {
					eo.printStackTrace();
				}
				message.setText(dialog.getMessage());
				
			}
		});
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	public void setMessage(String m) {
		message.setText(m);
	}
}
