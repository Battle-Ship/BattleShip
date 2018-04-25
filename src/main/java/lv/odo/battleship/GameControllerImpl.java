package lv.odo.battleship;

import java.util.ArrayList;
import java.util.List;

import lv.odo.battleship.Cell;
import lv.odo.battleship.Field;
import lv.odo.battleship.Game;
import lv.odo.battleship.Player;


public class GameControllerImpl implements Controller {

	public List<Game> getGames() {
		
		List<Game> games = new ArrayList<Game>();
		games.add(createGame(new Player("me", getMyField(0))));
		System.out.println("get games " + games);
		return games;
	}

	public Game createGame(Player me) {
		
		Game game = new Game();
		game.setId(4);
		Player player = new Player();
		player.setName("My Name");
		player.setField(getMyField(0));
		game.setMe(player);
		Player enemy = new Player();
		enemy.setName("Enemy");
		enemy.setField(getMyField(0));
		game.setEnemy(enemy);
		System.out.println("create new game " + game);		
		return game;
		
	}

	public int playGame(int gameId, Player me) {
		
		System.out.println("playGame " + gameId);
		return 0;
		
	}

	public int placeShipInCell(int gameId, Cell cell) {
		
		System.out.println("placeShipInCell " + cell);
		return 0;
		
	}

	public Field getEnemyField(int gameId) {
		
		System.out.println("getEnemyField");
		Field field = getMyExampleField();
		Cell[][] cells = field.getCells();
		cells[4][4] = new Cell('x', 4, 4);
		cells[5][4] = new Cell('x', 5, 4);
		cells[6][4] = new Cell('x', 6, 4);
		cells[7][4] = new Cell('x', 7, 4);
		return field;
		
		
	}

	public Field getMyField(int gameId) {
		
		System.out.println("getMyFields");
		return getMyExampleField();
	}

	public Cell shot(int gameId, Cell cell) {
		
		System.out.println("shot " + cell);
		cell.setStatus('x');
		return cell;
		
	}
	
	
	public Field getMyExampleField() {
		Field field = new Field();
		Cell[][] cells = new Cell[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				cells[i][j] = new Cell('.', i, j);
			}
		}
		cells[4][4] = new Cell('s', 4, 4);
		cells[5][4] = new Cell('s', 5, 4);
		cells[6][4] = new Cell('s', 6, 4);
		cells[7][4] = new Cell('s', 7, 4);
		field.setCells(cells);
		return field;
	}

}
