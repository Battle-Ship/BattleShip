package lv.odo.battleship.demo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import lv.odo.battleship.Cell;

public class BoardTableCellRenderer implements TableCellRenderer {	

	static String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

	public BoardTableCellRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
												   int row, int column) {
		if (row == 0 && column == 0) {
			return new JLabel();
		}
		if (row == 0 && column > 0) {
			return new JLabel(columns[column - 1]);
		} 
		if (column == 0 && row > 0) {
			return new JLabel(Integer.toString(row));
		}
		return buildCellButton((Cell) value);

	}

	private Component buildCellButton(final Cell cell) {
			switch (cell.getStatus()) {
				case '*': {
					ImageIcon buttonIcon = new ImageIcon ("images/miss.png");
					JButton button = new JButton();
					button.setIcon(buttonIcon);
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