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

public class GUI extends JFrame{

	private JFrame frame;
	private JTextField txtEnterStateName;
	private JTextField txtEnterSecondState;

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
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBounds(358, 121, 78, 30);
		panel.setLayout(null);
	    panel.add(btnNewButton);
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.getContentPane().add(panel);
	    frame.setSize(500, 300);
	    frame.setVisible(true);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnNewButton);
		
		txtEnterStateName = new JTextField();
		txtEnterStateName.setHorizontalAlignment(SwingConstants.CENTER);	
//		txtEnterStateName.setText("Enter First State");
		txtEnterStateName.setBounds(345, 21, 111, 30);
		panel.add(txtEnterStateName);
		txtEnterStateName.setColumns(10);
		
		txtEnterSecondState = new JTextField();
		txtEnterSecondState.setHorizontalAlignment(SwingConstants.CENTER);
//		txtEnterSecondState.setText("Enter Second State");
		txtEnterSecondState.setBounds(345, 61, 111, 30);
		panel.add(txtEnterSecondState);
		txtEnterSecondState.setColumns(10);	
	}
}
