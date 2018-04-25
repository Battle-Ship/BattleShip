package lv.odo.battleship.demo;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import lv.odo.battleship.Cell;

public class BoardTableCellRenderer implements TableCellRenderer {	

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		Cell cell = (Cell) value;
		return new JButton(cell.getStatus() + "");
	}

}