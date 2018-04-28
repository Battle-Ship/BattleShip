package lv.odo.battleship.demo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

public class NameURL extends JFrame {

	private JPanel contentPane;
	private JTextField Name;
	private JTextField textFieldURL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NameURL frame = new NameURL();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NameURL() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel URL = new JPanel();
		contentPane.add(URL);
		
		textFieldURL = new JTextField("URL server");
		URL.add(textFieldURL);
		textFieldURL.setColumns(10);
		
		JPanel userName = new JPanel();
		contentPane.add(userName, BorderLayout.NORTH);
		
		Name = new JTextField("Name");
		userName.add(Name);
		Name.setColumns(10);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Save");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		panel.add(btnNewButton_1);
	}

}
