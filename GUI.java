package src;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.ComponentOrientation;
import javax.swing.JList;

/**
 * Represents the GUI interface for the wiki project.
 * @author Austin Fashimpaur
 *
 */
public class GUI extends JFrame {

	private JFrame frame;
	private JPanel introScreen;
	private JTextField source;
	private JLabel lblLogo;
	private JTextField destination;
	private JButton btnSearch;
	private JLabel lblSource;
	private JLabel lblDestination;
	private JPanel resultsScreen;
	private JLabel destinationWarning;
	private JLabel sourceWarning;
	private JLabel lblLink;
	private JTextField linkNumber;
	private JLabel linkWarning;
	private static ArrayList<String> pathTo;
	private JLabel lblResults;

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
		frame.setSize(686, 540);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		introScreen = new JPanel();
		introScreen.setBackground(Color.WHITE);
		frame.getContentPane().add(introScreen, "name_1149608370375900");
		introScreen.setLayout(null);

		source = new JTextField();
		source.setToolTipText("");
		source.setFont(new Font("Tahoma", Font.PLAIN, 20));
		source.setBounds(224, 175, 270, 40);
		introScreen.add(source);
		source.setColumns(10);

		lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(GUI.class.getResource("/src/resources/logo.png")));
		lblLogo.setBounds(261, 11, 152, 144);
		introScreen.add(lblLogo);

		destination = new JTextField();
		destination.setFont(new Font("Tahoma", Font.PLAIN, 20));
		destination.setColumns(10);
		destination.setBounds(224, 258, 270, 40);
		introScreen.add(destination);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validSource = true;
				boolean validDestination = true;
				boolean validNum = true;

				// validate source is not blank
				if (source.getText().trim().isEmpty()) {
					source.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red));
					sourceWarning.setText("Source cannot be blank!");
					sourceWarning.setVisible(true);
					validSource = false;
				} else {
					validSource = true;
					source.setBorder(BorderFactory.createLineBorder(Color.gray));
					sourceWarning.setVisible(false);
				}

				// validate destination is not blank
				if (destination.getText().trim().isEmpty()) {
					destination.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red));
					destinationWarning.setText("Destination cannot be blank!");
					destinationWarning.setVisible(true);
					validDestination = false;
				} else {
					validDestination = true;
					destination.setBorder(BorderFactory.createLineBorder(Color.gray));
					destinationWarning.setVisible(false);
				}
				
				//validate number
				if (linkNumber.getText().trim().isEmpty() || Integer.parseInt(linkNumber.getText()) > 500 || Integer.parseInt(linkNumber.getText()) < 1) {
					linkNumber.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red));
					linkWarning.setVisible(true);
					validNum = false;
				} else {
					validNum = true;
					linkNumber.setBorder(BorderFactory.createLineBorder(Color.gray));
					linkWarning.setVisible(false);
				}

				// validate if source is a valid wikipedia page
				if (validSource && validDestination && validNum) {
					if (!Validate.validatePage(source.getText())) {
						source.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red));
						sourceWarning.setText("Not a valid Wikipedia page!");
						sourceWarning.setVisible(true);
						validSource = false;
					} else {
						validSource = true;
						source.setBorder(BorderFactory.createLineBorder(Color.gray));
						sourceWarning.setVisible(false);
					}

					// validate if destination is valid wikipedia page
					if (!Validate.validatePage(destination.getText())) {
						destination.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red));
						destinationWarning.setText("Not a valid Wikipedia page!");
						destinationWarning.setVisible(true);
						validDestination = false;
					} else {
						validDestination = true;
						destination.setBorder(BorderFactory.createLineBorder(Color.gray));
						destinationWarning.setVisible(false);
					}
				}

				if (validSource && validDestination) {
					introScreen.setVisible(false);
					pathTo = WikiApi.findPath(source.getText(), destination.getText(), Integer.parseInt(linkNumber.getText()));
					if(pathTo.isEmpty()) {
						pathTo.add("No Path Found!");
					}
					
					
					JList displayList = new JList(pathTo.toArray());
			        JScrollPane scrollPane = new JScrollPane(displayList);
			        scrollPane.setBounds(230, 122, 180, 180);
			        resultsScreen.add(scrollPane);
					resultsScreen.setVisible(true);
				}

			}
		});
		btnSearch.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		btnSearch.setBounds(296, 436, 102, 42);
		introScreen.add(btnSearch);

		lblSource = new JLabel("Source:");
		lblSource.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSource.setBounds(130, 179, 67, 30);
		introScreen.add(lblSource);

		lblDestination = new JLabel("Destination:");
		lblDestination.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDestination.setBounds(103, 262, 111, 30);
		introScreen.add(lblDestination);

		destinationWarning = new JLabel("Destination cannot be blank!");
		destinationWarning.setVisible(false);
		destinationWarning.setFont(new Font("Tahoma", Font.PLAIN, 13));
		destinationWarning.setForeground(new Color(220, 20, 60));
		destinationWarning.setBounds(271, 299, 161, 30);
		introScreen.add(destinationWarning);

		sourceWarning = new JLabel("Source cannot be blank!");
		sourceWarning.setVisible(false);
		sourceWarning.setForeground(new Color(220, 20, 60));
		sourceWarning.setFont(new Font("Tahoma", Font.PLAIN, 13));
		sourceWarning.setBounds(271, 217, 161, 30);
		introScreen.add(sourceWarning);
		
		lblLink = new JLabel("Links per page:");
		lblLink.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblLink.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLink.setBounds(78, 351, 145, 30);
		introScreen.add(lblLink);
		
		linkNumber = new JTextField();
		linkNumber.setToolTipText("Maximum is 500");
		linkNumber.setText("10");
		linkNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		linkNumber.setColumns(10);
		linkNumber.setBounds(224, 347, 67, 40);
		introScreen.add(linkNumber);
		
		linkWarning = new JLabel("Links must be a number between 1 - 500!");
		linkWarning.setForeground(new Color(220, 20, 60));
		linkWarning.setFont(new Font("Tahoma", Font.PLAIN, 13));
		linkWarning.setBounds(234, 398, 250, 14);
		introScreen.add(linkWarning);
		linkWarning.setVisible(false);

		frame.setVisible(true);
		
		resultsScreen = new JPanel();
		frame.getContentPane().add(resultsScreen, "name_1225352941932200");
		resultsScreen.setLayout(null);
		
        
        lblResults = new JLabel("Results:");
        lblResults.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblResults.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        lblResults.setBounds(276, 35, 134, 38);
        resultsScreen.add(lblResults);
        
	}
}
