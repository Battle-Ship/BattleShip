package lv.odo.battleship;

import java.io.IOException;

public class BattleShipGame {
	
	ComputerLocation field;
	Player player;
	int attempts;

	public BattleShipGame(ComputerLocation field, Player player){
		this.field = field;
		this.player = player;
	}
	
	public void start() throws IOException{
		System.out.println("Game start!!!");
		field.showField();
		while(field.hasShips()){
			attempts++;
			player.checkShot(field);
			field.showField();
		}
		System.out.println("Game Over!!!");
		field.showField();
		System.out.printf("You've made %d attemts!", attempts);
	}
	
	public static void main(String[] args) throws IOException{
		ComputerLocation computerField = new ComputerLocation();
		Player player = new Player();
		BattleShipGame game = new BattleShipGame(computerField, player);
		game.start();
	}
}
