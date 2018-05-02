package MultiplayerInterface;

import MultiplayerGame.*;
import lv.odo.battleship.demo.Main;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.Box;
import java.awt.Component;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;

public class MainMulti {
	
	//сначала определимся с габаритами нашего игрового окна
	//пусть размер ячейчки будет 30 единиц
	public static final int CELL_SIZE = 30;
	
	//количество ячеек в игровом поле 10*10, значит одно измерение равно 10
	public static final int FIELD_DIMENSION = 10;
	
	//ширина окна такая, чтобы влезало два поля и было место для небольшого зазора
	public static final int WINDOW_WIDTH = CELL_SIZE * 2 * FIELD_DIMENSION + CELL_SIZE * 4;
	
	//высота окна такая, чтобы влезало поле и было место для кнопок и расстояние снизу
	public static final int WINDOW_HEIGHT = CELL_SIZE * FIELD_DIMENSION + CELL_SIZE * 6;
	
	public static final int SHIPS = 5;
	
	private JFrame frame;
	private BoardTable leftTable;
	private BoardTable rightTable;
	private JPanel leftShipsPanel;
	private JPanel rightShipsPanel;
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public PlayerLocation playerField;
	public Field opponentField;
	public boolean yourShot = false;
	public JLabel gameStatusLabel;
	Client client;

	public MainMulti(JFrame frame, InetAddress ip) throws Exception {
		client = new Client(ip, this);
		this.frame = frame;
		playerField = new PlayerLocation();
		opponentField = new Field();
		initialize();
	}


	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() {
		frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//we set layout to place game objects on game window
		frame.getContentPane().setLayout(new BorderLayout(0, 0));	
		JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        JTextPane txtpnHelpTextAbout = new JTextPane();
        txtpnHelpTextAbout.setText("Help Text About game");
        mnHelp.add(txtpnHelpTextAbout);

        JPanel panel = new JPanel();
        menuBar.add(panel);

        this.gameStatusLabel = new JLabel("");
        gameStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(gameStatusLabel);
		buildBattlefield();
	}
	
