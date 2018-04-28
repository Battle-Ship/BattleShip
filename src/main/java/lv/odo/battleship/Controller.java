package lv.odo.battleship;

import java.util.List;

import lv.odo.battleship.Field;
import lv.odo.battleship.Game;
import lv.odo.battleship.Player;

//We need this interface to provide a link between the user interface and business logic
//This interface is a some contract, the development of both parts can go independently by two teams
//If they follow this contract, it guarantees the compatibility of both parts
public interface Controller {
	
	public List<Game> getGames();//return all games
	
	
	public Game createGame(Player me);//create new game
	
	
	public int playGame(int gameId, Player me);
			//returns -1 if error
			//0 if my turn
			//1 if enemy turn
	
	public int placeShipInCell(int gameId, Cell cell);
			//returns -1 if no luck
			//returns 0 if ship is placed
			//returns 1 if all ships have been placed
	
	public Field getEnemyField(int gameId);
			//returns enemy field
	
	public Field getMyField(int gameId);
			//returns my field
	
	public Cell shot(int gameId, Cell cell);
			//makes shot and return result as cell with updated status
}
