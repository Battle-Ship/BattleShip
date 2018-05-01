package lv.odo.battleship.ai;

import lv.odo.battleship.Cell;

public interface ComputerAI {

    public Cell selectNextTarget(Cell[][] cells) throws GameOverException;

}