	private void buildBattlefield() {
		gameStatusLabel.setText("Place 4 1-cell ships on a field");
		JPanel bottom = new JPanel();		
		frame.getContentPane().add(bottom, BorderLayout.SOUTH);		
		Button buttonEnd = new Button("End game");
		bottom.add(buttonEnd);
		buttonEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				new Main(frame);
			}
		});		
		Dimension dimension = new Dimension(CELL_SIZE * FIELD_DIMENSION, CELL_SIZE * FIELD_DIMENSION);		
		JPanel left = new JPanel();		
		FlowLayout flowLayout_2 = (FlowLayout) left.getLayout();
		flowLayout_2.setVgap(0);
		left.setMinimumSize(dimension);
		frame.getContentPane().add(left, BorderLayout.WEST);		
		JPanel leftField = new JPanel();
		left.add(leftField);
		leftField.setLayout(new BorderLayout(0, 0));		
		leftTable = new BoardTable(playerField.getField());
		leftField.add(leftTable, BorderLayout.CENTER);		
		JLabel player0 = new JLabel("My Field");
		leftField.add(player0, BorderLayout.NORTH);
		player0.setFont(new Font("Tahoma", Font.PLAIN, 22));
		player0.setHorizontalAlignment(SwingConstants.CENTER);		
		leftShipsPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) leftShipsPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		leftField.add(leftShipsPanel, BorderLayout.SOUTH);		
		//refreshShipsPanel(leftShipsPanel, processor.getMyField(currentGame.getId()).getCells());
		//we need to handle clicks on table with player field
		leftTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//we get column number
				int column = leftTable.columnAtPoint(e.getPoint());
				//and row number
				int row = leftTable.rowAtPoint(e.getPoint());
				System.out.println("Pressed: " + column + " " + row);
				gameStatusLabel.setText("Place " + (4 - playerField.getShips().size()) + " 1-cell ships on a field");
				if(playerField.getShips().size() < 3) {
					int result = playerField.putSmallShip(column-1, row-1);
					if(result == -1)
						gameStatusLabel.setText("Ship is out of field. Try again!");
					else if(result == 0)
						gameStatusLabel.setText("Ship overlays or touches other ship. Try again!");
					else{
						gameStatusLabel.setText("Place " + (4 - playerField.getShips().size()) + " 1-cell ships on a field");
					}
				}
				else if(playerField.getShips().size() == 3) {
					int result = playerField.putSmallShip(column-1, row-1);
					if(result == -1)
						gameStatusLabel.setText("Ship is out of field. Try again!");
					else if(result == 0)
						gameStatusLabel.setText("Ship overlays or touches other ship. Try again!");
					else{
						client.writeField(playerField);
						client.start();
						gameStatusLabel.setText("All ships placed");
					}
				}
				else {
					System.out.println("You've placed all your ships");
					
				}
				leftTable.refreshTable(playerField.getField());
				refreshShipsPanel(leftShipsPanel, playerField.getField());
			}
		});	
		
		JPanel right = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) right.getLayout();
		flowLayout_3.setVgap(0);
		right.setMinimumSize(dimension);
		frame.getContentPane().add(right, BorderLayout.EAST);		
		JPanel rightField = new JPanel();
		right.add(rightField);
		rightField.setLayout(new BorderLayout(0, 0));		
		rightTable = new BoardTable(opponentField.getField());
		rightField.add(rightTable, BorderLayout.CENTER);		
		JLabel player1 = new JLabel("Opponent Field");
		rightField.add(player1, BorderLayout.NORTH);
		player1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		player1.setHorizontalAlignment(SwingConstants.CENTER);		
		rightShipsPanel = new JPanel();
		((FlowLayout) rightShipsPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		rightField.add(rightShipsPanel, BorderLayout.SOUTH);		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		frame.getContentPane().add(rigidArea, BorderLayout.CENTER);
		//for correct display of enemy statistics, we need to get actual information about enemy cells
		//refreshShipsPanel(rightShipsPanel, currentGame.getEnemy().getField().getCells());
		//we need to handle clicks on table with enemy field
		rightTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//we get column number
				int column = rightTable.columnAtPoint(e.getPoint());
				//and row number
				int row = rightTable.rowAtPoint(e.getPoint());
				//we get cell from table by column and row number
				System.out.println("Shot: " + column + " " + row);
				if(yourShot) {
					if(column == 0 || row == 0)
						return;
					Shot shot = new Shot(column-1, row-1);
					client.writeShot(shot);
					yourShot = false;
				}
				//We pass game id and cell to make a shot and get the cell after shot
				//Cell updated = processor.shot(currentGame.getId(), target);
				//refresh table model to see result
				rightTable.refreshTable(opponentField.getField());
				refreshShipsPanel(rightShipsPanel, opponentField.getField());
			}
		});
		System.out.println("end frame");
	}
	
	public void refresh(){
		leftTable.refreshTable(playerField.getField());
		refreshShipsPanel(leftShipsPanel, playerField.getField());
		rightTable.refreshTable(opponentField.getField());
		refreshShipsPanel(rightShipsPanel, opponentField.getField());
	}

	private void refreshShipsPanel(JPanel shipPanel, char[][] cells) {
		shipPanel.removeAll();
		int playerLiveShips = 0;
		int playerKilledShips = 0;
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				if(cells[i][j] == 's') {
					playerLiveShips++;
				}
				if(cells[i][j] == '#') {
					playerKilledShips++;
				}
			}
		}
		for(int i = 0; i < playerKilledShips + playerLiveShips; i++) {
			JButton ship = new JButton("");
			if(i < playerLiveShips) {
				ship.setBackground(Color.YELLOW);
			} else {
				ship.setBackground(Color.RED);
			}
			ship.setHorizontalAlignment(SwingConstants.LEFT);
			ship.setForeground(new Color(255, 255, 0));
			ship.setPreferredSize(new Dimension(15, 15));
			shipPanel.add(ship);
		}
		frame.getContentPane().invalidate();
		frame.getContentPane().validate();
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
				frame.getContentPane().removeAll();
				buildBattlefield();
				frame.getContentPane().invalidate();
				frame.getContentPane().validate();
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

