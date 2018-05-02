package MultiplayerGame;

import java.io.Serializable;

public class Shot implements Serializable {

	private static final long serialVersionUID = -4278076903136319271L;

	int column, row;

	public Shot(int row, int column) {
		this.column = column;
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public String toString() {
		return "Column: " + column + " Row: " + row;
	}
}
