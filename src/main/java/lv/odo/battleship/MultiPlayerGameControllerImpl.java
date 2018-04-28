package lv.odo.battleship;

import java.util.List;

public class MultiPlayerGameControllerImpl implements Controller {

    public List<Game> getGames() {
        return null;
    }

    public Game createGame(Player me) {
        return null;
    }

    public int playGame(int gameId, Player me) {
        return 0;
    }

    public int placeShipInCell(int gameId, Cell cell) {
        return 0;
    }

    public Field getEnemyField(int gameId) {
        return null;
    }

    public Field getMyField(int gameId) {
        return null;
    }

    public Cell shot(int gameId, Cell cell) {
        return null;
    }
}
