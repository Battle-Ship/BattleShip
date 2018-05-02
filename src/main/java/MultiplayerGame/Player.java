package MultiplayerGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Player extends Thread {

	private PlayerLocation playerField;
	private Field opponentField;
	private String name;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public Player opponent;

	public Player(Socket socket) throws IOException {
		opponentField = new Field();
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public void run() {
		try {
			String command;
			while ((command = (String) in.readObject()) != null) {
				if(command.equals("FIELD")) {
					playerField = (PlayerLocation) in.readObject();
					System.out.println("Field recieved");
					break;
				}
				else {
					System.out.println("Error: " + command);
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Field recieved from " + name);
	}

	public void makeShot() throws IOException {
		try {
			out.writeObject("MAKE_SHOT");
			// Takes a user input and generate shot object (x, y)
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int checkShot(Shot shot) throws IOException {
		ArrayList<Ship> ships = opponent.playerField.getShips();
		for (Ship ship : ships) {
			int result = ship.checkHit(shot);
			if (result == -2) {
				markOnField("HIT");
				sendMessage("\nHit!");
				System.out.println("Hit");
				opponentField.hitMark(shot);
				opponent.playerField.hitMark(shot);
				return -2;
			} else if (result == -1) {
				markOnField("ALREADY");
				sendMessage("\nAlready shooted here!");
				System.out.println("Already shooted here");
				return -1;
			} else if (result > 0) {
				markOnField("DESTROY");
				sendMessage("\nHit!");
				System.out.println("Destroy!");

				opponentField.destroyMark(shot);
				opponent.playerField.destroyMark(shot);
				return result;
			}
		}
		markOnField("MISS");
		sendMessage("\nMiss!");
		System.out.println("Miss");
		opponentField.missMark(shot);
		opponent.playerField.missMark(shot);
		return 0;
	}

	public Object readObject() throws ClassNotFoundException, IOException {
		String command;
		while ((command = (String) in.readObject()) != null) {
			if (command.equals("SHOT"))
				return in.readObject();
			else {
				System.out.println("ReadObject error1: " + command);
				return 0;
			}
		}
		System.out.println("ReadObject error2: " + command);
		return 0;
	}

	// Check if player hitted or missed
	public void write(Object object) throws IOException {
		out.writeObject(object);
	}

	public void writeField() throws IOException {
		out.writeObject("FIELD");
	}

	public void finishGame() throws IOException {
		out.writeObject("END");
	}

	public void markOnField(String text) throws IOException {
		out.writeObject("MARK");
		out.writeObject(text);
	}

	public void markOpponentShot(String text, Shot shot) throws IOException {
		out.writeObject("OPPONENT_SHOT");
		out.writeObject(text);
		out.writeObject(shot);
	}

	public void sendMessage(String message) throws IOException {
		out.writeObject("MESSAGE");
		out.writeObject(message);
	}

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	public String getNick() {
		return name;
	}

	public PlayerLocation getPlayerField() {
		return playerField;
	}
}