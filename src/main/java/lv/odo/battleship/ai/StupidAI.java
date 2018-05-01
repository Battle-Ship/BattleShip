package lv.odo.battleship.ai;

import lv.odo.battleship.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StupidAI implements ComputerAI {

    //method scans all cells and adds unknown cells into list of possible targets,
    //after that returns random cell from list
    //if there are no unknown cells into list throws GameOverException
    public Cell selectNextTarget(Cell[][] cells) throws GameOverException {
        List<Cell> possibleTargets = new ArrayList<Cell>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = cells[i][j];
                if (cell.getStatus() != '*' && cell.getStatus() != '.' && cell.getStatus() != 'x') {
                    possibleTargets.add(cell);
                }
            }
        }
        if (possibleTargets.size() > 0) {
            Random r = new Random();
            return possibleTargets.get(r.nextInt(possibleTargets.size()));
        } else {
            throw new GameOverException();
        }
    }
}
