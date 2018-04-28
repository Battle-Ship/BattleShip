package lv.odo.battleship.demo;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {

	private JFrame frame;

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
		buildStartMenuWindow();
	}

	private void buildStartMenuWindow() {
		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(new Color(224, 255, 255));
		panelCenter.setForeground(Color.BLUE);
		frame.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 60));

		JPanel panel0 = new JPanel();
		panel0.setBackground(new Color(224, 255, 255));
		FlowLayout fl_panel0 = (FlowLayout) panel0.getLayout();
		fl_panel0.setVgap(0);
		fl_panel0.setHgap(40);
		panelCenter.add(panel0, BorderLayout.NORTH);

		JButton btnSingleplayer = new JButton();
		ImageIcon singleplayerIcon = new ImageIcon("images/singleplayer.png");
		btnSingleplayer.setIcon(singleplayerIcon);
		// to remote the spacing between the image and button's borders
		btnSingleplayer.setMargin(new Insets(0, 0, 0, 0));
		// to remove the border
		btnSingleplayer.setBorder(null);
		btnSingleplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("open battlefield");
			}
		});
		btnSingleplayer.setBackground(new Color(224, 255, 255));
		panel0.add(btnSingleplayer);

		JButton btnMultiplayer = new JButton();
		ImageIcon multiplayerIcon = new ImageIcon("images/multiplayer.png");

		Component rigidArea = Box.createRigidArea(new Dimension(10, 20));
		panel0.add(rigidArea);
		btnMultiplayer.setIcon(multiplayerIcon);
		// to remote the spacing between the image and button's borders
		btnMultiplayer.setMargin(new Insets(0, 0, 0, 0));
		// to remove the border
		btnMultiplayer.setBorder(null);
		btnMultiplayer.setBackground(new Color(224, 255, 255));
		panel0.add(btnMultiplayer);

		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(224, 255, 255));
		FlowLayout fl_panel1 = (FlowLayout) panel1.getLayout();
		fl_panel1.setVgap(0);
		fl_panel1.setHgap(20);
		panelCenter.add(panel1, BorderLayout.CENTER);

		JButton btnSettings = new JButton();
		ImageIcon settingsIcon = new ImageIcon("images/settings.png");
		btnSettings.setIcon(settingsIcon);
		// to remote the spacing between the image and button's borders
		btnSettings.setMargin(new Insets(0, 0, 0, 0));
		// to remove the border
		btnSettings.setBorder(null);
		btnSettings.setBackground(new Color(224, 255, 255));
		panel1.add(btnSettings);

		JButton btnExit = new JButton();
		ImageIcon exitIcon = new ImageIcon("images/exit.png");

		Component rigidArea_1 = Box.createRigidArea(new Dimension(30, 20));
		panel1.add(rigidArea_1);
		btnExit.setIcon(exitIcon);
		// to remote the spacing between the image and button's borders
		btnExit.setMargin(new Insets(0, 0, 0, 0));
		// to remove the border
		btnExit.setBorder(null);
		btnExit.setBackground(new Color(224, 255, 255));
		panel1.add(btnExit);
		//we need to handle click on button exit to exit program
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JPanel panelTop = new JPanel();
		FlowLayout fl_panelTop = (FlowLayout) panelTop.getLayout();
		fl_panelTop.setVgap(30);
		panelTop.setBackground(new Color(224, 255, 255));
		frame.getContentPane().add(panelTop, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Battleship");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 35));
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelTop.add(lblNewLabel);
	}

}
