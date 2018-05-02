package MultiplayerGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import MultiplayerInterface.MainMulti;

public class Client extends Thread{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private PlayerLocation playerField;
	private Field opponentField;
	MainMulti main;
	Shot shot;

	public Client(InetAddress serverAddress, MainMulti main) throws Exception {
		this.main = main;
		this.socket = new Socket(serverAddress, 2000);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}

	public void run() {
		String command = null;
		try {
			command = (String) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while ((command = (String) ois.readObject()) != null) {
				if (command.equals("MAKE_SHOT")) {
					main.yourShot = true;
					main.gameStatusLabel.setText("Your Shot");
					//showFields();
					System.out.println("Shot");
				} else if (command.equals("MESSAGE")) {
					String message = (String) ois.readObject();
					if(message.equals("\nOpponent is shooting"))
						main.gameStatusLabel.setText("Opponent Shot");
					else if(message.equals("You won!!!"))
						main.gameStatusLabel.setText("Game Over!!! You won!!!");
					else if(message.equals("You lose!!!"))
						main.gameStatusLabel.setText("Game Over!!! You lose!!!");
					System.out.println(message);
				} else if (command.equals("FIELD")) {
					
				} else if (command.equals("OPPONENT_SHOT")) {
					String text = (String) ois.readObject();
					Shot shot = (Shot) ois.readObject();
					System.out.println("Opponentmark: " + text);
					if (text.equals("HIT")) {
						main.playerField.hitMark(shot);
					} 
					else if (text.equals("MISS"))
						main.playerField.missMark(shot);
					else if (text.equals("DESTROY")) {
						main.playerField.destroyMark(shot);
					}
					main.refresh();

				} else if (command.equals("END")){}
					
				else if (command.equals("MARK")) {
					String text = null;
					try {
						text = (String) ois.readObject();
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (text.equals("HIT")) {
						main.opponentField.hitMark(shot);
					} else if (text.equals("MISS"))
						main.opponentField.missMark(shot);
					else if (text.equals("DESTROY"))
						main.opponentField.destroyMark(shot);
					else
						System.out.println("Strange");
					main.refresh();
					main.gameStatusLabel.setText("Opponent Shot");
				}
				else
					System.out.println("Strange: " + command);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeField(PlayerLocation field) {
		try {
			oos.writeObject("FIELD");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oos.writeObject(field);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Field send");
	}
	
	public void writeShot(Shot shot) {
		try {
			oos.writeObject("SHOT");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error in sending shot word");
			e.printStackTrace();
		}
		try {
			oos.writeObject(shot);
			this.shot= shot;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error in sending shot");
			e.printStackTrace();
		}
		System.out.println("shot send");
	}

	public void showFields() {
		System.out.print("\t\tOPPONENT FIELD:");
		System.out.print("\t\t\t\t\t");
		System.out.println("YOUR FIELD:");
		System.out.print("   | ");
		String[] abc = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		for (int i = 0; i < 10; i++) {
			System.out.print(abc[i] + " | ");
		}
		System.out.print("\t");
		System.out.print("   | ");
		for (int i = 0; i < 10; i++) {
			System.out.print(abc[i] + " | ");
		}
		System.out.println();
		System.out.print("--------------------------------------------");
		System.out.print("\t--------------------------------------------");
		System.out.println();

		int number = 1;
		for (int i = 0; i < 10; i++) {
			if (number < 10) {
				System.out.print(number + " ");
			} else {
				System.out.print(number + "");
			}
			for (char cell : main.opponentField.getField()[i]) {
				System.out.print(" | " + cell);
			}

			System.out.print("\t");
			if (number < 10) {
				System.out.print(number + " ");
			} else {
				System.out.print(number + "");
			}
			for (char cell : main.playerField.getField()[i]) {
				System.out.print(" | " + cell);
			}
			System.out.print(" |");
			System.out.println();
			System.out.print("--------------------------------------------");
			System.out.println("\t--------------------------------------------");
			number++;
		}
	}

	public static void main(String[] args) throws Exception {
		//Client client = new Client(InetAddress.getByName(args[0]));
		//System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
		//client.start();
	}
}
