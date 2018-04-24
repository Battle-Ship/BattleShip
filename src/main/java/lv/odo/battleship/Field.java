package lv.odo.battleship;

import java.util.Arrays;

public class Field {
	
	private Cell[][] cells;

	public Field(Cell[][] cells) {
		super();
		this.cells = cells;
	}

	public Field() {
		cells = new Cell[10][10];
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	@Override
	public String toString() {
		return "Field [cells=" + Arrays.toString(cells) + "]";
	}	

}

