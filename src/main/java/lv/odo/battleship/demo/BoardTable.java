package lv.odo.battleship.demo;

import javax.swing.*;
import javax.swing.table.TableColumn;

import lv.odo.battleship.Cell;

//this is a customized table
//we need it to display game field's content
public class BoardTable extends JTable {

	Cell[][] cells;

	public BoardTable(Cell[][] cells) {
		this.cells = cells;
		//we need this customized TableModel here to disable editing of table cell by double-click of mouse
		TableModel model = new TableModel(cells.length + 1, cells[0].length + 1);
		setModel(model);
		//in cycle we set the size of columns and add our customized BoardTableCellRenderer into each column
		for(int i = 0; i < cells.length + 1; i++) {	
			TableColumn column = getColumnModel().getColumn(i);
			BoardTableCellRenderer renderer = new BoardTableCellRenderer();
			column.setCellRenderer(renderer);
			//centerRenderer
			column.setMinWidth(Main.CELL_SIZE);
			column.setMaxWidth(Main.CELL_SIZE);
			column.setPreferredWidth(Main.CELL_SIZE);
		}
		//this method to set our Cell objects into each cell of table model
		refreshTable(cells);
		//here we set rows height to make cells dimensions equals
		setRowHeight(Main.CELL_SIZE);
	}

	//set our Cell objects into each cell of table model in cycle
	//later our Cell objects will be rendered into another place
	public void refreshTable(Cell[][] cells) {
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				this.getModel().setValueAt(cells[i][j], i + 1, j + 1);
			}
		}
	}

}

