package lv.odo.battleship.demo;

import java.awt.EventQueue;
import javax.swing.JFrame;

import lv.odo.battleship.Cell;
import lv.odo.battleship.Game;
import lv.odo.battleship.GameControllerImpl;
import lv.odo.battleship.Controller;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	private JFrame frame;
	private BoardTable leftTable;
	private BoardTable rightTable;

	private Game currentGame;
	
	public static Controller processor = new GameControllerImpl();

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
		
		JPanel top = new JPanel();
		frame.getContentPane().add(top, BorderLayout.NORTH);
		
		Dimension buttonDimension = new Dimension(CELL_SIZE, CELL_SIZE * 2);
		
		JButton startButton = new JButton("Start");		
		startButton.setMinimumSize(buttonDimension);
		top.add(startButton);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processor.playGame(currentGame.getId(), currentGame.getMe());
			}
		});
		
		JButton capitulationButton = new JButton("Capitulation");
		capitulationButton.setMinimumSize(buttonDimension);
		
		top.add(capitulationButton);
		
		JPanel bottom = new JPanel();		
		frame.getContentPane().add(bottom, BorderLayout.SOUTH);
		
		Dimension dimension = new Dimension(CELL_SIZE * FIELD_DIMENSION, CELL_SIZE * FIELD_DIMENSION);
		
		JPanel left = new JPanel();		
		left.setMinimumSize(dimension);
		frame.getContentPane().add(left, BorderLayout.WEST);
		
		leftTable = new BoardTable(processor.getMyField(0).getCells());
		leftTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int column = leftTable.columnAtPoint(e.getPoint());
				int row = leftTable.rowAtPoint(e.getPoint());
				int result = processor.placeShipInCell(33, (Cell) leftTable.getValueAt(row, column));
				if (result == 0) {
					//currentGame.getMe().getField().getCell(row - 1, column - 1).setStatus('s');
				}
				leftTable.refreshTable();
			}
		});
		left.add(leftTable);
		
		JPanel right = new JPanel();
		right.setMinimumSize(dimension);
		frame.getContentPane().add(right, BorderLayout.EAST);
		
		rightTable = new BoardTable(processor.getEnemyField(0).getCells());
		rightTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int column = rightTable.columnAtPoint(e.getPoint());
				int row = rightTable.rowAtPoint(e.getPoint());
				Cell updated = processor.shot(33, (Cell) rightTable.getValueAt(row, column));
				//currentGame.getEnemy().getField().getCell(row - 1, column - 1).setStatus(updated.getStatus());
				rightTable.refreshTable();
			}
		});
		right.add(rightTable);
	}
}

