package lv.odo.battleship.demo;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import lv.odo.battleship.Cell;

public class BoardTable extends JTable {
	
	private Cell[][] cells;
	
	DefaultTableModel model;
	
	public BoardTable(Cell[][] cells) {
		this.cells = cells;
		model = new DefaultTableModel(cells.length, cells[0].length);
		setModel(model);
		for(int i = 0; i < cells.length; i++) {	
			TableColumn column = getColumnModel().getColumn(i);
			column.setCellRenderer(new BoardTableCellRenderer());
			
			column.setMinWidth(Main.CELL_SIZE);
			column.setMaxWidth(Main.CELL_SIZE);
			column.setPreferredWidth(Main.CELL_SIZE);
			
			for(int j = 0; j < cells[i].length; j++) {
				this.getModel().setValueAt((cells[i][j]), i, j);				
			}
		}
		setRowHeight(Main.CELL_SIZE);		
	}
	
	@Override
	public DefaultTableModel getModel() {
		return model;
	}

}

