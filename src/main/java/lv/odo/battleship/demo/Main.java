package lv.odo.battleship.demo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import lv.odo.battleship.GameControllerImpl;
import lv.odo.battleship.Controller;

import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.UIManager;

public class Main {
	
	//сначала определимся с габаритами нашего игрового окна
	//пусть размер ячейчки будет 30 единиц
	public static final int CELL_SIZE = 30;
	
	//количество ячеек в игровом поле 10*10, значит одно измерение равно 10
	public static final int FIELD_DIMENSION = 10;
	
	//ширина окна такая, чтобы влезало два поля и было место для небольшого зазора
	public static final int WINDOW_WIDTH = CELL_SIZE * 2 * FIELD_DIMENSION + CELL_SIZE * 2;
	
	//высота окна такая, чтобы влезало поле и было место для кнопок и расстояние снизу
	public static final int WINDOW_HEIGHT = CELL_SIZE * FIELD_DIMENSION + CELL_SIZE * 5;

	private JFrame frame;
	private JTable leftTable;
	private JTable rightTable;
	
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
		initialize();
	}

	/**
	* Initialize the contents of the frame.
	*/
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 255));
		frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel top = new JPanel();
		top.setBackground(new Color(0, 0, 255));
		frame.getContentPane().add(top, BorderLayout.NORTH);
		
		Dimension buttonDimention = new Dimension(CELL_SIZE, CELL_SIZE * 2);
		
		JButton startButton = new JButton("Start");		
		startButton.setBackground(Color.GREEN);
		startButton.setMinimumSize(buttonDimention);
		top.add(startButton);
		
		JButton capitulationButton = new JButton("Capitulation");
		capitulationButton.setBackground(new Color(255, 140, 0));
		capitulationButton.setMinimumSize(buttonDimention);
		
		top.add(capitulationButton);
		
		JPanel bottom = new JPanel();		
		frame.getContentPane().add(bottom, BorderLayout.SOUTH);
		
		Dimension fieldDimention = new Dimension(CELL_SIZE * FIELD_DIMENSION, CELL_SIZE * FIELD_DIMENSION);
		
		JPanel left = new JPanel();		
		left.setBackground(UIManager.getColor("OptionPane.warningDialog.titlePane.background"));
		left.setMinimumSize(fieldDimention);
		frame.getContentPane().add(left, BorderLayout.WEST);
		
		leftTable = new BoardTable(processor.getMyField(0).getCells());
		leftTable.setBackground(new Color(173, 216, 230));
		left.add(leftTable);
		
		JPanel right = new JPanel();
		right.setBackground(new Color(176, 224, 230));
		right.setMinimumSize(fieldDimention);
		frame.getContentPane().add(right, BorderLayout.EAST);
		
		rightTable = new BoardTable(processor.getEnemyField(0).getCells());
		right.add(rightTable);
	}
}

