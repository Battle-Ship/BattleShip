package lv.odo.battleship.demo;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import lv.odo.battleship.Cell;
import lv.odo.battleship.Controller;
import lv.odo.battleship.Game;
import lv.odo.battleship.SingleGameControllerImpl;

public class BattleView extends JFrame {

	private JPanel contentPane;
	
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
					BattleView frame = new BattleView();
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
	public BattleView() {
		setBounds(100, 100, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//we set layout to place game objects on game window
		getContentPane().setLayout(new BorderLayout(0, 0));		
		buildBattlefield();
	}
	
	private void buildBattlefield() {			
		JPanel bottom = new JPanel();		
		getContentPane().add(bottom, BorderLayout.SOUTH);		
		Button buttonEnd = new Button("End game");
		bottom.add(buttonEnd);
		buttonEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("open battlefield");
				getContentPane().removeAll();
				//buildStartMenuWindow();
				getContentPane().invalidate();
				getContentPane().validate();
			}
		});		
		Dimension dimension = new Dimension(Main.CELL_SIZE * Main.FIELD_DIMENSION, Main.CELL_SIZE * Main.FIELD_DIMENSION);		
		JPanel left = new JPanel();		
		FlowLayout flowLayout_2 = (FlowLayout) left.getLayout();
		flowLayout_2.setVgap(0);
		left.setMinimumSize(dimension);
		getContentPane().add(left, BorderLayout.WEST);		
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
		getContentPane().add(right, BorderLayout.EAST);		
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
		getContentPane().add(rigidArea, BorderLayout.CENTER);
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

}
