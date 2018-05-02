package MultiplayerInterface;

import MultiplayerGame.*;

import javax.swing.*;
import javax.swing.table.TableColumn;


//this is a customized table
//we need it to display game field's content
public class BoardTable extends JTable {

	private static final long serialVersionUID = 8554409577436939213L;
	
	char[][] cells;

	public BoardTable(char[][] cells2) {
		this.cells = cells2;
		//we need this customized TableModel here to disable editing of table cell by double-click of mouse
		TableModel model = new TableModel(cells2.length + 1, cells2[0].length + 1);
		setModel(model);
		//in cycle we set the size of columns and add our customized BoardTableCellRenderer into each column
		for(int i = 0; i < cells2.length + 1; i++) {	
			TableColumn column = getColumnModel().getColumn(i);
			column.setCellRenderer(new BoardTableCellRenderer());
			column.setMinWidth(MainMulti.CELL_SIZE);
			column.setMaxWidth(MainMulti.CELL_SIZE);
			column.setPreferredWidth(MainMulti.CELL_SIZE);
		}
		//this method to set our Cell objects into each cell of table model
		refreshTable(cells2);
		//here we set rows height to make cells dimensions equals
		setRowHeight(MainMulti.CELL_SIZE);
	}

	//set our Cell objects into each cell of table model in cycle
	//later our Cell objects will be rendered into another place
	public void refreshTable(char[][] cells2) {
		for(int i = 0; i < cells2.length; i++) {
			for(int j = 0; j < cells2[i].length; j++) {
				this.getModel().setValueAt(cells2[i][j], i + 1, j + 1);
			}
		}
	}

}

