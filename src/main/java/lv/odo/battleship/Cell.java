package lv.odo.battleship;

public class Cell {
	
	private char status;
	
	private int x;
	
	private int y;

	public Cell(char status, int x, int y) {
		super();
		this.status = status;
		this.x = x;
		this.y = y;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Cell [status=" + status + ", x=" + x + ", y=" + y + "]";
	}

}