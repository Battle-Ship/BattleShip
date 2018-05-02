package MultiplayerGame;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	private ServerSocket server;

	public Server(String ipAddress) throws Exception {
		this.server = new ServerSocket(2000, 1, InetAddress.getByName(ipAddress));
	}
	
	public void run() {
		Socket socketP1 = null;
		try {
			socketP1 = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Player1 connected");
		Socket socketP2 = null;
		try {
			socketP2 = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Player2 connected");

		Player player1 = null;
		try {
			player1 = new Player(socketP1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player2 = null;
		try {
			player2 = new Player(socketP2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player1.start();
		player2.start();
		try {
			player1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			player2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		BattleShipGame game = new BattleShipGame(player1, player2, server);
		try {
			game.start();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InetAddress getSocketAddress() {
		return this.server.getInetAddress();
	}

	public int getPort() {
		return this.server.getLocalPort();
	}

	public static void main(String[] args) throws Exception {
		Server app = new Server("0.0.0.0");
		System.out.println(
				"\r\nRunning Server: " + "Host=" + app.getSocketAddress().getHostAddress() + " Port=" + app.getPort());

		app.start();
	}
}
