package lv.odo.battleship.demo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Multiplayer {

	private JFrame frame;
	private JPanel panel_1;
	private JPanel panelMultipl;
	private JTextField Multiplayer;
	private JPanel panelList;
	private JTable ListPlayers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multiplayer window = new Multiplayer();
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
	public Multiplayer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnNewButton = new JButton("Create game");
		frame.getContentPane().add(btnNewButton, BorderLayout.SOUTH);
		
		panelMultipl = new JPanel();
		frame.getContentPane().add(panelMultipl, BorderLayout.NORTH);
		
		Multiplayer = new JTextField("Multiplayer");
		panelMultipl.add(Multiplayer);
		Multiplayer.setColumns(10);
		
		panelList = new JPanel();
		frame.getContentPane().add(panelList, BorderLayout.CENTER);
		
		ListPlayers = new JTable();
		panelList.add(ListPlayers);
	}

}
