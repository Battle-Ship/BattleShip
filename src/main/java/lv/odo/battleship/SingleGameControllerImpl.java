package lv.odo.battleship;

import java.util.ArrayList;
import java.util.List;

import lv.odo.battleship.demo.Main;

public class SingleGameControllerImpl implements Controller {

	private Game game;

	private Player enemy;

	private Player player;

	private Field enemyField;

	private Field myField;
	
	private int playerShips; 

	public SingleGameControllerImpl() {
		super();
		this.playerShips = Main.SHIPS;
		myField = getMyExampleField();
		enemyField = getEnemyExampleField();
		player = new Player("Me", myField);
		enemy = new Player("Computer", enemyField);
		game = new Game(0, player, enemy);
	}

	public List<Game> getGames() {
		List<Game> games = new ArrayList<Game>();
		games.add(game);
		System.out.println("get games " + games);
		return games;
	}

	public Game createGame(Player me) {
		return game;
	}

	public int playGame(int gameId, Player me) {
		System.out.println("playGame " + gameId);
		return 0;
	}

	public int placeShipInCell(int gameId, Cell cell) {
		if(myField.getCell(cell.getX(), cell.getY()).getStatus() != '~') {
			System.out.println("can't place Ship In Cell " + cell + "it is not empty");
			return -1;
		}
		if(playerShips > 0) {
			myField.getCell(cell.getX(), cell.getY()).setStatus('s');
			System.out.println("placeShipInCell " + cell);
			playerShips--;
			return 0;
		} else {
			System.out.println("can't place Ship In Cell " + cell + ", you don't have ships anymore");
			return 1;
		}
	}

	//we must hide the enemy fleet here
	//for that we need to return only cloned and
	//changed array of cells with hide ships
	public Field getEnemyField(int gameId) {
		System.out.println("getEnemyField");
		Field result = enemyField.clone();
		Cell[][] enemyCells = result.getCells();
		for(int i = 0; i < enemyCells.length; i++) {
			for(int j = 0; j < enemyCells[i].length; j++) {
				if(enemyCells[i][j].getStatus() == 's') {
					enemyCells[i][j].setStatus('~');
				}
			}
		}
		return result;
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
		Cell[][] cells = new Cell[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cells[i][j] = new Cell('~', i, j);
			}
		}
		Field field = new Field(cells);
		return field;
	}

	private Field getEnemyExampleField() {
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

		cells[4][8] = new Cell('s', 4, 8);
		cells[5][8] = new Cell('s', 5, 8);
		cells[6][8] = new Cell('s', 6, 8);
		cells[7][8] = new Cell('s', 7, 8);

		cells[5][5] = new Cell('s', 5, 5);
		Field field = new Field(cells);
		return field;
	}

}
