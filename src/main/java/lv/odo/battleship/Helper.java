package lv.odo.battleship;

import lv.odo.battleship.demo.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Helper {

    private static List<Cell> getSingleShipsCoordinates(Cell[][] cells) {
        List<Cell> ships = new ArrayList<Cell>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].isShip() && !hasNeighborShipCell(cells[i][j], cells)) {
                    ships.add(cells[i][j]);
                    cells[i][j].setStatus('~');
                }
            }
        }
        return ships;
    }

    public static List<List<Cell>> processFleet(Field field) {
        List<List<Cell>> fleet = new ArrayList<List<Cell>>();
        Field processed = field.clone();
        Cell[][] cells = processed.getCells();
        List<Cell> singleShips = getSingleShipsCoordinates(cells);
        for(int i = 0; i < singleShips.size(); i++) {
            List<Cell> singleShip = new ArrayList<Cell>();
            singleShip.add(singleShips.get(i));
            fleet.add(singleShip);
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = cells[i][j];
                if(hasNeighborShipCell(cell, cells) && cell.isShip()) {
                    List<Cell> ship = new ArrayList<Cell>();
                    fleet.add(findShipCells(cell, ship, cells));
                }
            }
        }
        Collections.sort(fleet, new Comparator<List<Cell>>() {
            public int compare(List<Cell> o1, List<Cell> o2) {
                return o2.size() - o1.size();
            }
        });
        Field notProcessed = field.clone();
        cells = notProcessed.getCells();
        List<List<Cell>> found = new ArrayList<List<Cell>>();
        for(int i = 0; i < fleet.size(); i++) {
            found.add(new ArrayList<Cell>());
            for (int j = 0; j < fleet.get(i).size(); j++) {
                Cell content = cells[fleet.get(i).get(j).getX()][fleet.get(i).get(j).getY()];
                found.get(i).add(content);
            }
        }
        return found;
    }

    public static Set<Cell> findWholeShip(Cell cell, Set<Cell> ship, Cell[][] cells) {
        while (hasNeighborShipCell(cell, cells)) {
            Cell neighbor = getNeighborShipCell(cell, cells);
            neighbor.setStatus('~');
            if (!ship.contains(neighbor)) {
                ship.add(neighbor);
            }
            findWholeShip(neighbor, ship, cells);
        }
        cell.setStatus('~');
        if (!ship.contains(cell)) {
            ship.add(cell);
        }

        return ship;
    }

    public static List<Cell> findShipCells(Cell cell, List<Cell> ship, Cell[][] cells) {
        if(hasNeighborShipCell(cell, cells)) {
            ship.add(cell);
            cell.setStatus('~');
            findShipCells(getNeighborShipCell(cell, cells), ship, cells);
        } else {
            ship.add(cell);
            cell.setStatus('~');
        }
        return ship;
    }

    static boolean hasNeighborShipCell(Cell cell, Cell[][] cells) {
        int maxX = cells.length;
        int maxY = cells[cell.getX()].length;
        int x = cell.getX();
        int y = cell.getY();
        if(x + 1 < maxX) {
            if(cells[x + 1][y].isShip()) {
                return true;
            }
        }
        if(y + 1 < maxY) {
            if(cells[x][y + 1].isShip()) {
                return true;
            }
        }
        if(x - 1 >= 0) {
            if(cells[x - 1][y].isShip()) {
                return true;
            }
        }
        if(y - 1 >= 0) {
            if(cells[x][y - 1].isShip()) {
                return true;
            }
        }
        return false;
    }

    //returns one neighbor ship's cell
    private static Cell getNeighborShipCell(Cell cell, Cell[][] cells) {
        int maxX = cells.length;
        int maxY = cells[cell.getX()].length;
        int x = cell.getX();
        int y = cell.getY();
        if(x + 1 < maxX) {
            if(cells[x + 1][y].isShip()) {
                return cells[x + 1][y];
            }
        }
        if(y + 1 < maxY) {
            if(cells[x][y + 1].isShip()) {
                return cells[x][y + 1];
            }
        }
        if(x - 1 >= 0) {
            if(cells[x - 1][y].isShip()) {
                return cells[x - 1][y];
            }
        }
        if(y - 1 >= 0) {
            if(cells[x][y - 1].isShip()) {
                return cells[x][y - 1];
            }
        }
        return null;
    }

    private static boolean canBePlaced(Cell[][] cells, Cell begin, Cell end) {
        for(int i = begin.getX(); i <= end.getX(); i++) {
            for(int j = begin.getY(); j <= end.getY(); j++) {
                if (cells[i][j].isShip() || Helper.hasContactWithOtherShip(cells[i][j], cells)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasContactWithOtherShip(Cell cell, Cell[][] cells) {
        if(hasNeighborShipCell(cell, cells)) {
            return true;
        }
        int maxX = cells.length;
        int maxY = cells[cell.getX()].length;
        int x = cell.getX();
        int y = cell.getY();
        if(x + 1 < maxX && y + 1 < maxY) {
            if(cells[x + 1][y + 1].isShip()) {
                return true;
            }
        }
        if(y + 1 < maxY && x - 1 >= 0) {
            if(cells[x - 1][y + 1].isShip()) {
                return true;
            }
        }
        if(x - 1 >= 0 && y - 1 >= 0) {
            if(cells[x - 1][y - 1].isShip()) {
                return true;
            }
        }
        if(y - 1 >= 0 && x + 1 < maxX) {
            if(cells[x + 1][y - 1].isShip()) {
                return true;
            }
        }
        return false;
    }

    public static Cell[][] generateRandomField() {
        int watchDog = 0;
        final int maxNumberOfTries = 1000;
        Cell[][] cells = getEmptyField();
        while(true) {
            int[] fleet = Main.POSSIBLE_FLEET;
            for(int i = 0; i < fleet.length; i++) {
                int length = fleet[i];
                for (int j = 0; j < i + 1; j++) {
                    while (watchDog < maxNumberOfTries) {
                        watchDog++;
                        Random r = new Random();
                        boolean rotate = r.nextBoolean();
                        int x = r.nextInt(Main.FIELD_DIMENSION);
                        int y = r.nextInt(Main.FIELD_DIMENSION);
                        if (rotate && x + length < Main.FIELD_DIMENSION && (canBePlaced(cells, cells[x][y], cells[x + length][y]))) {
                            for (int k = x; k < x + length; k++) {
                                cells[k][y].setStatus('s');
                            }
                            break;
                        }
                        if (!rotate && y + length < Main.FIELD_DIMENSION && (canBePlaced(cells, cells[x][y], cells[x][y + length]))) {
                            for (int k = y; k < y + length; k++) {
                                cells[x][k].setStatus('s');
                            }
                            break;
                        }
                    }
                }
            }
            if (watchDog >= maxNumberOfTries) {
                watchDog = 0;
                cells = getEmptyField();
            } else {
                break;
            }
        }
        return cells;
    }

    public static Cell[][] getEmptyField() {
        Cell[][] cells = new Cell[Main.FIELD_DIMENSION][Main.FIELD_DIMENSION];
        for (int i = 0; i < Main.FIELD_DIMENSION; i++) {
            for (int j = 0; j < Main.FIELD_DIMENSION; j++) {
                Cell cell = new Cell('~', i, j);
                cells[i][j] = cell;
            }
        }
        return cells;
    }


}
