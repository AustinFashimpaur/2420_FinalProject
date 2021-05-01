package src;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;

public class GUI extends JFrame{

	private JFrame frame;
	private JPanel IntroScreen;
	private JTextField source;
	private JLabel lblLogo;
	private JTextField destination;
	private JButton btnSearch;
	private JLabel lblNewLabel;
	private JLabel lblDestination;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setSize(684, 420);
	    frame.getContentPane().setLayout(new CardLayout(0, 0));
	    
	    IntroScreen = new JPanel();
	    IntroScreen.setBackground(Color.WHITE);
	    frame.getContentPane().add(IntroScreen, "name_1149608370375900");
	    IntroScreen.setLayout(null);
	    
	    source = new JTextField();
	    source.setToolTipText("");
	    source.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    source.setBounds(224, 175, 270, 40);
	    IntroScreen.add(source);
	    source.setColumns(10);
	    
	    lblLogo = new JLabel("");
	    lblLogo.setIcon(new ImageIcon(GUI.class.getResource("/src/resources/logo.png")));
	    lblLogo.setBounds(261, 11, 152, 144);
	    IntroScreen.add(lblLogo);
	    
	    destination = new JTextField();
	    destination.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    destination.setColumns(10);
	    destination.setBounds(224, 244, 270, 40);
	    IntroScreen.add(destination);
	    
	    btnSearch = new JButton("Search");
	    btnSearch.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
	    btnSearch.setBounds(290, 308, 102, 42);
	    IntroScreen.add(btnSearch);
	    
	    lblNewLabel = new JLabel("Source:");
	    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    lblNewLabel.setBounds(130, 179, 67, 30);
	    IntroScreen.add(lblNewLabel);
	    
	    lblDestination = new JLabel("Destination:");
	    lblDestination.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    lblDestination.setBounds(92, 248, 111, 30);
	    IntroScreen.add(lblDestination);
	    frame.setVisible(true);
	}
}
