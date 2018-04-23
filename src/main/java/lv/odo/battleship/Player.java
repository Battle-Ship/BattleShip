package lv.odo.battleship;

import java.util.Scanner;

public class Player {

	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	// Takes a user
	public int[] shoot(){
		int[] shot = new int[2];
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		System.out.print("Row: ");
		shot[0] = input.nextInt() - 1;
		System.out.print("Column: ");
		shot[1] = input.nextInt() - 1;
		
		return shot;
	}
	
	public void checkShot(ComputerLocation field){
		int[] shot = shoot();
		int shotX = shot[0];
		int shotY = shot[1];
		if(field.getField()[shotX][shotY] == 's'){
			System.out.println("Hit");
			field.hitMark(shot);
		}
		else if(field.getField()[shotX][shotY] == 'X')
			System.out.println("Already hitted");
		else{
			System.out.println("Miss");
			field.missMark(shot);
		}
	}

}
