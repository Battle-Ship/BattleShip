package MultiplayerGame;

import java.io.Serializable;
import java.util.ArrayList;

public class Ship implements Serializable {

	private static final long serialVersionUID = -5582772825561917954L;

	private ArrayList<Cell> cells = new ArrayList<Cell>();
	int row, column, length, position;
	public boolean alive;

	Ship(int row, int column, int length, int position) {
		for (int i = 0; i < length; i++) {
			Cell cell = new Cell(column + i * ((position == 1) ? 0 : 1), row + i * ((position == 1) ? 1 : 0));
			// System.out.println("Creating: " + cell.toString());
			cells.add(cell);

		}
		this.row = row;
		this.column = column;
		this.length = length;
		this.position = position;
		alive = true;
	}
	
	Ship(int row, int column, int length) {
			Cell cell = new Cell(column , row);
			cells.add(cell);
		this.row = row;
		this.column = column;
		this.length = length;
		alive = true;
	}
	
	

	// is ship outside the boundary of the field?
	public boolean isOutOfField(int bottom, int top) {
		for (Cell cell : cells)
			if (cell.getRow() < bottom || cell.getRow() > top || cell.getColumn() < bottom || cell.getColumn() > top)
				return true;

		return false;
	}

	public boolean isOverlayOrTouch(Ship checkShip) { // is ship overlay or
														// touch other ships
		for (Cell cell : cells)
			if (checkShip.isOverlayOrTouchCell(cell)){
				System.out.println(cell.row + cell.column);
				return true;
			}
				
		return false;
	}

	boolean isOverlayOrTouchCell(Cell ctrlCell) {
		for (Cell cell : cells) {
			for (int dx = -1; dx < 2; dx++)
				for (int dy = -1; dy < 2; dy++)
					if (ctrlCell.getRow() == cell.getRow() + dx && ctrlCell.getColumn() == cell.getColumn() + dy)
						return true;
		}
		return false;
	}

	public int checkHit(Shot shot) {
		for (Cell cell : cells) {
			if (cell.checkHit(shot.getColumn(), shot.getRow()) && cell.isAlive()) {
				cell.destroy();
				if (!hasHealth()) {
					destroy();
					return cells.size();

				}
				return -2;
			} else if (cell.checkHit(shot.getColumn(), shot.getRow()) && !cell.isAlive()) {
				return -1;
			}
		}
		return 0;
	}

	public boolean isAlive() {
		return alive;
	}

	public String toString() {
		return "row: " + Integer.toString(row) + " column: " + Integer.toString(column) + " len: "
				+ Integer.toString(length) + " pos: " + Integer.toString(position);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getLength() {
		return length;
	}

	public int getPosition() {
		return position;
	}

	public void destroy() {
		alive = false;
	}

	public boolean hasHealth() {
		for (Cell cell : cells) {
			if (cell.isAlive())
				return true;
		}
		return false;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

}
