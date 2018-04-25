package lv.odo.battleship.demo;

import javax.swing.*;
import javax.swing.table.TableColumn;

import lv.odo.battleship.Cell;

public class BoardTable extends JTable {

	Cell[][] cells;

	public BoardTable(Cell[][] cells) {
		this.cells = cells;
		TableModel model = new TableModel(cells.length + 1, cells[0].length + 1);
		setModel(model);
		for(int i = 0; i < cells.length + 1; i++) {	
			TableColumn column = getColumnModel().getColumn(i);
			column.setCellRenderer(new BoardTableCellRenderer());
			column.setMinWidth(Main.CELL_SIZE);
			column.setMaxWidth(Main.CELL_SIZE);
			column.setPreferredWidth(Main.CELL_SIZE);
		}
		refreshTable();
		setRowHeight(Main.CELL_SIZE);
		setCellSelectionEnabled(true);
		ListSelectionModel cellSelectionModel = getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void refreshTable() {
		for(int i = 0; i < cells.length; i++) {		
			for(int j = 0; j < cells[i].length; j++) {
				this.getModel().setValueAt(cells[i][j], i + 1, j + 1);
			}
		}
	}

}

