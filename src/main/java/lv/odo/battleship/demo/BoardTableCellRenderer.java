package lv.odo.battleship.demo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import lv.odo.battleship.Cell;

//Standard JTable contains interface TableCellRenderer class to render table cells by default
//We have to implement it to render our cells as we need
public class BoardTableCellRenderer implements TableCellRenderer {	

	//here are the name of columns
	static String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

	public BoardTableCellRenderer() {
	}

	//The table model calls this method every time when it builds cell
	//Here we decide how to display them
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
												   int row, int column) {
		//the first element of table is empty
		if (row == 0 && column == 0) {
			return new JLabel();
		}
		//all elements in first row, excluding first column, must be a letters from array of letters
		if (row == 0 && column > 0) {
			return new JLabel(columns[column - 1]);
		}
		//all elements in first column, excluding first row, must be a numbers equals to row number
		if (column == 0 && row > 0) {
			return new JLabel(Integer.toString(row));
		}
		//other cells of table must contain Cell objects, we set them before
		//and we handle them in that method:
		return buildCellButton((Cell) value);
	}

	//in that method we decide how to display Cell objects on the table
	private Component buildCellButton(final Cell cell) {
			switch (cell.getStatus()) {
				case '*': {
					ImageIcon buttonIcon = new ImageIcon ("images/miss.png");
					JButton button = new JButton();
					button.setIcon(buttonIcon);
					button.setBackground(Color.BLUE);
					return button;
				}
				case 'x': {
					ImageIcon buttonIcon = new ImageIcon ("images/hit.png");
					JButton button = new JButton();
					button.setIcon(buttonIcon);
					return button;
				}
				case '.': {
					JButton button = new JButton();
					button.setBackground(Color.RED);
					return button;
				}
				case 's': {
					JButton button = new JButton();
					button.setBackground(Color.YELLOW);
					return button;
				}
				default: {
					JButton button = new JButton();
					button.setBackground(Color.BLUE);
					return button;
				}
			}
	}

}