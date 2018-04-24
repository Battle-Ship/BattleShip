/**
 * 
 */
package lv.odo.battleship;



public class Field {
	
	private Cell cell;
	private Cell[][] cells;
	

	
	public Field() {
		cells = new Cell[10][10];
		cell = new Cell();
	}

}
