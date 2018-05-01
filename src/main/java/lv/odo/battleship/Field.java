package lv.odo.battleship;

import java.util.Arrays;

public class Field {
	
	private Cell[][] cells;

	public Field(Cell[][] cells) {
		super();
		this.cells = cells;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public Cell getCell(int x, int y) {
		return cells[x][y];
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	public String toString() {
		final StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < cells.length; i++) {
			sb.append(Arrays.toString(cells[i]));
			sb.append("\n");
		}
		sb.append(']');
		return sb.toString();
	}

	@Override
	public Field clone() {
		Cell[][] cloned = new Cell[this.cells.length][this.cells[0].length];
		for(int i = 0; i < cloned.length; i++) {
			for(int j = 0; j < cloned[i].length; j++) {
				cloned[i][j] = new Cell(this.cells[i][j].getStatus(), this.cells[i][j].getX(), this.cells[i][j].getY());
			}
		}
		return new Field(cloned);
	}
	
	

}

