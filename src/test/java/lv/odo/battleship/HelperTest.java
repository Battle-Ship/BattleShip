package lv.odo.battleship;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HelperTest {

    @Test
    public void processFleet() throws Exception {
        Field field = getExampleField();
        List<List<Cell>> fleet = Helper.processFleet(field);
        assertTrue(10 == fleet.size());
    }

    @Test
    public void processShip() throws Exception {
        Field field = getExampleField();
        List<Cell> ship = new ArrayList<Cell>();
        ship = Helper.findShipCells(field.getCells()[1][2], ship, field.getCells());
        assertTrue(4 == ship.size());
        assertEquals(field.getCells()[1][2], ship.get(0));
        assertEquals(field.getCells()[1][3], ship.get(1));
        assertEquals(field.getCells()[1][4], ship.get(2));
        assertEquals(field.getCells()[1][5], ship.get(3));
    }

    @Test
    public void getNeighbor() throws Exception {
    }

    @Test
    public void hasNeighbors() throws Exception {
        Cell[][] cells = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell('~', i, j);
            }
        }
        cells[5][5] = new Cell('s', 5, 5);
        assertFalse(Helper.hasNeighborShipCell(cells[5][5], cells));
        cells[5][6] = new Cell('s', 5, 6);
        assertTrue(Helper.hasNeighborShipCell(cells[5][5], cells));
        assertTrue(Helper.hasNeighborShipCell(cells[5][6], cells));
        cells[0][0] = new Cell('s', 0, 0);
        assertFalse(Helper.hasNeighborShipCell(cells[0][0], cells));
        cells[0][1] = new Cell('s', 0, 1);
        assertTrue(Helper.hasNeighborShipCell(cells[0][0], cells));
        assertTrue(Helper.hasNeighborShipCell(cells[0][1], cells));
        cells[9][5] = new Cell('s', 9, 5);
        assertFalse(Helper.hasNeighborShipCell(cells[9][5], cells));
        cells[9][4] = new Cell('s', 9, 4);
        assertTrue(Helper.hasNeighborShipCell(cells[9][5], cells));
        assertTrue(Helper.hasNeighborShipCell(cells[9][4], cells));
    }

    private static Field getExampleField() {
        Cell[][] cells = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell('~', i, j);
            }
        }
        cells[1][2] = new Cell('s', 1, 2);
        cells[1][3] = new Cell('s', 1, 3);
        cells[1][4] = new Cell('s', 1, 4);
        cells[1][5] = new Cell('s', 1, 5);

        cells[5][7] = new Cell('s', 5, 7);
        cells[6][7] = new Cell('s', 6, 7);
        cells[7][7] = new Cell('s', 7, 7);

        cells[7][0] = new Cell('s', 7, 0);
        cells[7][1] = new Cell('s', 7, 1);
        cells[7][2] = new Cell('s', 7, 2);

        cells[8][4] = new Cell('s', 8, 4);
        cells[8][5] = new Cell('s', 8, 5);

        cells[3][5] = new Cell('s', 3, 5);
        cells[3][6] = new Cell('s', 3, 6);

        cells[5][2] = new Cell('s', 5, 2);
        cells[5][3] = new Cell('s', 5, 3);

        cells[9][8] = new Cell('s', 9, 8);

        cells[3][3] = new Cell('s', 3, 3);

        cells[5][9] = new Cell('s', 5, 9);

        cells[0][0] = new Cell('s', 0, 0);
        Field field = new Field(cells);
        return field;
    }

}