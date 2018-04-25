package lv.odo.battleship.demo;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
	
	public TableModel(int i, int j) {
		super(i, j);
	}

	public boolean isCellEditable(int row,int cols) {
		return false;
	}
	
}
