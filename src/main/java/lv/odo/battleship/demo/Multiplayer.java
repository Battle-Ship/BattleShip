package lv.odo.battleship.demo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;

public class Multiplayer extends JFrame {

	private JPanel contentPane;
	private JTextField txtMultiplayer;
	private JTable Listtable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multiplayer frame = new Multiplayer();
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
	public Multiplayer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel Textpanel = new JPanel();
		contentPane.add(Textpanel);
		
		txtMultiplayer = new JTextField();
		txtMultiplayer.setText("Multiplayer");
		Textpanel.add(txtMultiplayer);
		txtMultiplayer.setColumns(10);
		
		JPanel Listpanel = new JPanel();
		contentPane.add(Listpanel, BorderLayout.NORTH);
		
		Listtable = new JTable();
		Listpanel.add(Listtable);
		
		JPanel Buttonpanel = new JPanel();
		contentPane.add(Buttonpanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Main Menu");
		Buttonpanel.add(btnNewButton);
	}

}
