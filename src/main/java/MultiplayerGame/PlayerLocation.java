package MultiplayerGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import MultiplayerInterface.MainMulti;

public class PlayerLocation extends Field implements Serializable {

	private static final long serialVersionUID = 5456343159124430963L;

	private final int[] PATTERN = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 }; // pattern
																	// for ships
	// private final int[] PATTERN = {2, 1}; // pattern for ships
	private ArrayList<Ship> ships = new ArrayList<Ship>();

	public PlayerLocation() {
		super();
		//setShips();
	}

	// Generates locations for ships from user input
	public void setShips() {
		System.out.println("Locate on the field " + PATTERN.length + " ships");
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		showField();
		Ship ship;
		// int column, row, position
		String stringColumn, stringRow, stringPosition;
		for (int size : PATTERN) {
			do {
				System.out.printf("Locate on the field ship with size %d.\n", size);
				System.out.print("Column: ");
				stringColumn = input.nextLine();

				int column = stringToNumber(stringColumn, "Column") - 1;

				while (!(column < 10 && column > -1)) {
					System.out.println("Wrong Column");
					System.out.println("Choose Column from 1 to 10.");
					System.out.print("Column: ");
					stringColumn = input.nextLine();

					column = stringToNumber(stringColumn, "Column") - 1;
				}

				System.out.print("Row: ");
				stringRow = input.nextLine();
				int row = stringToNumber(stringRow, "Row") - 1;
				while (!(row < 10 && row > -1)) {
					System.out.println("Wrong Row");
					System.out.println("Choose Row from 1 to 10.");
					System.out.print("Row: ");
					stringRow = input.nextLine();

					row = stringToNumber(stringRow, "Row") - 1;
				}

				System.out.print("Position (0 - horizontal, 1 - vertical): ");
				stringPosition = input.nextLine();
				int position = stringToNumber(stringPosition, "Position: ");
				while (position != 0 && position != 1) {
					System.out.println("Wrong position");
					System.out.print("Choose the correct position (0 - horizontal, 1 - vertical): ");
					stringPosition = input.nextLine();

					position = stringToNumber(stringPosition, "Position: ");
				}

				ship = new Ship(row, column, size, position);
				if (ship.isOutOfField(0, 9))
					System.out.println("Ship is out of field");
				else if (isOverlayOrTouch(ship))
					System.out.println("Ship overlays or touches other ship");

			} while (ship.isOutOfField(0, 9) || this.isOverlayOrTouch(ship));

			ships.add(ship);
			putShip(ship);
			showField();
		}
	}

	public boolean isNumber(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int stringToNumber(String s, String name) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		while (!isNumber(s)) {
			System.out.println("Enter a number");
			System.out.print(name + ": ");
			s = input.nextLine();
		}

		int column = Integer.parseInt(s);
		return column;
	}

	boolean isOverlayOrTouch(Ship ctrlShip) {
		System.out.println("size: " + ships.size());
		for (Ship ship : ships)
			if (ship.isOverlayOrTouch(ctrlShip))
				return true;
		return false;
	}
	
	public int putSmallShip(int column, int row) {
		Ship ship = new Ship(column, row, 1);
		if (ship.isOutOfField(0, 9)) {
			System.out.println("Ship is out of field");
			return -1;
		}	
		if (isOverlayOrTouch(ship)) {
			System.out.println("Ship overlays or touches other ship");
			return 0;
		}
		
			ships.add(ship);
			field[row][column] = 's';
			return 1;
		
		
	}

	// Put ship in appropriate place
	public void putShip(Ship ship) {
		int shipX = ship.getRow();
		int shipY = ship.getColumn();
		int length = ship.getLength();
		int positionY = (ship.getPosition() == 1) ? 0 : 1;
		int positionX = ship.getPosition();
		for (int i = 0; i < length; i++)
			field[shipX + i * positionX][shipY + i * positionY] = 's';
	}

	// Mark the cell in a field with a hit-mark
	public void hitMark(Shot shot) {
		field[shot.getRow()][shot.getColumn()] = 'X';
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public boolean hasShips() {
		for (Ship ship : ships) {
			if (ship.isAlive())
				return true;
		}
		return false;
	}

}
