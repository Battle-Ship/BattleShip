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

	@Override
	public String toString() {
		return "Field [cells=" + Arrays.toString(cells) + "]";
	}

	@Override
	protected Field clone() {
		Cell[][] cloned = new Cell[this.cells.length][this.cells[0].length];
		for(int i = 0; i < cloned.length; i++) {
			for(int j = 0; j < cloned[i].length; j++) {
				cloned[i][j] = this.cells[i][j].clone();
			}
		}
		Field clone = new Field(cloned);
		return clone;
	}
	
	

}

