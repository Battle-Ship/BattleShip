package lv.odo.battleship.demo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;

public class Menu {

	private JFrame frame;
	private JTextField txtBattleShip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
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
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JButton btnNewButton_6 = new JButton("Singleplayer");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_3.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("Multiplayer");
		panel_3.add(btnNewButton_7);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JButton btnNewButton_4 = new JButton("     Pause     ");
		panel_2.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("   Continue   ");
		panel_2.add(btnNewButton_5);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JButton btnNewButton_2 = new JButton(" Settings ");
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("     Exit     ");
		panel_1.add(btnNewButton_3);
		
		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4, BorderLayout.NORTH);
		
		txtBattleShip = new JTextField();
		txtBattleShip.setForeground(Color.BLUE);
		txtBattleShip.setBackground(Color.WHITE);
		txtBattleShip.setHorizontalAlignment(SwingConstants.CENTER);
		txtBattleShip.setFont(new Font("Dialog", Font.BOLD, 20));
		txtBattleShip.setText("Battle Ship");
		panel_4.add(txtBattleShip);
		txtBattleShip.setColumns(10);
	}

}
