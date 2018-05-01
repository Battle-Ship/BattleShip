package lv.odo.battleship;

import lv.odo.battleship.demo.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SingleGameControllerImpl implements Controller {

    private Game game;

    private Player enemy;

    private Player player;

    private Field enemyField;

    private Field myField;

    private int maxShipElementsNumber;

    //0 if my turn
    //1 if enemy turn
    private int turn;

    public SingleGameControllerImpl() {
        super();
    }

    public List<Game> getGames() {
        List<Game> games = new ArrayList<Game>();
        games.add(this.game);
        return games;
    }

    public Game createGame(Player me) {
        this.maxShipElementsNumber = Main.SHIPS;
        this.enemyField = generateEnemyField();
        this.myField = initMyField();
        me.setField(this.myField);
        this.player = new Player(me.getName(), myField);
        this.enemy = new Player("Computer", enemyField);
        this.game = new Game(0, me, enemy);
        //random player will shot first
        if (new Random().nextBoolean()) {
            this.game.setTurn(0);
        } else {
            this.game.setTurn(1);
        }
        return this.game;
    }

    //returns -1 if error
    //0 if my turn
    //1 if enemy turn
    public int playGame(int gameId, Player me) {
        return turn;
    }

    public int placeShipInCell(int gameId, Cell cell) {
        if (myField.getCell(cell.getX(), cell.getY()).getStatus() == 's') {
            if (canSetNewStatus(cell, '~')) {
                myField.getCell(cell.getX(), cell.getY()).setStatus('~');
                return 1;
            } else {
                return -1;
            }
        }
        if (maxShipElementsNumber > 0) {
            if (canSetNewStatus(cell, 's')) {
                myField.getCell(cell.getX(), cell.getY()).setStatus('s');
                System.out.println("placed Ship in Cell " + cell);
                maxShipElementsNumber--;
                return 0;
            } else {
                return 1;
            }
        } else {
            System.out.println("can't place Ship in Cell " + cell + ", you don't have ships anymore");
            return 1;
        }
    }

    private boolean canSetNewStatus(Cell cell, char status) {
        Field testField = myField.clone();
        testField.getCell(cell.getX(), cell.getY()).setStatus(status);
        List<List<Cell>> myFleet = Helper.processFleet(testField);
        for (int i = 0; i < Main.POSSIBLE_FLEET.length; i++) {
            int maxNumber = Main.POSSIBLE_FLEET[i];
            int currentNumber = 0;
            for (int j = 0; j < myFleet.size(); j++) {
                if (myFleet.get(j).size() == i + 1) {
                    currentNumber++;
                }
                if (currentNumber >= maxNumber + 1) {
                    System.out.println("can't place Ship in Cell " + cell + ", reached " + maxNumber + " max number of cells in ship");
                    return false;
                }
            }
        }
        return true;
    }

    //we must hide the enemy fleet here
    //for that we need to return only cloned and
    //changed array of cells with hide ships
    public Field getEnemyField(int gameId) {
        System.out.println("getEnemyField");
        Field result = enemyField.clone();
        Cell[][] enemyCells = result.getCells();
        for (int i = 0; i < enemyCells.length; i++) {
            for (int j = 0; j < enemyCells[i].length; j++) {
                if (enemyCells[i][j].getStatus() == 's') {
                    enemyCells[i][j].setStatus('~');
                }
            }
        }
        return result;
    }

    public Field getMyField(int gameId) {
        return myField;
    }

    public Cell shot(int gameId, Cell cell) {
        char status = enemyField.getCell(cell.getX(), cell.getY()).getStatus();
        if (status == 's') {
            processHitting(cell);
        } else {
            enemyField.getCell(cell.getX(), cell.getY()).setStatus('*');
        }
        return cell;
    }

    private void processHitting(Cell cell) {
        enemyField.getCell(cell.getX(), cell.getY()).setStatus('x');
        Set<Cell> shipPositions = new HashSet<Cell>();
        Helper.findWholeShip(cell.clone(), shipPositions, enemyField.clone().getCells());
        boolean allDead = true;
        for (Cell position : shipPositions) {
            if (enemyField.getCells()[position.getX()][position.getY()].getStatus() == 's') {
                allDead = false;
            }
        }
        if (allDead) {
            processDeadShip(shipPositions, enemyField.getCells());
        }
    }

    private void processDeadShip(Set<Cell> shipPositions, Cell[][] cells) {
        for (Cell position : shipPositions) {
            List<Cell> around = getAroundCells(cells[position.getX()][position.getY()], cells);
            for (int j = 0; j < around.size(); j++) {
                around.get(j).setStatus('*');
            }
        }
        for (Cell position : shipPositions) {
            cells[position.getX()][position.getY()].setStatus('.');
        }
    }

    private List<Cell> getAroundCells(Cell cell, Cell[][] cells) {
        List<Cell> result = new ArrayList<Cell>();
        if (cell.getX() + 1 < Main.FIELD_DIMENSION &&
                !cells[cell.getX() + 1][cell.getY()].isShip()) { //+1,0
            result.add(cells[cell.getX() + 1][cell.getY()]);
        }
        if (cell.getX() - 1 >= 0 &&
                !cells[cell.getX() - 1][cell.getY()].isShip()) { //-1,0
            result.add(cells[cell.getX() - 1][cell.getY()]);
        }
        if (cell.getX() - 1 >= 0 &&
                cell.getY() + 1 < Main.FIELD_DIMENSION &&
                !cells[cell.getX() - 1][cell.getY() + 1].isShip()) { //-1,+1
            result.add(cells[cell.getX() - 1][cell.getY() + 1]);
        }
        if (cell.getX() + 1 < Main.FIELD_DIMENSION &&
                cell.getY() - 1 >= 0 &&
                !cells[cell.getX() + 1][cell.getY() - 1].isShip()) { //+1,-1
            result.add(cells[cell.getX() + 1][cell.getY() - 1]);
        }
        if (cell.getY() + 1 < Main.FIELD_DIMENSION &&
                !cells[cell.getX()][cell.getY() + 1].isShip()) { //0,+1
            result.add(cells[cell.getX()][cell.getY() + 1]);
        }
        if (cell.getY() - 1 >= 0 &&
                !cells[cell.getX()][cell.getY() - 1].isShip()) { //0,-1
            result.add(cells[cell.getX()][cell.getY() - 1]);
        }
        if (cell.getX() + 1 < Main.FIELD_DIMENSION &&
                cell.getY() + 1 < Main.FIELD_DIMENSION &&
                !cells[cell.getX() + 1][cell.getY() + 1].isShip()) { //+1,+1
            result.add(cells[cell.getX() + 1][cell.getY() + 1]);
        }
        if (cell.getX() - 1 >= 0 &&
                cell.getY() - 1 >= 0 &&
                !cells[cell.getX() - 1][cell.getY() - 1].isShip()) { //-1,-1
            result.add(cells[cell.getX() - 1][cell.getY() - 1]);
        }
        return result;
    }

    private Field initMyField() {
        //return new Field(Helper.getEmptyField());
        return new Field(Helper.generateRandomField());
    }

    private Field generateEnemyField() {
        return new Field(Helper.generateRandomField());
    }

}
