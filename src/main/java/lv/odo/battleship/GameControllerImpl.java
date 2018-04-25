package lv.odo.battleship;

import java.util.ArrayList;
import java.util.List;

public class GameControllerImpl implements Controller {

	private Game game = new Game();

	Player enemy = new Player();

	Player player = new Player();

	Field enemyField = getEnemyExampleField();

	Field myField = getMyExampleField();

	public List<Game> getGames() {
		List<Game> games = new ArrayList<Game>();
		games.add(createGame(new Player("me", getMyField(0))));
		System.out.println("get games " + games);
		return games;
	}

	public Game createGame(Player me) {
		game.setId(4);
		player.setName("My Name");
		player.setField(getMyField(0));
		game.setMe(player);
		enemy.setName("Enemy");
		enemy.setField(getEnemyField(0));
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
		myField.getCell(cell.getX(), cell.getY()).setStatus('s');
		System.out.println(myField.getCell(cell.getX(), cell.getY()));
		return 0;
	}

	public Field getEnemyField(int gameId) {
		System.out.println("getEnemyField");
		return enemyField;
	}

	public Field getMyField(int gameId) {
		System.out.println("getMyFields");
		return myField;
	}

	public Cell shot(int gameId, Cell cell) {
		System.out.println("shot " + cell);
		char status = enemyField.getCell(cell.getX(), cell.getY()).getStatus();
		if (status == 's') {
			enemyField.getCell(cell.getX(), cell.getY()).setStatus('x');
		} else {
			enemyField.getCell(cell.getX(), cell.getY()).setStatus('*');
		}
		return cell;
	}
	
	
	private Field getMyExampleField() {
		Field field = new Field();
		Cell[][] cells = new Cell[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				cells[i][j] = new Cell('~', i, j);
			}
		}
		cells[4][4] = new Cell('s', 4, 4);
		cells[5][4] = new Cell('s', 5, 4);
		cells[6][4] = new Cell('s', 6, 4);
		cells[7][4] = new Cell('s', 7, 4);
		field.setCells(cells);
		return field;
	}

	private Field getEnemyExampleField() {
		Field field = new Field();
		Cell[][] cells = new Cell[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				cells[i][j] = new Cell('~', i, j);
			}
		}
		cells[4][8] = new Cell('s', 4, 8);
		cells[5][8] = new Cell('s', 5, 8);
		cells[6][8] = new Cell('s', 6, 8);
		cells[7][8] = new Cell('s', 7, 8);
		field.setCells(cells);
		return field;
	}

}
