package MultiplayerGame;

import java.io.Serializable;

public class Field implements Serializable {

	private static final long serialVersionUID = 8760903066930786698L;

	protected char[][] field;

	public Field() {
		// TODO Auto-generated constructor stub
		field = new char[10][10];
		setField();
	}

	// Fills the field with '0'
	public void setField() {
		for (int row = 0; row < field.length; row++) {
			for (int column = 0; column < field.length; column++) {
				field[row][column] = '0';
			}
		}
	}

	public String arrayToString() {
		String s = "";
		for (int i = 0; i < 10; i++)
			for (int k = 0; k < 10; k++)
				s = s + field[i][k] + ", ";
		return s;
	}

	// Mark the cell in a field with a hit-mark
	public void hitMark(Shot shot) {
		field[shot.getColumn()][shot.getRow()] = 'X';
	}

	// Mark the cell in a field with a miss-mark
	public void missMark(Shot shot) {
		field[shot.getColumn()][shot.getRow()] = '*';
	}

	public void destroyMark(Shot shot) {
		field[shot.getColumn()][shot.getRow()] = '#';
		for (int i = -1; i < 2; i++) {
			if (i == 0)
				continue;
			if ((shot.getRow() + i) >= 0 && (shot.getRow() + i) < 10) {
				if (field[shot.getRow() + i][shot.getColumn()] == 'X') {
					Shot newShot = new Shot(shot.getColumn(), shot.getRow() + i);
					destroyMark(newShot);
				}
			}
			if ((shot.getColumn() + i) >= 0 && (shot.getColumn() + i) < 10) {
				if (field[shot.getRow()][shot.getColumn() + i] == 'X') {
					Shot newShot = new Shot(shot.getColumn() + 1, shot.getRow());
					destroyMark(newShot);
				}
			}
		}
	}

	// Prints the field
	public void showField() {

		System.out.print("   | ");

		String[] abc = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		for (int i = 0; i < field.length; i++) {
			System.out.print(abc[i] + " | ");
		}
		System.out.println();
		System.out.println("--------------------------------------------");

		int number = 1;
		for (char[] row : field) {
			if (number < 10) {
				System.out.print(number + " ");
			} else {
				System.out.print(number + "");

			}

			for (char cell : row) {
				System.out.print(" | " + cell);
			}
			System.out.print(" |");

			System.out.println();
			System.out.println("--------------------------------------------");
			number++;
		}
	}

	public char[][] getField() {
		return field;
	}
}
