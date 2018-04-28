package lv.odo.battleship.demo;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;

import lv.odo.battleship.Cell;
import lv.odo.battleship.Game;
import lv.odo.battleship.SingleGameControllerImpl;
import lv.odo.battleship.Controller;
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
import javax.swing.Box;
import java.awt.Component;
import java.awt.Button;

public class Main {
	
	//сначала определимся с габаритами нашего игрового окна
	//пусть размер ячейчки будет 30 единиц
	public static final int CELL_SIZE = 30;
	
	//количество ячеек в игровом поле 10*10, значит одно измерение равно 10
	public static final int FIELD_DIMENSION = 10;
	
	//ширина окна такая, чтобы влезало два поля и было место для небольшого зазора
	public static final int WINDOW_WIDTH = CELL_SIZE * 2 * FIELD_DIMENSION + CELL_SIZE * 4;
	
	//высота окна такая, чтобы влезало поле и было место для кнопок и расстояние снизу
	public static final int WINDOW_HEIGHT = CELL_SIZE * FIELD_DIMENSION + CELL_SIZE * 5;
	
	//maximal number of ships  
	public static final int SHIPS = 5;

	private JFrame frame;
	private BoardTable leftTable;
	private BoardTable rightTable;
	private JPanel leftShipsPanel;

	private Game currentGame;
	
	public static Controller processor = new SingleGameControllerImpl();

	/**
	* Launch the application.
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
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
	public Main() {
		currentGame = processor.getGames().get(0);
		initialize();
	}

	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//we set layout to place game objects on game window
		frame.getContentPane().setLayout(new BorderLayout(0, 0));		
		buildBattlefield();
	}

	private void buildBattlefield() {			
		JPanel bottom = new JPanel();		
		frame.getContentPane().add(bottom, BorderLayout.SOUTH);		
		Button buttonEnd = new Button("End game");
		bottom.add(buttonEnd);
		buttonEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("open battlefield");
				frame.getContentPane().removeAll();
				buildStartMenuWindow();
				frame.getContentPane().invalidate();
				frame.getContentPane().validate();
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
		leftTable = new BoardTable(processor.getMyField(0).getCells());
		leftField.add(leftTable, BorderLayout.CENTER);		
		JLabel player0 = new JLabel("Player");
		leftField.add(player0, BorderLayout.NORTH);
		player0.setFont(new Font("Tahoma", Font.PLAIN, 22));
		player0.setHorizontalAlignment(SwingConstants.CENTER);		
		leftShipsPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) leftShipsPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		leftField.add(leftShipsPanel, BorderLayout.SOUTH);		
		addShipOnPlayerPanel();
		//we need to handle clicks on table with player field
		leftTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//we get column number
				int column = leftTable.columnAtPoint(e.getPoint());
				//and row number
				int row = leftTable.rowAtPoint(e.getPoint());
				//we get cell from table by column and row number
				Cell target = (Cell) leftTable.getValueAt(row, column);
				//We pass game id and cell to place our ship and get the result
				int result = processor.placeShipInCell(currentGame.getId(), target);
				leftTable.refreshTable(processor.getMyField(currentGame.getId()).getCells());
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
		rightTable = new BoardTable(processor.getEnemyField(0).getCells());
		rightField.add(rightTable, BorderLayout.CENTER);		
		JLabel player1 = new JLabel("Computer");
		rightField.add(player1, BorderLayout.NORTH);
		player1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		player1.setHorizontalAlignment(SwingConstants.CENTER);		
		JPanel rightShipsPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) rightShipsPanel.getLayout();
		rightField.add(rightShipsPanel, BorderLayout.SOUTH);		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		frame.getContentPane().add(rigidArea, BorderLayout.CENTER);
		//we need to handle clicks on table with enemy field
		rightTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//we get column number
				int column = rightTable.columnAtPoint(e.getPoint());
				//and row number
				int row = rightTable.rowAtPoint(e.getPoint());
				//we get cell from table by column and row number
				Cell target = (Cell) rightTable.getValueAt(row, column);
				//We pass game id and cell to make a shot and get the cell after shot
				Cell updated = processor.shot(currentGame.getId(), target);
				//refresh table model to see result
				rightTable.refreshTable(processor.getEnemyField(currentGame.getId()).getCells());
			}
		});
	}

	private void addShipOnPlayerPanel() {
		JButton ship = new JButton("");
		ship.setBackground(Color.YELLOW);
		ship.setHorizontalAlignment(SwingConstants.LEFT);
		ship.setForeground(new Color(255, 255, 0));
		ship.setPreferredSize(new Dimension(15, 15));
		leftShipsPanel.add(ship);
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

